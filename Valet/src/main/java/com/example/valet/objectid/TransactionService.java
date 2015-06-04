package com.example.valet.objectid;

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
public class TransactionService {

    private final TransactionRepository transactions;

    /**
     *
     * @param transactionsRepository
     */
    public TransactionService(TransactionRepository transactionsRepository) {
        super();
        this.transactions = transactionsRepository;
    }

    /**
     *
     * @param entity
     * @return
     */
    public Transaction create(Transaction entity) {
        ValidationEngine.validateAndThrow(entity);
        return transactions.create(entity);
    }

    /**
     *
     * @param id
     * @return
     */
    public Transaction read(Identifier id) {
        return transactions.read(id);
    }

    /**
     *
     * @param entity
     */
    public void update(Transaction entity) {
        ValidationEngine.validateAndThrow(entity);
        transactions.update(entity);
    }

    /**
     *
     * @param id
     */
    public void delete(Identifier id) {
        transactions.delete(id);
    }

    /**
     *
     * @param filter
     * @param range
     * @param order
     * @return
     */
    public List<Transaction> readAll(QueryFilter filter, QueryRange range, QueryOrder order) {
        return transactions.readAll(filter, range, order);
    }

    /**
     *
     * @param filter
     * @return
     */
    public long count(QueryFilter filter) {
        return transactions.count(filter);
    }
}
