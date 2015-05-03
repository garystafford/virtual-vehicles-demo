package com.example.authentication.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class ClientRepository
        extends MongodbEntityRepository<Client> {

    @SuppressWarnings("unchecked")
    public ClientRepository(MongoClient mongo, String dbName) {
        super(mongo, dbName, Client.class);
    }
}
