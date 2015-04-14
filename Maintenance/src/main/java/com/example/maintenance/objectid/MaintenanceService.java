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
public class MaintenanceService {

    private MaintenanceRepository maintenances;

    public MaintenanceService(MaintenanceRepository maintenancesRepository) {
        super();
        this.maintenances = maintenancesRepository;
    }

    public Maintenance create(Maintenance entity) {
        ValidationEngine.validateAndThrow(entity);
        return maintenances.create(entity);
    }

    public Maintenance read(Identifier id) {
        return maintenances.read(id);
    }

    public void update(Maintenance entity) {
        ValidationEngine.validateAndThrow(entity);
        maintenances.update(entity);
    }

    public void delete(Identifier id) {
        maintenances.delete(id);
    }

    public List<Maintenance> readAll(QueryFilter filter, QueryRange range, QueryOrder order) {
        return maintenances.readAll(filter, range, order);
    }

    public long count(QueryFilter filter) {
        return maintenances.count(filter);
    }
}
