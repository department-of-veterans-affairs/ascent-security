/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

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
        String[] ignoreUrls = {"http://localhost:8762/api/ascent-demo-service/swagger-ui.html"};

        IgnoredRequestMatcher instance = new IgnoredRequestMatcher("baselineMatches",  ignoreUrls);
        
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
        request.addHeader("Authorization", "Bearer "+ GenerateToken.generateJwt());    
        boolean expResult = false;
        boolean result = instance.matches(request);
        assertEquals(expResult, result);
    }
    
    
    /**
     * Test of matches method, of class IgnoredRequestMatcher.
     */
    @Test
    public void testBaselineMatches() {
        RequestMatcher ignoreReqMatch = new AntPathRequestMatcher("http://localhost:8762/api/ascent-demo-service/swagger-ui.htmls");
        RequestMatcher baselineMatches = new AntPathRequestMatcher("http://localhost:8762/api");
        IgnoredRequestMatcher instance = new IgnoredRequestMatcher(baselineMatches,  ignoreReqMatch);
        
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setServletPath("http://localhost:8762/api");
        request.addHeader("Authorization", "Bearer "+ GenerateToken.generateJwt());    
        boolean expResult = true;
        boolean result = instance.matches(request);
        assertEquals(expResult, result);
    }
}
