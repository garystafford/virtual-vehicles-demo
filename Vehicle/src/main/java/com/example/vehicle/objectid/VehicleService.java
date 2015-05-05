package com.example.vehicle.objectid;

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
public class VehicleService {

    private final VehicleRepository vehicles;

    /**
     *
     * @param vehiclesRepository
     */
    public VehicleService(VehicleRepository vehiclesRepository) {
        super();
        this.vehicles = vehiclesRepository;
    }

    /**
     *
     * @param entity
     * @return
     */
    public Vehicle create(Vehicle entity) {
        ValidationEngine.validateAndThrow(entity);
        return vehicles.create(entity);
    }

    /**
     *
     * @param id
     * @return
     */
    public Vehicle read(Identifier id) {
        return vehicles.read(id);
    }

    /**
     *
     * @param entity
     */
    public void update(Vehicle entity) {
        ValidationEngine.validateAndThrow(entity);
        vehicles.update(entity);
    }

    /**
     *
     * @param id
     */
    public void delete(Identifier id) {
        vehicles.delete(id);
    }

    /**
     *
     * @param filter
     * @param range
     * @param order
     * @return
     */
    public List<Vehicle> readAll(QueryFilter filter, QueryRange range, QueryOrder order) {
        return vehicles.readAll(filter, range, order);
    }

    /**
     *
     * @param filter
     * @return
     */
    public long count(QueryFilter filter) {
        return vehicles.count(filter);
    }
}
