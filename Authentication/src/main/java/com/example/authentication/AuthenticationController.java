package com.example.authentication;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.restexpress.Request;
import org.restexpress.Response;
import org.apache.commons.lang.RandomStringUtils;

public class AuthenticationController {

    public AuthenticationController() {
        super();
    }

    public Object createApiKey(Request request, Response response) {
        // Mocking out the creation and storage of api_key
        // for virtual-vehicles.com
        
        // String api_key = RandomStringUtils.randomAlphanumeric(24);
        
        return "e0a7ace4-a19c-4952-89ce-882a4109194f"; //uuid
    }

    public Object createJwt(Request request, Response response)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            SignatureException, JWTVerifyException {
        String jwt;
        // http://www.epochconverter.com/
        long epoch_now = System.currentTimeMillis() / 1000;
        long epoch_expire = epoch_now + 86400; // plus 1 day

        try {

            JWTSigner jwts = new JWTSigner("secret goes here!");
            Map<String, Object> payload = new HashMap<>();
//            payload.put("iss", "example-api.com");
//            payload.put("sub", "Virtual Vehicles Application");
//            payload.put("aud", "virtual-vehicles.com");
            payload.put("ait", epoch_now);
            payload.put("exp", epoch_expire);
            payload.put("api-key", request.getQueryStringMap().get("api_key"));

            jwt = jwts.sign(payload);
//            System.out.println(jwt);
//            Map<String, Object> decodedPayload
//                    = new JWTVerifier("super secret goes here!").verify(jwt);
//            System.out.println(decodedPayload);
        } catch (IllegalStateException illegalStateException) {
            return "Invalid Token! " + illegalStateException;
        }
        return jwt;
    }

    public Object read(Request request, Response response) {
        //TODO: Your 'GET' logic here...
        return null;
    }

    public List<Object> readAll(Request request, Response response) {
        //TODO: Your 'GET collection' logic here...
        return Collections.emptyList();
    }

    public void update(Request request, Response response) {
        //TODO: Your 'PUT' logic here...
        response.setResponseNoContent();
    }

    public void delete(Request request, Response response) {
        //TODO: Your 'DELETE' logic here...
        response.setResponseNoContent();
    }
}
