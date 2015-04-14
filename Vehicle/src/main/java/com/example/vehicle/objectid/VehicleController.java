package com.example.vehicle.objectid;

import java.util.List;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;
import com.example.vehicle.Constants;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.hyperexpress.builder.UrlBuilder;
import com.strategicgains.repoexpress.mongodb.Identifiers;

/**
 * This is the 'controller' layer, where HTTP details are converted to domain
 * concepts and passed to the service layer. Then service layer response
 * information is enhanced with HTTP details, if applicable, for the response.
 * <p/>
 * This controller demonstrates how to process an entity that is identified by a
 * MongoDB ObjectId.
 */
public class VehicleController {

    private static final UrlBuilder LOCATION_BUILDER = new UrlBuilder();
    private VehicleService service;

    /**
     *
     * @param vehicleService
     */
    public VehicleController(VehicleService vehicleService) {
        super();
        this.service = vehicleService;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Vehicle create(Request request, Response response) {
        Vehicle entity = request.getBodyAs(Vehicle.class, "Resource details not provided");
        Vehicle saved = service.create(entity);

        // Construct the response for create...
        response.setResponseCreated();

        // Bind the resource with link URL tokens, etc. here...
        TokenResolver resolver = HyperExpress.bind(Constants.Url.VEHICLE_ID, Identifiers.MONGOID.format(saved.getId()));

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_VEHICLE);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        // Return the newly-created resource...
        return saved;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Vehicle read(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No resource ID supplied");
        Vehicle entity = service.read(Identifiers.MONGOID.parse(id));

        // enrich the resource with links, etc. here...
        HyperExpress.bind(Constants.Url.VEHICLE_ID, Identifiers.MONGOID.format(entity.getId()));

        return entity;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public List<Vehicle> readAll(Request request, Response response) {
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        List<Vehicle> entities = service.readAll(filter, range, order);
        long count = service.count(filter);
        response.setCollectionResponse(range, entities.size(), count);

        // Bind the resources in the collection with link URL tokens, etc. here...
        HyperExpress.tokenBinder(new TokenBinder<Vehicle>() {
            @Override
            public void bind(Vehicle entity, TokenResolver resolver) {
                resolver.bind(Constants.Url.VEHICLE_ID, Identifiers.MONGOID.format(entity.getId()));
            }
        });

        return entities;
    }

    /**
     *
     * @param request
     * @param response
     */
    public void update(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No resource ID supplied");
        Vehicle entity = request.getBodyAs(Vehicle.class, "Resource details not provided");
        entity.setId(Identifiers.MONGOID.parse(id));
        service.update(entity);
        response.setResponseNoContent();
    }

    /**
     *
     * @param request
     * @param response
     */
    public void delete(Request request, Response response) {
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No resource ID supplied");
        service.delete(Identifiers.MONGOID.parse(id));
        response.setResponseNoContent();
    }
}
