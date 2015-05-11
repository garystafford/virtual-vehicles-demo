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

    public TransactionService(TransactionRepository transactionsRepository) {
        super();
        this.transactions = transactionsRepository;
    }

    public Transaction create(Transaction entity) {
        ValidationEngine.validateAndThrow(entity);
        return transactions.create(entity);
    }

    public Transaction read(Identifier id) {
        return transactions.read(id);
    }

    public void update(Transaction entity) {
        ValidationEngine.validateAndThrow(entity);
        transactions.update(entity);
    }

    public void delete(Identifier id) {
        transactions.delete(id);
    }

    public List<Transaction> readAll(QueryFilter filter, QueryRange range, QueryOrder order) {
        return transactions.readAll(filter, range, order);
    }

    public long count(QueryFilter filter) {
        return transactions.count(filter);
    }

    public List<Transaction> find(QueryFilter filter) {
        return transactions.find(filter);
    }
}
