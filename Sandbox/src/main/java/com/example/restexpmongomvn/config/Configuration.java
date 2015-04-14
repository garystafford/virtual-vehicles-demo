package com.example.restexpmongomvn.config;

import java.util.Properties;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;
import com.strategicgains.restexpress.Format;
import com.strategicgains.restexpress.RestExpress;
import com.example.restexpmongomvn.controller.SampleController;
import com.example.restexpmongomvn.controller.VehicleController;
import com.example.restexpmongomvn.domain.Sample;
import com.example.restexpmongomvn.domain.Vehicle;
import com.strategicgains.restexpress.util.Environment;

/**
 *
 * @author gstafford
 */
public class Configuration
        extends Environment {

    private static final String DEFAULT_EXECUTOR_THREAD_POOL_SIZE = "20";

    private static final String PORT_PROPERTY = "port";
    private static final String DEFAULT_FORMAT_PROPERTY = "default.format";
    private static final String BASE_URL_PROPERTY = "base.url";
    private static final String EXECUTOR_THREAD_POOL_SIZE = "executor.threadPool.size";

    private int port;
    private String defaultFormat;
    private String baseUrl;
    private int executorThreadPoolSize;
    private MetricsConfig metricsSettings;

    private SampleController sampleController;
    private VehicleController vehicleController;

    /**
     *
     * @param p
     */
    @Override
    protected void fillValues(Properties p) {
        this.port = Integer.parseInt(p.getProperty(PORT_PROPERTY, String.valueOf(RestExpress.DEFAULT_PORT)));
        this.defaultFormat = p.getProperty(DEFAULT_FORMAT_PROPERTY, Format.JSON);
        this.baseUrl = p.getProperty(BASE_URL_PROPERTY, "http://localhost:" + String.valueOf(port));
        this.executorThreadPoolSize = Integer.parseInt(p.getProperty(EXECUTOR_THREAD_POOL_SIZE, DEFAULT_EXECUTOR_THREAD_POOL_SIZE));
        this.metricsSettings = new MetricsConfig(p);
        MongoConfig mongo = new MongoConfig(p);
        initialize(mongo);
    }

    private void initialize(MongoConfig mongo) {
        @SuppressWarnings("unchecked")
        MongodbEntityRepository<Sample> orderRepository = new MongodbEntityRepository<Sample>(mongo.getClient(), mongo.getDbName(), Sample.class);
        sampleController = new SampleController(orderRepository);

        @SuppressWarnings("unchecked")
        MongodbEntityRepository<Vehicle> vehicleRepository = new MongodbEntityRepository<Vehicle>(mongo.getClient(), mongo.getDbName(), Vehicle.class);
        vehicleController = new VehicleController(vehicleRepository);
    }

    /**
     *
     * @return
     */
    public String getDefaultFormat() {
        return defaultFormat;
    }

    /**
     *
     * @return
     */
    public int getPort() {
        return port;
    }

    /**
     *
     * @return
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
    public SampleController getSampleController() {
        return sampleController;
    }

    /**
     *
     * @return
     */
    public VehicleController getVehicleController() {
        return vehicleController;
    }
}
