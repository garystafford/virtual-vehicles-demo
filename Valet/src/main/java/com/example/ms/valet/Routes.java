package com.example.ms.valet;

import org.jboss.netty.handler.codec.http.HttpMethod;

import com.strategicgains.restexpress.RestExpress;
import com.example.ms.valet.config.Configuration;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        server.uri("/virtualvehicle/valet/{valetId}.{format}",
                config.getValetController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.VALET_INSTANCE);

        server.uri("/virtualvalet/valets.{format}",
                config.getValetController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.VALET_COLLECTION);
    }
}
