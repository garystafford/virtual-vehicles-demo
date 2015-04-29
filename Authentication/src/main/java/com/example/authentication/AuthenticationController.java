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

public class AuthenticationController {

    static final String MOCK_API_KEY = "e0a7ace4-a19c-4952-89ce-882a4109194f";
    static final String JWT_SECRET = "super secret";

    public AuthenticationController() {
        super();
    }

    public Object createApiKey(Request request, Response response) {
        // Mocking out the creation and storage of API Key
        // for virtual-vehicles.com with a static uuid
        // https://www.famkruithof.net/uuid/uuidgen
        return MOCK_API_KEY;
    }

    public Object createJwt(Request request, Response response)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            SignatureException, JWTVerifyException {
        String jwt;
        // http://www.epochconverter.com/
        long epoch_now = System.currentTimeMillis() / 1000;
        long epoch_expire = epoch_now + 86400; // plus 1 day

        try {
            String apikey = request.getQueryStringMap().get(Constants.Url.API_KEY);
            if (apikey == null) {
                return "No API key supplied";
            }
            JWTSigner jwts = new JWTSigner(JWT_SECRET);
            Map<String, Object> payload = new HashMap<>();
//            payload.put("iss", "example-api.com");
//            payload.put("sub", "Virtual Vehicles Application");
//            payload.put("aud", "virtual-vehicles.com");
            payload.put("ait", epoch_now);
            payload.put("exp", epoch_expire);
            payload.put("api-key", apikey);

            jwt = jwts.sign(payload);
        } catch (IllegalStateException illegalStateException) {
            return "Invalid Token! " + illegalStateException;
        }
        return jwt;
    }

    public Object validateJwt(Request request, Response response)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            SignatureException, JWTVerifyException {
        try {
            String jwt = request.getHeader(Constants.Url.JWT, "No JWT supplied");

            Map<String, Object> decodedPayload
                    = new JWTVerifier(JWT_SECRET).verify(jwt);

            if (decodedPayload.get("api-key").equals(MOCK_API_KEY)
                    && Long.parseLong(decodedPayload.get("exp").toString())
                    > System.currentTimeMillis() / 1000) {
                return true;
            }
        } catch (IllegalStateException illegalStateException) {
            return false;
        }

        return false;
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
