package com.example.authentication;

import java.util.Properties;

import org.restexpress.RestExpress;
import com.example.authentication.objectid.ClientController;
import com.example.authentication.objectid.ClientRepository;
import com.example.authentication.objectid.ClientService;
import com.example.authentication.objectid.JwtController;
import com.example.utilities.DiagnosticController;

import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

/**
 *
 * @author gstafford
 */
public class Configuration
        extends Environment {

    private static final String DEFAULT_BASE_URL = "http://localhost";
    private static final String DEFAULT_SERVICE_NAME = "authentication-service";
    private static final String DEFAULT_AUTHENTICATION_PORT = "8082";
    private static final String DEFAULT_AUTHENTICATION_URL = "localhost";
    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String DEFAULT_JWT_EXPIRE_LENGTH = "36000"; // 10 hours
    private static final String DEFAULT_JWT_ISSUER = "Virtual-Vehicles";

    private static final String PORT_PROPERTY = "port";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String SERVICE_NAME = "service.name";
    private static final String AUTHENTICATION_PORT = "authentication.port";
    private static final String AUTHENTICATION_URL = "authentication.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private static final String JWT_EXPIRE_LENGTH = "jwt.expire.length";
    private static final String JWT_ISSUER = "jwt.issuer";

    private int port;
    private String baseUrl;
    private String baseUrlAndPort;
    private int authPort;
    private String authUrl;
    private String authUrlAndAuthPort;
    private String serviceName;
    private int jwtExpireLength;
    private String jwtIssuer;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;
    private ClientController clientController;
    private JwtController jwtController;
    private DiagnosticController diagnosticController;

    /**
     *
     * @param p
     */
    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY,
                String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, DEFAULT_BASE_URL);
        this.baseUrlAndPort = baseUrl + ":" + port;

        this.authUrl = p.getProperty(AUTHENTICATION_URL, DEFAULT_AUTHENTICATION_URL);
        this.authPort = Integer.parseInt(p.getProperty(
                AUTHENTICATION_PORT, String.valueOf(DEFAULT_AUTHENTICATION_PORT)));
        this.authUrlAndAuthPort = getAuthUrl() + ":" + authPort;

        this.jwtExpireLength = Integer.parseInt(p.getProperty(
                JWT_EXPIRE_LENGTH, DEFAULT_JWT_EXPIRE_LENGTH));
        this.jwtIssuer = p.getProperty(
                JWT_ISSUER, DEFAULT_JWT_ISSUER);
        
        this.serviceName = p.getProperty(SERVICE_NAME, DEFAULT_SERVICE_NAME);
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
        jwtController = new JwtController(authUrlAndAuthPort, jwtExpireLength, jwtIssuer);
        diagnosticController = new DiagnosticController();
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
     * @return the jwtExpireLength
     */
    public int getJwtExpireLength() {
        return jwtExpireLength;
    }

    /**
     * @return the jwtIssuer
     */
    public String getJwtIssuer() {
        return jwtIssuer;
    }

    /**
     *
     * @return
     */
    public int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    /**
     *
     * @return
     */
    public MetricsConfig getMetricsConfig() {
        return metricsSettings;
    }

    /**
     *
     * @return
     */
    public ClientController getClientController() {
        return clientController;
    }

    /**
     *
     * @return
     */
    public JwtController getJwtController() {
        return jwtController;
    }

    /**
     *
     * @return
     */
    public DiagnosticController getDiagnosticController() {
        return diagnosticController;
    }

    /**
     * @return the authUrl
     */
    public String getAuthUrl() {
        return authUrl;
    }

    /**
     * @return the authUrlAndAuthPort
     */
    public String getAuthUrlAndAuthPort() {
        return authUrlAndAuthPort;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }
}
