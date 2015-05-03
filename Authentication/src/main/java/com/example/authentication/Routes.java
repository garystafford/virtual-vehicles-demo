package com.example.authentication;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
//        server.uri("/virtual/auth/apikey", config.getClientController())
//                .action("createApiKey", HttpMethod.GET);

        // Get JWT by passing API key
        server.uri("/virtual/auth/jwt", config.getJwtController())
                .action("createJwt", HttpMethod.GET);

        // Validate JWT by passing JWT
        server.uri("/virtual/auth/jwt/{jwt}", config.getJwtController())
                .action("validateJwt", HttpMethod.GET);

        server.uri("/virtual/client/{oid}.{format}", config.getClientController())
                .method(HttpMethod.GET, HttpMethod.PUT, HttpMethod.DELETE)
                .name(Constants.Routes.SINGLE_CLIENT);

        server.uri("/virtual/clients.{format}", config.getClientController())
                .action("readAll", HttpMethod.GET)
                .method(HttpMethod.POST)
                .name(Constants.Routes.CLIENT_COLLECTION);
    }
}
