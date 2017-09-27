/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.util;

import gov.va.ascent.framework.security.PersonTraits;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author rthota
 */
public class GenerateTokenTest {
    
    public GenerateTokenTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class GenerateToken.
     */
    @Test
    public void testMain() {
        System.out.println("main");
        String[] args = null;
        GenerateToken.main(args);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
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
