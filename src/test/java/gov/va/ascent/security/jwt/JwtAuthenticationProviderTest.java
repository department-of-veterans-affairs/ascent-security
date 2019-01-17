/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import static gov.va.ascent.security.jwt.JwtAuthenticationProvider.isPersonTraitsValid;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.TokenResource;
import gov.va.ascent.security.config.AscentSecurityTestConfig;
import gov.va.ascent.security.model.Person;
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
		final Person person = new Person();
		person.setFirstName("john");
		person.setLastName("doe");
		person.setCorrelationIds(Arrays.asList(new String[] { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"600071516^PI^200CORP^USVBA^A", "1040626995^NI^200DOD^USDOD^A", "796046489^SS" }));
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
		final Person person = new Person();
		person.setFirstName("john");
		person.setLastName("doe");
		person.setCorrelationIds(Arrays.asList(new String[] { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"600071516^PI^200CORP^USVBA^A", "1040626995^NI^200DOD^USDOD^A", "796046489^SS" }));
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

	/**
	 * Test of method that validates PersonTraits in the Jwt token, of class JwtAuthenticationProvider.
	 */
	@Test
	public void testIsPersonTraitsValid() {
		PersonTraits person = new PersonTraits();
		person.setFirstName("string");
		assertTrue(isPersonTraitsValid(person, new String[] { "firstName" }));
		assertFalse(isPersonTraitsValid(person, new String[] { "firstName", "assuranceLevel" }));
	}

	/**
	 * Test of method that validates PersonTraits in the Jwt token, of class JwtAuthenticationProvider.
	 */
	@Test
	public void testIsPersonTraitsValidInvocationException() {
		PersonTraits person = Mockito.mock(PersonTraits.class);
		when(person.getFirstName()).thenThrow(new IllegalArgumentException());
		person.setFirstName("string");
		assertFalse(isPersonTraitsValid(person, new String[] { "firstName" }));
	}

	/**
	 * Test of method that validates PersonTraits in the Jwt token, of class JwtAuthenticationProvider.
	 */
	@Test
	public void testIsPersonTraitsValidWithInvalidJwtParameter() {
		PersonTraits person = new PersonTraits();
		person.setFirstName("string");
		assertFalse(isPersonTraitsValid(person, new String[] { "testText" }));
	}
}
