/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;

import org.junit.Test;
import org.junit.runner.RunWith;
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
		final String username = "jdoe";
		final PersonTraits person = new PersonTraits();
		person.setFirstName("john");
		person.setLastName("doe");
		final String token = tokenResource.getToken(person);

		final JwtAuthenticationToken authentication = new JwtAuthenticationToken(token);
		final JwtParser parser = new JwtParser(jwtAuthenticationProperties);
		final JwtAuthenticationProvider instance = new JwtAuthenticationProvider(parser);
		final UserDetails result = instance.retrieveUser(username, authentication);
		assertNotNull(result);
	}

	@Test
	public void testRetrieveUser_NoPerson() {
		final String username = "jdoe";
		final PersonTraits person = new PersonTraits();
		person.setFirstName("john");
		person.setLastName("doe");
		final String token = tokenResource.getToken(person);

		final JwtAuthenticationToken authentication = new JwtAuthenticationToken(token);
		final JwtParser parser = spy(new JwtParser(jwtAuthenticationProperties));
		final JwtAuthenticationProvider instance = new JwtAuthenticationProvider(parser);
		doReturn(null).when(parser).parseJwt(any());

		UserDetails result = null;
		try {
			result = instance.retrieveUser(username, authentication);
			fail("Should have thrown JwtAuthenticationException.");
		} catch (final Exception e) {
			assertTrue(JwtAuthenticationException.class.isAssignableFrom(e.getClass()));
			assertTrue("Invalid Token".equals(e.getMessage()));
		}
		assertNull(result);
	}

	/**
	 * Test of retrieveUser method, of class JwtAuthenticationProvider.
	 */
	@Test(expected = MalformedJwtException.class)
	public void testRetrieveUserWithException() {

		final JwtAuthenticationToken authentication = new JwtAuthenticationToken("test");
		final JwtParser parser = new JwtParser(jwtAuthenticationProperties);
		final JwtAuthenticationProvider instance = new JwtAuthenticationProvider(parser);
		instance.retrieveUser("username", authentication);
	}
}
