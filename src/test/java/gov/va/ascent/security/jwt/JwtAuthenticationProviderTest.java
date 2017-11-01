/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.TokenResource;
import gov.va.ascent.security.config.AscentSecurityTestConfig;
import io.jsonwebtoken.MalformedJwtException;

/**
 *
 * @author rthota
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AscentSecurityTestConfig.class)
public class JwtAuthenticationProviderTest {
    @Autowired
    JwtAuthenticationProperties jwtAuthenticationProperties;
    
	@Autowired
	TokenResource tokenResource;
	
    public JwtAuthenticationProviderTest() {
    }

    /**
     * Test of retrieveUser method, of class JwtAuthenticationProvider.
     */
    @Test
    public void testRetrieveUser() {
        System.out.println("retrieveUser");
        String username = "jdoe";
		PersonTraits person = new PersonTraits();
		person.setFirstName("john");
		person.setLastName("doe");
		String token = tokenResource.getToken(person);
		
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(token);
        JwtParser parser = new JwtParser(jwtAuthenticationProperties);
        JwtAuthenticationProvider instance = new JwtAuthenticationProvider(parser);
        UserDetails result = instance.retrieveUser(username, authentication);
        assertNotNull(result);
    }
    
    /**
     * Test of retrieveUser method, of class JwtAuthenticationProvider.
     */
    @Test(expected = MalformedJwtException.class) 
    public void testRetrieveUserWithException() {
        System.out.println("retrieveUser");
		
        JwtAuthenticationToken authentication = new JwtAuthenticationToken("test");
        JwtParser parser = new JwtParser(jwtAuthenticationProperties);
        JwtAuthenticationProvider instance = new JwtAuthenticationProvider(parser);    
        UserDetails result = instance.retrieveUser("username", authentication);
    }
}
