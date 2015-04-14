package com.example.valet.objectid;

import java.util.List;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;

/**
 * This is the 'service' or 'business logic' layer, where business logic, syntactic and semantic
 * domain validation occurs, along with calls to the persistence layer.
 */
public class ValetService
{
	private ValetRepository valets;
	
	public ValetService(ValetRepository valetsRepository)
	{
		super();
		this.valets = valetsRepository;
	}

	public Valet create(Valet entity)
	{
		ValidationEngine.validateAndThrow(entity);
		return valets.create(entity);
	}

	public Valet read(Identifier id)
    {
		return valets.read(id);
    }

	public void update(Valet entity)
    {
		ValidationEngine.validateAndThrow(entity);
		valets.update(entity);
    }

	public void delete(Identifier id)
    {
		valets.delete(id);
    }

	public List<Valet> readAll(QueryFilter filter, QueryRange range, QueryOrder order)
    {
		return valets.readAll(filter, range, order);
    }

	public long count(QueryFilter filter)
    {
		return valets.count(filter);
    }
}
