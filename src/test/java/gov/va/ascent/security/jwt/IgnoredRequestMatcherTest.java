/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import gov.va.ascent.security.util.GenerateToken;

import static org.junit.Assert.*;

/**
 *
 * @author rthota
 */
public class IgnoredRequestMatcherTest {
    
    public IgnoredRequestMatcherTest() {
    }
   
    /**
     * Test of matches method, of class IgnoredRequestMatcher.
     */
    @Test
    public void testMatches() {
        System.out.println("matches");
        String[] ignoreUrls = {"http://localhost:8762/api/ascent-demo-service/swagger-ui.html"};

        IgnoredRequestMatcher instance = new IgnoredRequestMatcher("baselineMatches",  ignoreUrls);
        
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
        request.addHeader("Authorization", "Bearer "+ GenerateToken.generateJwt());    
        boolean expResult = false;
        boolean result = instance.matches(request);
        assertEquals(expResult, result);
    }
    
}
