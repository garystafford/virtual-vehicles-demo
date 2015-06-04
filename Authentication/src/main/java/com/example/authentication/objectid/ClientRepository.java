package com.example.authentication.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

/**
 *
 * @author gstafford
 */
public class ClientRepository
        extends MongodbEntityRepository<Client> {

    /**
     *
     * @param mongo
     * @param dbName
     */
    @SuppressWarnings("unchecked")
    public ClientRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Client.class);
    }
}
