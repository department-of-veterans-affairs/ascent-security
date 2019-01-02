/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.util;

import org.junit.Test;

import gov.va.ascent.security.model.Person;

import static org.junit.Assert.*;

import java.util.Arrays;

/**
 *
 * @author rthota
 */
public class GenerateTokenTest {

	public GenerateTokenTest() {
	}

	/**
	 * Test of generateJwt method, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwt_0args() {
		String expResult = "";
		String result = GenerateToken.generateJwt();
		assertNotNull(expResult, result);
	}

	/**
	 * Test of generateJwt method, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwt_PersonTraits_String() {
		Person person = new Person();
		person.setCorrelationIds(Arrays.asList(new String[] { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"600071516^PI^200CORP^USVBA^A", "1040626995^NI^200DOD^USDOD^A", "796046489^SS" }));
		String secret = "test";
		String issuer = "Vets.gov";
		String result = GenerateToken.generateJwt(person, secret, issuer);
		assertNotNull(result);
	}

	/**
	 * Test of generateJwt method for the negative scenario of not having a Pid, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwtForFailureOnInvalidPid() {
		Person person = new Person();
		person.setCorrelationIds(Arrays.asList(new String[] { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"1040626995^NI^200DOD^USDOD^A", "796046489^SS" }));
		String secret = "test";
		String issuer = "Vets.gov";

		try {
			GenerateToken.generateJwt(person, secret, issuer);
			fail("gov.va.ascent.security.jwt.JwtAuthenticationException should be thrown");
		} catch (gov.va.ascent.security.jwt.JwtAuthenticationException e) {
			assertTrue(e.getMessage().contains("Pid not provided or Invalid Pid"));
		}
	}

	/**
	 * Test of generateJwt method, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwt_Person() {
		Person person = new Person();
		person.setCorrelationIds(Arrays.asList(new String[] { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"600071516^PI^200CORP^USVBA^A", "1040626995^NI^200DOD^USDOD^A", "796046489^SS" }));
		String result = GenerateToken.generateJwt(person);
		assertNotNull(result);
	}

	/**
	 * Test of generateJwt method, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwt_int() {
		int expireInsec = 0;
		String result = GenerateToken.generateJwt(expireInsec);
		assertNotNull(result);
	}

	/**
	 * Test of generateJwt method, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwt_3args() {
		Person person = new Person();
		person.setCorrelationIds(Arrays.asList(new String[] { "1012832469V956223^NI^200M^USVHA^P", "796046489^PI^200BRLS^USVBA^A",
				"600071516^PI^200CORP^USVBA^A", "1040626995^NI^200DOD^USDOD^A", "796046489^SS" }));
		int expireInsec = 10;
		String secret = "test";
		String issuer = "Vets.gov";
		String result = GenerateToken.generateJwt(person, expireInsec, secret, issuer);
		assertNotNull(result);
	}

	/**
	 * Test of person method, of class GenerateToken.
	 */
	@Test
	public void testPerson() {
		Person result = GenerateToken.person();
		assertEquals("JANE", result.getFirstName());
	}

}
