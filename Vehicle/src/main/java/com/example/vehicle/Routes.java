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

        server.uri("/vehicles/utils/ping.{format}", config.getDiagnosticController())
                .action("ping", HttpMethod.GET);
    }
}
