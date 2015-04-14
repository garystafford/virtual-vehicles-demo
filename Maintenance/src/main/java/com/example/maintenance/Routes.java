package com.example.maintenance;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/maintenances/{oid}.{format}", config.getMaintenanceController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_MAINTENANCE);

        server.uri("/maintenances.{format}", config.getMaintenanceController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MAINTENANCE_COLLECTION);
    }
}
