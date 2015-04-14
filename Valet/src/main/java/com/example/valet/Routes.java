package com.example.valet;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/valets/{oid}.{format}", config.getValetController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_VALET);

        server.uri("/valets.{format}", config.getValetController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.VALET_COLLECTION);
    }
}
