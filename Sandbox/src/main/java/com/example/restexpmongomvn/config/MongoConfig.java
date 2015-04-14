package com.example.restexpmongomvn.config;

import java.net.UnknownHostException;
import java.util.Properties;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.strategicgains.restexpress.exception.ConfigurationException;

/**
 *
 * @author gstafford
 */
public class MongoConfig {

    private static final String URI_PROPERTY = "mongodb.uri";

    private String dbName;
    private MongoClient client;

    /**
     *
     * @param p
     */
    public MongoConfig(Properties p) {
        String uri = p.getProperty(URI_PROPERTY);

        if (uri == null) {
            throw new ConfigurationException("Please define a MongoDB URI for property: " + URI_PROPERTY);
        }

        MongoClientURI mongoUri = new MongoClientURI(uri);
        dbName = mongoUri.getDatabase();
        try {
            client = new MongoClient(mongoUri);
        } catch (UnknownHostException e) {
            throw new ConfigurationException(e);
        }
    }

    /**
     *
     * @return
     */
    public String getDbName() {
        return dbName;
    }

    /**
     *
     * @return
     */
    public MongoClient getClient() {
        return client;
    }
}
