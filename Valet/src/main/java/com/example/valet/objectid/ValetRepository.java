package com.example.valet.objectid;

import com.mongodb.MongoClient;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;

public class ValetRepository
extends MongodbEntityRepository<Valet>
{
	@SuppressWarnings("unchecked")
    public ValetRepository(MongoClient mongo, String dbName)
	{
		super(mongo, dbName, Valet.class);
	}
}
