package com.example.maintenance;

import java.util.Properties;
import org.restexpress.RestExpress;
import com.example.maintenance.objectid.MaintenanceController;
import com.example.maintenance.objectid.MaintenanceRepository;
import com.example.maintenance.objectid.MaintenanceService;
import org.restexpress.util.Environment;
import com.strategicgains.repoexpress.mongodb.MongoConfig;
import com.strategicgains.restexpress.plugin.metrics.MetricsConfig;

public class Configuration
        extends Environment {

    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT_PROPERTY = "port";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String baseUrl;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private MaintenanceController maintenanceController;

    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        MaintenanceRepository maintenanceRepository = new MaintenanceRepository(mongo.getClient(), mongo.getDbName());
        MaintenanceService maintenanceService = new MaintenanceService(maintenanceRepository);
        maintenanceController = new MaintenanceController(maintenanceService);
    }

    public int getPort() {
        return port;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public int getExecutorThreadPoolSize() {
        return executorThreadPoolSize;
    }

    public MetricsConfig getMetricsConfig() {
        return metricsSettings;
    }

    public MaintenanceController getMaintenanceController() {
        return maintenanceController;
    }
}
