package com.example.authentication;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        //TODO: Your routes here...
        server.uri("/auth0/apikey", config.getSampleController())
                .action("createApiKey", HttpMethod.GET)
                .name(Constants.Routes.SINGLE_SAMPLE);

        server.uri("/auth0/jwt", config.getSampleController())
                .action("createJwt", HttpMethod.GET)
                .name(Constants.Routes.SINGLE_SAMPLE);

//		server.uri("/your/route/here.{format}", config.getSampleController())
//			.action("readAll", HttpMethod.GET)
//			.method(HttpMethod.POST)
//			.name(Constants.Routes.SAMPLE_COLLECTION);
// or...
//		server.regex("/some.regex", config.getRouteController());
    }
}
