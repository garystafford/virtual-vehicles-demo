package com.example.vehicle;

import java.util.Map;

import org.restexpress.RestExpress;
import com.example.vehicle.objectid.Vehicle;

import com.strategicgains.hyperexpress.HyperExpress;
import com.strategicgains.hyperexpress.RelTypes;

/**
 *
 * @author gstafford
 */
public abstract class Relationships {

    /**
     *
     * @param server
     */
    public static void define(RestExpress server) {
        Map<String, String> routes = server.getRouteUrlsByName();

        HyperExpress.relationships()
                .forCollectionOf(Vehicle.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.VEHICLE_COLLECTION))
                .withQuery("limit={limit}")
                .withQuery("offset={offset}")
                .rel(RelTypes.NEXT, routes.get(Constants.Routes.VEHICLE_COLLECTION) + "?offset={nextOffset}")
                .withQuery("limit={limit}")
                .optional()
                .rel(RelTypes.PREV, routes.get(Constants.Routes.VEHICLE_COLLECTION) + "?offset={prevOffset}")
                .withQuery("limit={limit}")
                .optional()
                .forClass(Vehicle.class)
                .rel(RelTypes.SELF, routes.get(Constants.Routes.SINGLE_VEHICLE))
                .rel(RelTypes.UP, routes.get(Constants.Routes.VEHICLE_COLLECTION));
    }
}
