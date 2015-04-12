package com.example.ms.vehicle;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.codahale.metrics.MetricFilter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.graphite.Graphite;
import com.codahale.metrics.graphite.GraphiteReporter;
import com.strategicgains.repoexpress.exception.DuplicateItemException;
import com.strategicgains.repoexpress.exception.InvalidObjectIdException;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;
import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.Parameters;
import com.strategicgains.restexpress.RestExpress;
import com.strategicgains.restexpress.exception.BadRequestException;
import com.strategicgains.restexpress.exception.ConflictException;
import com.strategicgains.restexpress.exception.NotFoundException;
import com.strategicgains.restexpress.pipeline.SimpleConsoleLogMessageObserver;
import com.strategicgains.restexpress.plugin.cache.CacheControlPlugin;
import com.strategicgains.restexpress.plugin.metrics.MetricsPlugin;
import com.strategicgains.restexpress.plugin.route.RoutesMetadataPlugin;
import com.example.ms.vehicle.config.Configuration;
import com.example.ms.vehicle.config.MetricsConfig;
import com.example.ms.vehicle.serialization.ResponseProcessors;
import com.strategicgains.restexpress.util.Environment;
import com.strategicgains.syntaxe.ValidationException;

public class Main {

    private static final String SERVICE_NAME = "virtual-vehicle";
    private static final Logger LOG = LoggerFactory.getLogger(SERVICE_NAME);

    public static void main(String[] args) throws Exception {
        RestExpress server = initializeServer(args);
        server.awaitShutdown();
    }

    public static RestExpress initializeServer(String[] args) throws IOException {
        Configuration config = loadEnvironment(args);
        RestExpress server = new RestExpress()
                .setName(SERVICE_NAME)
                .setBaseUrl(config.getBaseUrl())
                .setDefaultFormat(config.getDefaultFormat())
                .setExecutorThreadCount(config.getExecutorThreadPoolSize())
                .putResponseProcessor(Format.JSON, ResponseProcessors.json())
                .putResponseProcessor(Format.XML, ResponseProcessors.xml())
                .addMessageObserver(new SimpleConsoleLogMessageObserver());

        Routes.define(config, server);
        configureMetrics(config, server);

        new RoutesMetadataPlugin() // Support basic discoverability.
                .register(server)
                .parameter(Parameters.Cache.MAX_AGE, 86400);	// Cache for 1 day (24 hours).

        new CacheControlPlugin() // Support caching headers.
                .register(server);

        mapExceptions(server);
        server.bind(config.getPort());
        return server;
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
