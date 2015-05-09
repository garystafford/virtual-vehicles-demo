package com.example.authentication;

import java.util.Properties;

import org.restexpress.RestExpress;
import com.example.authentication.objectid.ClientController;
import com.example.authentication.objectid.ClientRepository;
import com.example.authentication.objectid.ClientService;
import com.example.authentication.objectid.JwtController;

import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration
        extends Environment {

    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT_PROPERTY = "port";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String AUTHENTICATION_PORT = "authentication.port";
    private static final String EXECUTOR_THREAD_POOL_SIZE
            = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private String baseUrlAndPort;
    private int authenticationPort;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;
    private ClientController clientController;
    private JwtController jwtController;

    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY,
                String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost");
        this.baseUrlAndPort = baseUrl + ":" + port;
        this.authenticationPort = Integer.parseInt(p.getProperty(
                AUTHENTICATION_PORT, String.valueOf(port)));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(
                EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        ClientRepository clientRepository = new ClientRepository(
                mongo.getClient(), mongo.getDbName());
        ClientService clientService = new ClientService(clientRepository);
        clientController = new ClientController(clientService);
        jwtController = new JwtController();
    }

    /**
     *
     * @return port
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return baseUrl
     */
    public String getBaseUrl() {
        return baseUrl;
    }

    /**
     *
     * @return baseUrlAndPort
     */
    public String getBaseUrlAndPort() {
        return baseUrlAndPort;
    }

    /**
     * @return authenticationPort
     */
    public int getAuthenticationPort() {
        return authenticationPort;
    }

    public int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    public MetricsConfig getMetricsConfig() {
        return metricsSettings;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public JwtController getJwtController() {
        return jwtController;
    }

    Object getJwtController(String baseUrlAndPort) {
        return new JwtController(baseUrlAndPort);
    }
}
