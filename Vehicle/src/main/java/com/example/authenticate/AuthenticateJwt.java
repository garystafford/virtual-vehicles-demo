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

    private static Logger log = LogManager.getLogger(AuthenticateJwt.class.getName());

    public static boolean authenticateJwt(Request request, String baseUrl, int authPort) {
        String jwt;

        try {
            log.info("request.getUrl(): " + request.getUrl());
            jwt = (request.getHeader("Authorization").split(" "))[1];
            if (jwt == null) {
                log.error("request.getHeader(\"Authorization\")... failed: JWT is null");
                return false;
            }
        } catch (NullPointerException e) {
            log.error("request.getHeader(\"Authorization\")... failed: "
                    + ExceptionUtils.getRootCauseMessage(e));
            log.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }

        try {
            URL url = new URL(baseUrl + ":" + authPort + "/jwts/" + jwt);
            log.info("Authentication service URL called: " + url);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                log.error(conn.getResponseCode() + ": "
                        + conn.getResponseMessage());
                return false;

            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            log.info("Output from Server:");
            while ((output = br.readLine()) != null) {
                log.info(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            log.error(ExceptionUtils.getRootCauseMessage(e));
            log.debug(ExceptionUtils.getStackTrace(e));
            return false;

        } catch (IOException e) {
            log.error(ExceptionUtils.getRootCauseMessage(e));
            log.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }
        return true;
    }
}
