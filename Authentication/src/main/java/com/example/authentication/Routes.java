package com.example.authentication;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        // Create a new JWT by passing API key and secret
        server.uri("/jwts.{format}", config.getJwtController())
                .action("createJwt", HttpMethod.GET);

        // Validate a JWT by passing JWT
        server.uri("/jwts/{jwt}.{format}", config.getJwtController(config.getBaseUrlAndPort()))
                .action("validateJwt", HttpMethod.GET);

        // Read, modify, delete single client
        server.uri("/clients/{oid}.{format}", config.getClientController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_CLIENT);

        // Find client using queryfilter
        server.uri("/clients/utils/find.{format}", config.getClientController())
                .action("find", HttpMethod.GET)
                .name(Constants.Routes.CLIENT_FIND);

                // Find client secret using queryfilter for apiKey
        server.uri("/clients/utils/find/secrets.{format}", config.getClientController())
                .action("findClientSecret", HttpMethod.GET)
                .name(Constants.Routes.CLIENT_FIND_SECRET);

        // Find client count using queryfilter
        server.uri("/clients/utils/count.{format}", config.getClientController())
                .action("count", HttpMethod.GET)
                .name(Constants.Routes.CLIENT_COUNT);

        // Create and aead all clients
        server.uri("/clients.{format}", config.getClientController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.CLIENT_COLLECTION);
    }
}
