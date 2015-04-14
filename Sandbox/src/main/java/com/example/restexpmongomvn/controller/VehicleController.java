package com.example.restexpmongomvn.controller;

import java.util.List;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.hyperexpress.RelTypes;
import com.strategicgains.hyperexpress.domain.Link;
import com.strategicgains.hyperexpress.domain.LinkableCollection;
import com.strategicgains.hyperexpress.util.LinkUtils;
import com.strategicgains.repoexpress.mongodb.MongodbEntityRepository;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;
import com.strategicgains.restexpress.common.query.QueryFilter;
import com.strategicgains.restexpress.common.query.QueryOrder;
import com.strategicgains.restexpress.common.query.QueryRange;
import com.strategicgains.restexpress.exception.BadRequestException;
import com.strategicgains.restexpress.query.QueryFilters;
import com.strategicgains.restexpress.query.QueryOrders;
import com.strategicgains.restexpress.query.QueryRanges;
import com.example.restexpmongomvn.Constants;
import com.example.restexpmongomvn.domain.Vehicle;
import com.strategicgains.syntaxe.ValidationEngine;

/**
 *
 * @author gstafford
 */
public class VehicleController {

    private MongodbEntityRepository<Vehicle> vehicles;

    /**
     *
     * @param vehicleRepository
     */
    public VehicleController(MongodbEntityRepository<Vehicle> vehicleRepository) {
        super();
        this.vehicles = vehicleRepository;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public String create(Request request, Response response) {
        Vehicle order = request.getBodyAs(Vehicle.class, "Vehicle details not provided");
        ValidationEngine.validateAndThrow(order);
        Vehicle saved = vehicles.create(order);

        // Construct the response for create...
        response.setResponseCreated();

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.VEHICLE_INSTANCE);
        response.addLocationHeader(LinkUtils.formatUrl(locationPattern, Constants.Url.VEHICLE_ID, saved.getId()));

        // Return the newly-created ID...
        return saved.getId();
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Vehicle read(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No Vehicle ID supplied");
        Vehicle vehicle = vehicles.read(id);

        // Add 'self' link
        String selfPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.VEHICLE_INSTANCE);
        String selfUrl = LinkUtils.formatUrl(selfPattern, Constants.Url.VEHICLE_ID, vehicle.getId());
        vehicle.addLink(new Link(RelTypes.SELF, selfUrl));

        return vehicle;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public LinkableCollection<Vehicle> readAll(Request request, Response response) {
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        List<Vehicle> results = vehicles.readAll(filter, range, order);
        long count = vehicles.count(filter);
        response.setCollectionResponse(range, results.size(), count);

        // Add 'self' links
        String orderSelfPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.VEHICLE_INSTANCE);

        for (Vehicle result : results) {
            String selfUrl = LinkUtils.formatUrl(orderSelfPattern, Constants.Url.VEHICLE_ID, result.getId());
            result.addLink(new Link(RelTypes.SELF, selfUrl));
        }

        String selfUrl = request.getNamedUrl(HttpMethod.GET, Constants.Routes.VEHICLE_COLLECTION);
        LinkableCollection<Vehicle> wrapper = new LinkableCollection<Vehicle>(results);
        wrapper.addLink(new Link(RelTypes.SELF, selfUrl));
        return wrapper;
    }

    /**
     *
     * @param request
     * @param response
     */
    public void update(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VEHICLE_ID);
        Vehicle vehicle = request.getBodyAs(Vehicle.class, "Vehicle details not provided");

        if (!id.equals(vehicle.getId())) {
            throw new BadRequestException("ID in URL and ID in Vehicle must match");
        }

        ValidationEngine.validateAndThrow(vehicle);
        vehicles.update(vehicle);
        response.setResponseNoContent();
    }

    /**
     *
     * @param request
     * @param response
     */
    public void delete(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No Vehicle ID supplied");
        vehicles.delete(id);
        response.setResponseNoContent();
    }
}
