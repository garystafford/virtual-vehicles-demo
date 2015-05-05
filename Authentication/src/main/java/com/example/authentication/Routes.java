package com.example.authentication;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        // Get JWT by passing API key
        server.uri("/virtual/jwts", config.getJwtController())
                .action("createJwt", HttpMethod.GET);

        // Validate JWT by passing JWT
        server.uri("/virtual/jwt/{jwt}", config.getJwtController())
                .action("validateJwt", HttpMethod.GET);

        // Read, modify, delete single client
        server.uri("/virtual/client/{oid}.{format}", config.getClientController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_CLIENT);

        // Find client using queryfilter
        server.uri("/virtual/clients/find.{format}", config.getClientController())
                .action("find", HttpMethod.GET)
                .name(Constants.Routes.CLIENT_FIND);

        // Find client count using queryfilter
        server.uri("/virtual/clients/count.{format}", config.getClientController())
                .action("count", HttpMethod.GET)
                .name(Constants.Routes.CLIENT_COUNT);

        // Create and aead all clients
        server.uri("/virtual/clients.{format}", config.getClientController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.CLIENT_COLLECTION);
    }
}
