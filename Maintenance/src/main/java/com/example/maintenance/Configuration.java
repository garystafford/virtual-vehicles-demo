package com.example.maintenance;

import java.util.Properties;
import org.restexpress.RestExpress;
import com.example.maintenance.objectid.RecordController;
import com.example.maintenance.objectid.RecordRepository;
import com.example.maintenance.objectid.RecordService;
import com.example.utilities.DiagnosticController;
import org.restexpress.util.Environment;
import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration
        extends Environment {

    private static final String DEFAULT_BASE_URL = "http://localhost";
    private static final String DEFAULT_SERVICE_NAME = "maintenance-service";
    private static final String DEFAULT_AUTHENTICATION_PORT = "8082";
    private static final String DEFAULT_AUTHENTICATION_URL = "localhost";
    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT = "port";
    private static final String BASE_URL = "base.url";
    private static final String SERVICE_NAME = "service.name";
    private static final String AUTHENTICATION_PORT = "authentication.port";
    private static final String AUTHENTICATION_URL = "authentication.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private String baseUrlAndPort;
    private int authPort;
    private String authUrl;
    private String authUrlAndAuthPort;
    private String serviceName;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private RecordController recordController;
    private DiagnosticController diagnosticController;

    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT,
                String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL, DEFAULT_BASE_URL);
        this.baseUrlAndPort = baseUrl + ":" + port;
        
        this.authUrl = p.getProperty(AUTHENTICATION_URL, DEFAULT_AUTHENTICATION_URL);
        this.authPort = Integer.parseInt(p.getProperty(
                AUTHENTICATION_PORT, String.valueOf(DEFAULT_AUTHENTICATION_PORT)));
        this.authUrlAndAuthPort = getAuthUrl() + ":" + authPort;

        this.serviceName = p.getProperty(SERVICE_NAME, DEFAULT_SERVICE_NAME);
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        RecordRepository recordRepository = new RecordRepository(mongo.getClient(), mongo.getDbName());
        RecordService recordService = new RecordService(recordRepository);
        recordController = new RecordController(recordService, authUrlAndAuthPort);
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
     * @return the authenticationPort
     */
    public int getAuthPort() {
        return authPort;
    }

    /**
     * @return the baseUrlAndAuthPort
     */
    public String getAuthUrlAndAuthPort() {
        return authUrlAndAuthPort;
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

    public RecordController getRecordController() {
        return recordController;
    }

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
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }
}
