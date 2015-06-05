package com.example.vehicle;

import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.example.vehicle.serialization.SerializationProvider;
import com.strategicgains.repoexpress.exception.DuplicateItemException;
import com.strategicgains.repoexpress.exception.InvalidObjectIdException;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.restexpress.plugin.cache.CacheControlPlugin;
import com.strategicgains.restexpress.plugin.cors.CorsHeaderPlugin;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsPlugin;
import com.strategicgains.restexpress.plugin.swagger.SwaggerPlugin;
import com.strategicgains.syntaxe.ValidationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import org.restexpress.exception.BadRequestException;
import org.restexpress.exception.ConflictException;
import org.restexpress.exception.NotFoundException;
import org.restexpress.Flags;
import org.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import org.restexpress.plugin.hyperexpress.HyperExpressPlugin;
import org.restexpress.plugin.hyperexpress.Linkable;
import org.restexpress.RestExpress;
import org.restexpress.util.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT;
import static io.netty.handler.codec.http.HttpHeaders.Names.AUTHORIZATION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpHeaders.Names.LOCATION;
import static io.netty.handler.codec.http.HttpHeaders.Names.REFERER;
import static org.restexpress.Flags.Auth.PUBLIC_ROUTE;

/**
 *
 * @author gstafford
 */
public class Main {

    private static Logger LOG;

    /**
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        RestExpress server = initializeServer(args);
        server.awaitShutdown();
    }

    /**
     *
     * @param args
     * @return
     * @throws IOException
     */
    public static RestExpress initializeServer(String[] args) throws IOException {
        RestExpress.setSerializationProvider(new SerializationProvider());

        Configuration config = loadEnvironment(args);
        LOG = LoggerFactory.getLogger(config.getServiceName());
        RestExpress server = new RestExpress()
                .setName(config.getServiceName())
                .setBaseUrl(config.getBaseUrlAndPort())
                .setExecutorThreadCount(config.getExecutorThreadPoolSize())
                .addMessageObserver(new SimpleConsoleLogMessageObserver());

        Routes.define(config, server);
        Relationships.define(server);
        configurePlugins(config, server);
        mapExceptions(server);
        server.bind(config.getPort());
        return server;
    }

    private static void configurePlugins(Configuration config,
            RestExpress server) {
        configureMetrics(config, server);

        new SwaggerPlugin()
                .flag(Flags.Auth.PUBLIC_ROUTE)
                .register(server);

        new CacheControlPlugin() // Support caching headers.
                .register(server);

        new HyperExpressPlugin(Linkable.class)
                .register(server);

        new CorsHeaderPlugin("*")
                .flag(PUBLIC_ROUTE)
                .allowHeaders(CONTENT_TYPE, ACCEPT, AUTHORIZATION, REFERER, LOCATION)
                .exposeHeaders(LOCATION)
                .register(server);
    }

    private static void configureMetrics(Configuration config, RestExpress server) {
        MetricsConfig mc = config.getMetricsConfig();

        if (mc.isEnabled()) {
            MetricRegistry registry = new MetricRegistry();
            new MetricsPlugin(registry)
                    .register(server);

            if (mc.isGraphiteEnabled()) {
                final Graphite graphite = new Graphite(new InetSocketAddress(mc.getGraphiteHost(), mc.getGraphitePort()));
                final GraphiteReporter reporter = GraphiteReporter.forRegistry(registry)
                        .prefixedWith(mc.getPrefix())
                        .convertRatesTo(TimeUnit.SECONDS)
                        .convertDurationsTo(TimeUnit.MILLISECONDS)
                        .filter(MetricFilter.ALL)
                        .build(graphite);
                reporter.start(mc.getPublishSeconds(), TimeUnit.SECONDS);
            } else {
                LOG.warn("*** Graphite Metrics Publishing is Disabled ***");
            }
        } else {
            LOG.warn("*** Metrics Generation is Disabled ***");
        }
    }

    private static void mapExceptions(RestExpress server) {
        server
                .mapException(ItemNotFoundException.class, NotFoundException.class)
                .mapException(DuplicateItemException.class, ConflictException.class)
                .mapException(ValidationException.class, BadRequestException.class)
                .mapException(InvalidObjectIdException.class, BadRequestException.class);
    }

    private static Configuration loadEnvironment(String[] args)
            throws FileNotFoundException, IOException {
        if (args.length > 0) {
            return Environment.from(args[0], Configuration.class);
        }

        return Environment.fromDefault(Configuration.class);
    }
}
