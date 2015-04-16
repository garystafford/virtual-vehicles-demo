package com.example.valet.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class TransactionRepository
        extends MongodbEntityRepository<Transaction> {

    @SuppressWarnings("unchecked")
    public TransactionRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Transaction.class);
    }
}
