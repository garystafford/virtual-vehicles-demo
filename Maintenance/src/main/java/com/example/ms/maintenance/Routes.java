package com.example.ms.maintenance;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.RestExpress;
import com.example.ms.maintenance.config.Configuration;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/virtualvehicle/maintenance/{maintenanceId}.{format}",
                config.getMaintenanceController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.MAINTENANCE_INSTANCE);

        server.uri("/virtualvehicle/maintenances.{format}",
                config.getMaintenanceController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.MAINTENANCE_COLLECTION);
    }
}
