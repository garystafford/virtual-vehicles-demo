package com.example.maintenance;

import io.netty.handler.codec.http.HttpMethod;
import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/maintenances/{oid}.{format}", config.getRecordController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_RECORD);

        server.uri("/maintenances.{format}", config.getRecordController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.RECORD_COLLECTION);

        server.uri("/maintenances/utils/ping.{format}", config.getDiagnosticController())
                .action("ping", HttpMethod.GET);
    }
}
