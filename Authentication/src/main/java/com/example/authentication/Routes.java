package com.example.authentication;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

/**
 *
 * @author gstafford
 */
public abstract class Routes {

    /**
     *
     * @param config
     * @param server
     */
    public static void define(Configuration config, RestExpress server) {
        // Create a new JWT by passing API key and secret
        server.uri("/jwts.{format}", config.getJwtController())
                .action("createJwt", HttpMethod.GET);

        // Validate a JWT by passing JWT
        server.uri("/jwts/{jwt}", config.getJwtController())
                .action("validateJwt", HttpMethod.GET);

        // Read, modify, delete single client
        server.uri("/clients/{oid}.{format}", config.getClientController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_CLIENT);

        // Create client, read all clients
        server.uri("/clients.{format}", config.getClientController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.CLIENT_COLLECTION);

        // Find client secret based on API key filter - INTERNAL USE ONLY
        server.uri("/clients/find/secret.{format}", config.getClientController())
                .action("findClientSecret", HttpMethod.GET);

        server.uri("/clients/utils/ping.{format}", config.getDiagnosticController())
                .action("ping", HttpMethod.GET);
    }
}
