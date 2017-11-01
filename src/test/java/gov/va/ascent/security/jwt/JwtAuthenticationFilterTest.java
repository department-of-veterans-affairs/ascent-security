/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.config.AscentSecurityTestConfig;
import gov.va.ascent.security.handler.JwtAuthenticationSuccessHandler;
import gov.va.ascent.security.util.GenerateToken;

/**
 *
 * @author rthota
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AscentSecurityTestConfig.class)
public class JwtAuthenticationFilterTest {

    @Autowired
    JwtAuthenticationProperties properties;

    @Autowired
    AuthenticationProvider provider;

    @Test
    public void testNormalOperation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
        request.addHeader("Authorization", "Bearer "+ GenerateToken.generateJwt());

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(properties,
                new JwtAuthenticationSuccessHandler(),provider);

        Authentication result = filter.attemptAuthentication(request,
                new MockHttpServletResponse());
        Assert.assertTrue(result != null);
        Assert.assertTrue((((PersonTraits) result.getPrincipal())).getFirstName().equalsIgnoreCase(GenerateToken.person().getFirstName()));
    }
    
    
    @Test(expected = JwtAuthenticationException.class) 
    public void testExceptionOperation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
        request.addHeader("Authorization", "Bearers "+ GenerateToken.generateJwt());

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(properties,
                new JwtAuthenticationSuccessHandler(),provider);

        Authentication result = filter.attemptAuthentication(request,
                new MockHttpServletResponse());
    }

    @Test
    public void testTamperedException() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
        String content = "{\n" + 
        		"  \"participantID\": 0,\n" + 
        		"  \"ssn\": \"string\"\n" + 
        		"}";
        request.setContent(content.getBytes());
        request.addHeader("Authorization", "Bearer "+ GenerateToken.generateJwt() + "s");

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(properties,
                new JwtAuthenticationSuccessHandler(),provider);

        try {
			Authentication result = filter.attemptAuthentication(request,
			        new MockHttpServletResponse());
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Tampered"));
		}
    }
    
    
    @Test
    public void testMalformedException() throws Exception {
    	 MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
         String content = "{\n" + 
         		"  \"participantID\": 0,\n" + 
         		"  \"ssn\": \"string\"\n" + 
         		"}";
        request.setContent(content.getBytes());
        request.addHeader("Authorization", "Bearer malformedToken");

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(properties,
                new JwtAuthenticationSuccessHandler(),provider);
        try {
			Authentication result = filter.attemptAuthentication(request,
			        new MockHttpServletResponse());
		} catch (Exception e) {
			Assert.assertTrue(e.getMessage().contains("Malformed"));
		}
    }
}
