/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.security.core.Authentication;

/**
 *
 * @author rthota
 */
public class JwtAuthenticationSuccessHandlerTest {
    
    public JwtAuthenticationSuccessHandlerTest() {
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
     * Test of onAuthenticationSuccess method, of class JwtAuthenticationSuccessHandler.
     */
    @Test
    public void testOnAuthenticationSuccess() throws Exception {
        System.out.println("onAuthenticationSuccess");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        Authentication authentication = null;
        JwtAuthenticationSuccessHandler instance = new JwtAuthenticationSuccessHandler();
        instance.onAuthenticationSuccess(request, response, authentication);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
