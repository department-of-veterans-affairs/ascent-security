/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import gov.va.ascent.security.config.AscentSecurityTestConfig;

import static org.junit.Assert.*;

/**
 *
 * @author rthota
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AscentSecurityTestConfig.class)
public class JwtAuthenticationPropertiesTest {
    
	@Autowired
	JwtAuthenticationProperties jwtAuthenticationProperties;
	
    public JwtAuthenticationPropertiesTest() {
    }
    
    /**
     * Test of isEnabled method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testIsEnabled() {
        System.out.println("isEnabled");
        boolean expResult = true;
        boolean result = jwtAuthenticationProperties.isEnabled();
        assertEquals(expResult, result);
    }

    /**
     * Test of setEnabled method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testSetEnabled() {
        System.out.println("setEnabled");
        boolean enabled = true;
        jwtAuthenticationProperties.setEnabled(enabled);
        assertTrue(jwtAuthenticationProperties.isEnabled());
    }

    /**
     * Test of getHeader method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testGetHeader() {
        System.out.println("getHeader");
        String expResult = "Authorization";
        String result = jwtAuthenticationProperties.getHeader();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of getSecret method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testGetSecret() {
        System.out.println("getSecret");
        JwtAuthenticationProperties jwtAuthenticationProperties = new JwtAuthenticationProperties();
        String expResult = "secret";
        String result = jwtAuthenticationProperties.getSecret();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of setSecret method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testSetSecret() {
        System.out.println("setSecret");
        String secret = "secret";
        jwtAuthenticationProperties.setSecret(secret);
        assertTrue(secret.equals(jwtAuthenticationProperties.getSecret()));
    }

    /**
     * Test of getFilterProcessUrl method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testGetFilterProcessUrl() {
        System.out.println("getFilterProcessUrl");
        String expResult = "url";
        String result = jwtAuthenticationProperties.getFilterProcessUrl();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of setFilterProcessUrl method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testSetFilterProcessUrl() {
        System.out.println("setFilterProcessUrl");
        String filterProcessUrl = "url";
        jwtAuthenticationProperties.setFilterProcessUrl(filterProcessUrl);
        assertTrue(filterProcessUrl.equals(jwtAuthenticationProperties.getFilterProcessUrl()));
    }

    /**
     * Test of getExcludeUrls method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testGetExcludeUrls() {
        System.out.println("getExcludeUrls");
        String[] result = jwtAuthenticationProperties.getExcludeUrls();
        assertTrue(result.length > 0);
    }

    /**
     * Test of setExcludeUrls method, of class JwtAuthenticationProperties.
     */
    @Test
    public void testSetExcludeUrls() {
        System.out.println("setExcludeUrls");
        String[] excludeUrls = {"http://localhost:8762/api/ascent-demo-service/swagger-ui.html"};
        jwtAuthenticationProperties.setExcludeUrls(excludeUrls);
        assertTrue(jwtAuthenticationProperties.getExcludeUrls().length > 0);
    }
    
}
