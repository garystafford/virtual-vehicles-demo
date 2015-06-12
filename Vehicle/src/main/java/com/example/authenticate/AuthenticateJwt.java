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

/**
 *
 * @author gstafford
 */
public class AuthenticateJwt {

    private static final Logger LOG = LogManager.getLogger(AuthenticateJwt.class.getName());

    /**
     *
     * @param request
     * @param authUrlAndAuthPort
     * @return
     */
    public boolean authenticateJwt(Request request, String authUrlAndAuthPort) {
        String jwt, output, valid = "";

        try {
            LOG.info("request.getUrl(): " + request.getUrl());
            jwt = (request.getHeader("Authorization").split(" "))[1];
        } catch (NullPointerException | ArrayIndexOutOfBoundsException e) {
            LOG.error("request.getHeader(\"Authorization\")... failed: "
                    + ExceptionUtils.getRootCauseMessage(e));
            LOG.debug(ExceptionUtils.getStackTrace(e));
            return false;
        }

        try {
            URL url = new URL("http://" + authUrlAndAuthPort + "/jwts/" + jwt);
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

            LOG.info("Output from Server:");
            while ((output = br.readLine()) != null) {
                valid = output;
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
        return Boolean.parseBoolean(valid);
    }
}
