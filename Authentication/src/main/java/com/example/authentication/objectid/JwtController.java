package com.example.authentication.objectid;

import com.auth0.jwt.JWTSigner;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.JWTVerifyException;
import com.auth0.jwt.internal.org.apache.commons.codec.binary.Base64;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.restexpress.Request;
import org.restexpress.Response;

import com.example.authentication.Constants;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JwtController {

    public JwtController() {
        super();
    }

    public Object createJwt(Request request, Response response)
            throws NoSuchAlgorithmException, InvalidKeyException, IOException,
            SignatureException, JWTVerifyException {
        String apikey;
        String secret;
        String jwt;
        // http://www.epochconverter.com/
        long epoch_now = System.currentTimeMillis() / 1000;
        long epoch_expire = epoch_now + 86400; // plus 1 day

        try {
            apikey = request.getQueryStringMap().get(Constants.Url.API_KEY);
            if (apikey == null) {
                return "No API key supplied";
            }
            secret = request.getQueryStringMap().get(Constants.Url.SECRET);
            if (secret == null) {
                return "No Secret supplied";
            }
            JWTSigner jwts = new JWTSigner(secret);
            Map<String, Object> payload = new HashMap<>();
            payload.put("iss", Constants.Url.ISS);
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
            SignatureException, JWTVerifyException, ParseException {
        try {
            String jwt = request.getHeader(Constants.Url.JWT, "No JWT supplied");
            String alg = getSignatureAlgorithm(jwt);
            String apiKey = getApiKey(jwt);
            String secret = getSecret(apiKey);
            Map<String, Object> decodedPayload
                    = new JWTVerifier(secret).verify(jwt);

            if (alg.equals("HS256")
                    && Long.parseLong(decodedPayload.get("exp").toString())
                    > System.currentTimeMillis() / 1000) {
                return true;
            }
        } catch (IllegalStateException illegalStateException) {
            return false;
        }

        return false;
    }

    private String getApiKey(String jwt) throws ParseException,
            UnsupportedEncodingException {
        String[] base64EncodedSegments = jwt.split(Pattern.quote("."));
        String base64EncodedClaims = base64EncodedSegments[1];
        byte[] decoded = Base64.decodeBase64(base64EncodedClaims);
        String claims = new String(decoded, "UTF-8") + "\n";
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(claims);
        String apiKey = json.get("api-key").toString();
        return apiKey;
    }

    private String getSignatureAlgorithm(String jwt) throws ParseException,
            UnsupportedEncodingException {
        String[] base64EncodedSegments = jwt.split(Pattern.quote("."));
        String base64EncodedHeader = base64EncodedSegments[0];
        byte[] decoded = Base64.decodeBase64(base64EncodedHeader);
        String header = new String(decoded, "UTF-8") + "\n";
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(header);
        String alg = json.get("alg").toString();
        return alg;
    }

    private static String getSecret(String apiKey) throws RuntimeException {
        String output;
        String secret = null;
        try {
            URL url = new URL("http://localhost:8587/virtual/clients/find");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("filter", "apiKey::" + apiKey);

            if (conn.getResponseCode() != 200) {
                System.out.println(conn.getResponseCode());
                return String.valueOf("Failed : HTTP error code : "
                        + conn.getResponseCode());
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            while ((output = br.readLine()) != null) {
                secret = output;
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            return e.getMessage();

        } catch (IOException e) {
            return e.getMessage();
        }
        return secret.replaceAll("\"", "");
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
