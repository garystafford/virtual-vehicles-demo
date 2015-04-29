package com.example.authentication;

import io.netty.handler.codec.http.HttpMethod;

import org.restexpress.RestExpress;

public abstract class Routes {

    public static void define(Configuration config, RestExpress server) {
        // Get API key
        server.uri("/virtual/auth/apikey", config.getSampleController())
                .action("createApiKey", HttpMethod.GET);
        // Get JWT by passing API key
        server.uri("/virtual/auth/jwt", config.getSampleController())
                .action("createJwt", HttpMethod.GET);
        // Validate JWT by passing JWT
        server.uri("/virtual/auth/jwt/{jwt}", config.getSampleController())
                .action("validateJwt", HttpMethod.GET);
    }
}
