package com.example.maintenance.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class MaintenanceRepository
        extends MongodbEntityRepository<Maintenance> {

    @SuppressWarnings("unchecked")
    public MaintenanceRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Maintenance.class);
    }
}
