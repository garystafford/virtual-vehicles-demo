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

    /**
     *
     * @param clientRepository
     */
    public ClientService(ClientRepository clientRepository) {
        super();
        this.clients = clientRepository;
    }

    /**
     *
     * @param entity
     * @return
     */
    public Client create(Client entity) {
        ValidationEngine.validateAndThrow(entity);
        return clients.create(entity);
    }

    /**
     *
     * @param id
     * @return
     */
    public Client read(Identifier id) {
        return clients.read(id);
    }

    /**
     *
     * @param entity
     */
    public void update(Client entity) {
        ValidationEngine.validateAndThrow(entity);
        clients.update(entity);
    }

    /**
     *
     * @param id
     */
    public void delete(Identifier id) {
        clients.delete(id);
    }

    /**
     *
     * @param filter
     * @param range
     * @param order
     * @return
     */
    public List<Client> readAll(QueryFilter filter, QueryRange range,
            QueryOrder order) {
        return clients.readAll(filter, range, order);
    }

    /**
     *
     * @param filter
     * @return
     */
    public long count(QueryFilter filter) {
        return clients.count(filter);
    }

//    public List<Client> find(QueryFilter filter) {
//        return clients.find(filter);
//    }
}
