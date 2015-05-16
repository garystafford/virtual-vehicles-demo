package com.example.utilities;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.restexpress.Request;
import org.restexpress.Response;

/**
 *
 * @author gstafford
 */
public class DiagnosticController {

    /**
     *
     */
    public DiagnosticController() {
        super();
    }

    /**
     *
     * @param request
     * @param response
     */
    public void ping(Request request, Response response) {
        response.setResponseStatus(HttpResponseStatus.OK);
        response.setResponseCode(200);
        response.setBody(true);
    }
}
