/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.authenticate;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.restexpress.Request;

/**
 *
 * @author gstafford
 */
public class AuthenticateJwtTest {
    
    public AuthenticateJwtTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of authenticateJwt method, of class AuthenticateJwt.
     */
    @Test
    public void testAuthenticateJwt() {
        System.out.println("authenticateJwt");
        Request request = null;
        String baseUrlAndAuthPort = "";
        boolean expResult = false;
        boolean result = AuthenticateJwt.authenticateJwt(request, baseUrlAndAuthPort);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
