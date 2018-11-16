/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.util;

import org.junit.Test;

import gov.va.ascent.security.model.Person;

import static org.junit.Assert.*;

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
		String secret = "test";
		String issuer = "Vets.gov";
		String result = GenerateToken.generateJwt(person, secret, issuer);
		assertNotNull(result);
	}

	/**
	 * Test of generateJwt method, of class GenerateToken.
	 */
	@Test
	public void testGenerateJwt_Person() {
		Person person = new Person();
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
