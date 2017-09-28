/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.util;

import gov.va.ascent.framework.security.PersonTraits;
import org.junit.Test;
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
        System.out.println("generateJwt");
        String expResult = "";
        String result = GenerateToken.generateJwt();
        assertNotNull(expResult, result);
    }

    /**
     * Test of generateJwt method, of class GenerateToken.
     */
    @Test
    public void testGenerateJwt_PersonTraits_String() {
        System.out.println("generateJwt");
        PersonTraits person = new PersonTraits();
        String secret = "test";
        String result = GenerateToken.generateJwt(person, secret);
        assertNotNull(result);
    }

    /**
     * Test of generateJwt method, of class GenerateToken.
     */
    @Test
    public void testGenerateJwt_PersonTraits() {
        System.out.println("generateJwt");
        PersonTraits person = new PersonTraits();
        String result = GenerateToken.generateJwt(person);
        assertNotNull(result);
    }

    /**
     * Test of generateJwt method, of class GenerateToken.
     */
    @Test
    public void testGenerateJwt_int() {
        System.out.println("generateJwt");
        int expireInsec = 0;
        String result = GenerateToken.generateJwt(expireInsec);
        assertNotNull(result);
    }

    /**
     * Test of generateJwt method, of class GenerateToken.
     */
    @Test
    public void testGenerateJwt_3args() {
        System.out.println("generateJwt");
        PersonTraits person = new PersonTraits();
        int expireInsec = 10;
        String secret = "test";
        String result = GenerateToken.generateJwt(person, expireInsec, secret);
        assertNotNull(result);
    }

    /**
     * Test of person method, of class GenerateToken.
     */
    @Test
    public void testPerson() {
        System.out.println("person");
        PersonTraits result = GenerateToken.person();
        assertEquals("JANE", result.getFirstName());
    }
    
}
