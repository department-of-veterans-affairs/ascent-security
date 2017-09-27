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
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author rthota
 */
public class JwtAuthenticationEntryPointTest {
    
    public JwtAuthenticationEntryPointTest() {
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
     * Test of commence method, of class JwtAuthenticationEntryPoint.
     */
    @Test
    public void testCommence() throws Exception {
        System.out.println("commence");
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        AuthenticationException authException = null;
        JwtAuthenticationEntryPoint instance = new JwtAuthenticationEntryPoint();
        //instance.commence(request, response, authException);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }
    
}
