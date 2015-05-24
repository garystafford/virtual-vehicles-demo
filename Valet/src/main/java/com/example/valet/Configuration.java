package com.example.valet;

import com.example.utilities.DiagnosticController;
import java.util.Properties;

import org.restexpress.RestExpress;
import com.example.valet.objectid.TransactionController;
import com.example.valet.objectid.TransactionRepository;
import com.example.valet.objectid.TransactionService;
import org.restexpress.util.Environment;

import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration
        extends Environment {

    private static final String DEFAULT_BASE_URL = "http://localhost";
    private static final String DEFAULT_AUTHENTICATION_PORT = "8082";
    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT = "port";
    private static final String BASE_URL = "base.url";
    private static final String AUTHENTICATION_PORT = "authentication.port";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private String baseUrlAndPort;
    private int authPort;
    private String baseUrlAndAuthPort;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private TransactionController transactionController;
    private DiagnosticController diagnosticController;

    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT,
                String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL, DEFAULT_BASE_URL);
        this.baseUrlAndPort = baseUrl + ":" + port;
        this.authPort = Integer.parseInt(p.getProperty(
                AUTHENTICATION_PORT, String.valueOf(DEFAULT_AUTHENTICATION_PORT)));
        this.baseUrlAndAuthPort = baseUrl + ":" + authPort;

        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        TransactionRepository transactionRepository = new TransactionRepository(mongo.getClient(), mongo.getDbName());
        TransactionService transactionService = new TransactionService(transactionRepository);
        transactionController = new TransactionController(transactionService, getBaseUrlAndAuthPort());
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
    public String getBaseUrlAndAuthPort() {
        return baseUrlAndAuthPort;
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

    public TransactionController getTransactionController() {
        return transactionController;
    }

    public DiagnosticController getDiagnosticController() {
        return diagnosticController;
    }
}
