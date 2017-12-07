/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.config.AscentSecurityTestConfig;
import gov.va.ascent.security.jwt.JwtAuthenticationProperties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.springframework.web.bind.WebDataBinder;

/**
 *
 * @author rthota
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AscentSecurityTestConfig.class)
public class TokenResourceTest {

	@Autowired
	TokenResource tokenResource;

	@Autowired
	JwtAuthenticationProperties properties;

	@Autowired
	AuthenticationProvider provider;

	/**
	 * Test of getToken method, of class TokenResource.
	 */
	@Test
	public void testGetToken() {

		System.out.println("getToken");
		PersonTraits person = new PersonTraits();
		person.setFirstName("john");
		person.setLastName("doe");
		String result = tokenResource.getToken(person);
		assertTrue(result.length() > 0);

	}

    /**
     * Test of initBinder method, of class TokenResource.
     */
    @Test
    public void testInitBinder() {
        System.out.println("initBinder");
        WebDataBinder binder = new WebDataBinder(null, null);
        TokenResource instance = new TokenResource();
        instance.initBinder(binder);
        assertTrue(binder.getAllowedFields().length > 0);
    }
}
