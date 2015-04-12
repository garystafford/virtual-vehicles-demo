package com.example.restexpmongomvn;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.RestExpress;
import com.example.restexpmongomvn.config.Configuration;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        //TODO: Your routes here...

        server.uri("/rest/test/sample/{sampleId}.{format}",
                config.getSampleController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_SAMPLE);

        server.uri("/rest/test/samples.{format}",
                config.getSampleController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.SAMPLE_COLLECTION);

        server.uri("/restexpress/vehicle/{vehicleId}.{format}",
                config.getVehicleController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.VEHICLE_INSTANCE);

        server.uri("/restexpress/vehicles.{format}",
                config.getVehicleController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.VEHICLE_COLLECTION);

// or REGEX matching routes...
//		server.regex("/some.regex", config.getRouteController());
    }
}
