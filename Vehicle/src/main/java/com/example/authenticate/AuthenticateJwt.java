//http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
package com.example.authenticate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AuthenticateJwt {

    public static void main(String[] args) {
        System.out.println(authenticate());
    }

    private static boolean authenticate() throws RuntimeException {
        try {
            String jwt = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJhcGkta2V5Ijoi"
                    + "ZTBhN2FjZTQtYTE5Yy00OTUyLTg5Y2UtODgyYTQxMDkxOTRmIiwiZXhwI"
                    + "joxNDMwNDQ4NTAxLCJhaXQiOjE0MzAzNjIxMDF9.lB5fRz5IqLsNzpNFL"
                    + "3aFlJKROKaqhMrw4llOUzDDv1k";

            URL url = new URL("http://localhost:8587/virtual/auth/jwt/" + jwt);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.out.println(conn.getResponseCode());
                return false;
//                throw new RuntimeException("Failed : HTTP error code : "
//                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                //System.out.println(output);
            }
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
