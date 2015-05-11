package com.example.vehicle;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

/**
 *
 * @author gstafford
 */
public abstract class Routes {

    /**
     *
     * @param config
     * @param server
     */
    public static void define(Configuration config, RestExpress server) {
        server.uri("/vehicles/{oid}.{format}", config.getVehicleController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_VEHICLE);

        server.uri("/vehicles.{format}", config.getVehicleController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.VEHICLE_COLLECTION);

        // Find vehicle using queryfilter
        server.uri("/vehicles/utils/find.{format}", config.getVehicleController())
                .action("find", HttpMethod.GET)
                .name(Constants.Routes.VEHICLE_FIND);

        // or REGEX matching routes...
        // server.regex("/some.regex", config.getRouteController());
    }
}
