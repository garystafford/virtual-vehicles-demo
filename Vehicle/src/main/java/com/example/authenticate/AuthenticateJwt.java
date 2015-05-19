//http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
package com.example.authenticate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.restexpress.Request;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.commons.lang3.exception.ExceptionUtils;

public class AuthenticateJwt {

    private static final Logger LOG = LogManager.getLogger(AuthenticateJwt.class.getName());

    public static boolean authenticateJwt(Request request) {
        String jwt;
        String baseUrl = System.getenv("VIRTUAL_VEHICLES_BASE_URL");
        String authPort = System.getenv("VIRTUAL_VEHICLES_AUTH_PORT");
        try {
            LOG.info("request.getUrl(): " + request.getUrl());
            jwt = (request.getHeader("Authorization").split(" "))[1];
            if (jwt == null) {
                LOG.error("request.getHeader(\"Authorization\")... failed: JWT is null");
                return false;
            }
        } catch (NullPointerException e) {
            LOG.error("request.getHeader(\"Authorization\")... failed: "
                    + ExceptionUtils.getRootCauseMessage(e));
            LOG.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }

        try {
            URL url = new URL(baseUrl + ":" + authPort + "/jwts/" + jwt);
            LOG.info("Authentication service URL called: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                LOG.error(conn.getResponseCode() + ": "
                        + conn.getResponseMessage());
                return false;

            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            LOG.info("Output from Server:");
            while ((output = br.readLine()) != null) {
                LOG.info(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            LOG.error(ExceptionUtils.getRootCauseMessage(e));
            LOG.debug(ExceptionUtils.getStackTrace(e));
            return false;

        } catch (IOException e) {
            LOG.error(ExceptionUtils.getRootCauseMessage(e));
            LOG.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }
}
