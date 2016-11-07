package com.example.vehicle.objectid;

import com.example.authenticate.AuthenticateJwt;
import com.example.vehicle.Constants;
import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.builder.DefaultUrlBuilder;
import com.strategicgains.hyperexpress.builder.TokenBinder;
import com.strategicgains.hyperexpress.builder.TokenResolver;
import com.strategicgains.repoexpress.mongodb.Identifiers;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.restexpress.Request;
import org.restexpress.Response;
import org.restexpress.common.query.QueryFilter;
import org.restexpress.common.query.QueryOrder;
import org.restexpress.common.query.QueryRange;
import org.restexpress.query.QueryFilters;
import org.restexpress.query.QueryOrders;
import org.restexpress.query.QueryRanges;

import java.util.List;

/**
 * This is the 'controller' layer, where HTTP details are converted to domain
 * concepts and passed to the service layer. Then service layer response
 * information is enhanced with HTTP details, if applicable, for the response.
 * <p/>
 * This controller demonstrates how to process an entity that is identified by a
 * MongoDB ObjectId.
 */
public class VehicleController {

    private static final Logger LOG = LogManager.getLogger(VehicleController.class.getName());
    private static final DefaultUrlBuilder LOCATION_BUILDER = new DefaultUrlBuilder();
    private final VehicleService service;
    private final String baseUrl;
    private final AuthenticateJwt jwtImpl = new AuthenticateJwt();

    /**
     *
     * @param vehicleService
     * @param baseUrl
     */
    public VehicleController(VehicleService vehicleService, String baseUrl) {
        super();
        this.service = vehicleService;
        this.baseUrl = baseUrl;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Vehicle create(Request request, Response response) {
        if (jwtImpl.authenticateJwt(request, baseUrl) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        Vehicle entity = request.getBodyAs(Vehicle.class, "Resource details not provided");
        Vehicle saved = service.create(entity);

        // Construct the response for create...
        response.setResponseCreated();

        // Bind the resource with link URL tokens, etc. here...
        TokenResolver resolver = HyperExpress.bind(Constants.Url.VEHICLE_ID, Identifiers.MONGOID.format(saved.getId()));

        // Include the Location header...
        String locationPattern = request.getNamedUrl(HttpMethod.GET, Constants.Routes.SINGLE_VEHICLE);
        response.addLocationHeader(LOCATION_BUILDER.build(locationPattern, resolver));

        LOG.info("vehicle created: " + Identifiers.MONGOID.format(saved.getId()));

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
        if (jwtImpl.authenticateJwt(request, baseUrl) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
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
        if (jwtImpl.authenticateJwt(request, baseUrl) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        QueryFilter filter = QueryFilters.parseFrom(request);
        QueryOrder order = QueryOrders.parseFrom(request);
        QueryRange range = QueryRanges.parseFrom(request, 20);
        boolean countOnly = Boolean.parseBoolean(request.getQueryStringMap().getOrDefault("countOnly", "false"));
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

        if (countOnly) { // only return count in Content-Range header
            entities.clear();
            return entities;
        }
        return entities;
    }

    /**
     *
     * @param request
     * @param response
     * @return
     */
    public Vehicle update(Request request, Response response) {
        if (jwtImpl.authenticateJwt(request, baseUrl) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
            return null;
        }
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No resource ID supplied");
        Vehicle entity = request.getBodyAs(Vehicle.class, "Resource details not provided");
        entity.setId(Identifiers.MONGOID.parse(id));
        service.update(entity);

        // new per http://stackoverflow.com/a/827045/580268
        entity = service.read(Identifiers.MONGOID.parse(id));
        response.setResponseStatus(HttpResponseStatus.CREATED);

        // enrich the resource with links, etc. here...
        HyperExpress.bind(Constants.Url.VEHICLE_ID, Identifiers.MONGOID.format(entity.getId()));

        LOG.info("vehicle updated: " + Identifiers.MONGOID.format(entity.getId()));

        return entity;

        // original response returned nothing
        //response.setResponseNoContent();
    }

    /**
     *
     * @param request
     * @param response
     */
    public void delete(Request request, Response response) {
        if (jwtImpl.authenticateJwt(request, baseUrl) != true) {
            response.setResponseStatus(HttpResponseStatus.UNAUTHORIZED);
        }
        String id = request.getHeader(Constants.Url.VEHICLE_ID, "No resource ID supplied");
        service.delete(Identifiers.MONGOID.parse(id));

        LOG.info("vehicle updated: " + Identifiers.MONGOID.parse(id));

        response.setResponseNoContent();
    }
}
