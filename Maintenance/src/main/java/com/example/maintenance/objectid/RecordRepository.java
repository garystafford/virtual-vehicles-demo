package com.example.maintenance.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class RecordRepository
        extends MongodbEntityRepository<Record> {

    @SuppressWarnings("unchecked")
    public RecordRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Record.class);
    }
}
