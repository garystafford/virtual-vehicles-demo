package com.example.maintenance;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/maintenance/records/{oid}.{format}", config.getRecordController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_RECORD);

        server.uri("/maintenance/records.{format}", config.getRecordController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.RECORD_COLLECTION);
    }
}
