package com.example.authentication.objectid;

import java.util.List;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;

/**
 * This is the 'service' or 'business logic' layer, where business logic,
 * syntactic and semantic domain validation occurs, along with calls to the
 * persistence layer.
 */
public class ClientService {

    private final ClientRepository clients;

    public ClientService(ClientRepository clientRepository) {
        super();
        this.clients = clientRepository;
    }

    public Client create(Client entity) {
        ValidationEngine.validateAndThrow(entity);
        return clients.create(entity);
    }

    public Client read(Identifier id) {
        return clients.read(id);
    }

    public void update(Client entity) {
        ValidationEngine.validateAndThrow(entity);
        clients.update(entity);
    }

    public void delete(Identifier id) {
        clients.delete(id);
    }

    public List<Client> readAll(QueryFilter filter, QueryRange range,
            QueryOrder order) {
        return clients.readAll(filter, range, order);
    }

    public long count(QueryFilter filter) {
        return clients.count(filter);
    }

//    public List<Client> find(QueryFilter filter) {
//        return clients.find(filter);
//    }
}
