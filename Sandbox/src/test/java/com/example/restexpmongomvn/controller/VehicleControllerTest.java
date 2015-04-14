/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.restexpmongomvn.controller;

import static org.junit.Assert.assertEquals;
import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.strategicgains.restexpress.RestExpress;
import com.example.restexpmongomvn.Main;
import com.example.restexpmongomvn.domain.Color;
import com.example.restexpmongomvn.domain.Vehicle;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.strategicgains.hyperexpress.domain.LinkableCollection;
import com.strategicgains.restexpress.Request;
import com.strategicgains.restexpress.Response;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import static org.junit.Assert.fail;

/**
 *
 * @author gstafford
 */
public class VehicleControllerTest {

    private static RestExpress server;
    private HttpClient httpClient;
    private static final String BASE_URL = "http://localhost:8587";

    /**
     *
     */
    public VehicleControllerTest() {
    }

    /**
     *
     * @throws IOException
     */
    @BeforeClass
    public static void setUpClass() throws IOException {
        String[] env = {"dev"};
        server = Main.initializeServer(env);
    }

    /**
     *
     */
    @AfterClass
    public static void tearDownClass() {
        server.shutdown();
    }

    /**
     *
     */
    @Before
    public void beforeEach() {
        httpClient = new DefaultHttpClient();
    }

    /**
     *
     */
    @After
    public void afterEach() {
        httpClient = null;
    }

    /**
     *
     * @throws IOException
     * @deprecated
     */
    @Test
    @Deprecated
    public void postDirectiveReplayRequest() throws IOException {
        HttpGet getRequest = new HttpGet(BASE_URL + "/rest/test/sample/123.json");
        final HttpResponse response = httpClient.execute(getRequest);
        assertEquals(400, response.getStatusLine().getStatusCode());
    }

    /**
     * Test of create method, of class VehicleController.
     *
     * @throws java.io.IOException
     */
    @Test
    public void testCreate() throws IOException {
        System.out.println("create");

        Vehicle testVehicle = new Vehicle(2015, "Test", "Vehicle",
                Color.Red, "4-Door Sedan");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        String testVehicleString = objectMapper.writeValueAsString(testVehicle);
//        System.out.println(testVehicleString);

        StringEntity postEntity = new StringEntity(testVehicleString,
                ContentType.create("application/json", "UTF-8"));
        postEntity.setChunked(true);

//        System.out.println(postEntity.getContentType());
//        System.out.println(postEntity.getContentLength());
//        System.out.println(EntityUtils.toString(postEntity));
//        System.out.println(EntityUtils.toByteArray(postEntity).length);
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(BASE_URL + "/restexpress/vehicles");
        httppost.setEntity(postEntity);
        CloseableHttpResponse response = httpclient.execute(httppost);

        String responseString = new BasicResponseHandler().handleResponse(response);
        assertEquals(parseMongoId(responseString), true);
    }

    private boolean parseMongoId(String responseString) {
        String responsePattern = "^\"[0-9a-fA-F]{24}\"$";

        // Create a Pattern object
        Pattern pattern = Pattern.compile(responsePattern);

        // Now create matcher object.
        Matcher matcher = pattern.matcher(responseString);
        if (matcher.find()) {
            //System.out.println("Found value: " + m.group(0));
            return true;
        } else {
            //System.out.println("NO MATCH");
        }
        return false;
    }

    /**
     * Test of read method, of class VehicleController.
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     * @throws java.io.IOException
     */
    @Test
    public void testRead() throws JsonProcessingException, IOException {
        System.out.println("read");

        Vehicle testVehicle = new Vehicle(2015, "Test", "Vehicle",
                Color.Red, "4-Door Sedan");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(Include.NON_EMPTY);
        String testVehicleString = objectMapper.writeValueAsString(testVehicle);

        StringEntity postEntity = new StringEntity(testVehicleString,
                ContentType.create("application/json", "UTF-8"));
        postEntity.setChunked(true);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httppost = new HttpPost(BASE_URL + "/restexpress/vehicles");
        httppost.setEntity(postEntity);
        CloseableHttpResponse response = httpclient.execute(httppost);

        String responseString = new BasicResponseHandler().handleResponse(response);
        responseString = responseString.replaceAll("^\"|\"$", "");

        // Get vehicle based on MongoDB id
        HttpGet httpget = new HttpGet(BASE_URL + "/restexpress/vehicle/" + responseString);
        CloseableHttpResponse response2 = httpclient.execute(httpget);

        String responseString2 = new BasicResponseHandler().handleResponse(response2);

        ResponseHandler<Vehicle> rh = new ResponseHandler<Vehicle>() {

            @Override
            public Vehicle handleResponse(
                    final HttpResponse response) throws IOException {
                StatusLine statusLine = response.getStatusLine();
                HttpEntity entity = response.getEntity();
                if (statusLine.getStatusCode() >= 300) {
                    throw new HttpResponseException(
                            statusLine.getStatusCode(),
                            statusLine.getReasonPhrase());
                }
                if (entity == null) {
                    throw new ClientProtocolException("Response contains no content");
                }
                Gson gson = new GsonBuilder()
                        .setPrettyPrinting()
                        .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                        .create();
                ContentType contentType = ContentType.getOrDefault(entity);
                Charset charset = contentType.getCharset();
                Reader reader = new InputStreamReader(entity.getContent(), "UTF-8");

//                String inputStreamString = new Scanner(entity.getContent(), "UTF-8").useDelimiter("\\A").next();
//                System.out.println(inputStreamString);

                return gson.fromJson(reader, Vehicle.class);
            }
        };
        Vehicle vehicle = httpclient.execute(httpget, rh);
        System.out.println("b");

//        MongodbEntityRepository<Vehicle> vehicleRepository;
//        VehicleController instance = new VehicleController(vehicleRepository);
//        Vehicle result = instance.read(request, response);
//        assertEquals(parseMongoId(responseString), true);
    }

    /**
     * Test of readAll method, of class VehicleController.
     */
    @Test
    @Deprecated
    public void testReadAll() {
        System.out.println("readAll");
        Request request = null;
        Response response = null;
        VehicleController instance = null;
        LinkableCollection<Vehicle> expResult = null;
        LinkableCollection<Vehicle> result = instance.readAll(request, response);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of update method, of class VehicleController.
     */
    @Test
    @Deprecated
    public void testUpdate() {
        System.out.println("update");
        Request request = null;
        Response response = null;
        VehicleController instance = null;
        instance.update(request, response);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delete method, of class VehicleController.
     */
    @Test
    @Deprecated
    public void testDelete() {
        System.out.println("delete");
        Request request = null;
        Response response = null;
        VehicleController instance = null;
        instance.delete(request, response);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

}
