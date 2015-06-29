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

/**
 *
 * @author gstafford
 */
public class Configuration
        extends Environment {

    private static final String DEFAULT_BASE_URL = "localhost";
    private static final String DEFAULT_SERVICE_NAME = "valet-service";
    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT = "port";
    private static final String BASE_URL = "base.url";
    private static final String SERVICE_NAME = "service.name";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private String serviceName;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private TransactionController transactionController;
    private DiagnosticController diagnosticController;

    /**
     *
     * @param p
     */
    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT,
                String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL, DEFAULT_BASE_URL);
        this.serviceName = p.getProperty(SERVICE_NAME, DEFAULT_SERVICE_NAME);
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        TransactionRepository transactionRepository = new TransactionRepository(mongo.getClient(), mongo.getDbName());
        TransactionService transactionService = new TransactionService(transactionRepository);
        transactionController = new TransactionController(transactionService, baseUrl);
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
    public TransactionController getTransactionController() {
        return transactionController;
    }

    /**
     *
     * @return
     */
    public DiagnosticController getDiagnosticController() {
        return diagnosticController;
    }

    /**
     * @return the serviceName
     */
    public String getServiceName() {
        return serviceName;
    }
}
