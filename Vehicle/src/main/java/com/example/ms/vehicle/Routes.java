package com.example.ms.vehicle;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.RestExpress;
import com.example.ms.vehicle.config.Configuration;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/virtualvehicle/vehicle/{vehicleId}.{format}",
                config.getVehicleController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.VEHICLE_INSTANCE);

        server.uri("/virtualvehicle/vehicles.{format}",
                config.getVehicleController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.VEHICLE_COLLECTION);
    }
}
