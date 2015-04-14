package com.example.vehicle.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

/**
 *
 * @author gstafford
 */
public class VehicleRepository
        extends MongodbEntityRepository<Vehicle> {

    /**
     *
     * @param mongo
     * @param dbName
     */
    @SuppressWarnings("unchecked")
    public VehicleRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Vehicle.class);
    }
}
