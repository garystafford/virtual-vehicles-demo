package com.example.maintenance.objectid;

import java.util.List;

import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;

import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.syntaxe.ValidationEngine;

/**
 * This is the 'maintenance' or 'business logic' layer, where business logic,
 * syntactic and semantic domain validation occurs, along with calls to the
 * persistence layer.
 */
public class RecordService {

    private final RecordRepository records;

    public RecordService(RecordRepository maintenancesRepository) {
        super();
        this.records = maintenancesRepository;
    }

    public Record create(Record entity) {
        ValidationEngine.validateAndThrow(entity);
        return records.create(entity);
    }

    public Record read(Identifier id) {
        return records.read(id);
    }

    public void update(Record entity) {
        ValidationEngine.validateAndThrow(entity);
        records.update(entity);
    }

    public void delete(Identifier id) {
        records.delete(id);
    }

    public List<Record> readAll(QueryFilter filter, QueryRange range, QueryOrder order) {
        return records.readAll(filter, range, order);
    }

    public long count(QueryFilter filter) {
        return records.count(filter);
    }
}
