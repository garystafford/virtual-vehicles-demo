package com.example.valet.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

/**
 *
 * @author gstafford
 */
public class TransactionRepository
        extends MongodbEntityRepository<Transaction> {

    /**
     *
     * @param mongo
     * @param dbName
     */
    @SuppressWarnings("unchecked")
    public TransactionRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Transaction.class);
    }
}
