/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.jwt.JwtAuthenticationProperties;
import gov.va.ascent.security.jwt.JwtAuthenticationProvider;
import gov.va.ascent.security.jwt.JwtParser;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author rthota
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JwtTestConfig.class)
public class TokenResourceTest {
	
	private AnnotationConfigWebApplicationContext context;
	
    @Autowired
    JwtAuthenticationProperties properties;

    @Autowired
    AuthenticationProvider provider;
    
    public TokenResourceTest() {
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
     * Test of getToken method, of class TokenResource.
     */
    @Test
    public void testGetToken() {
    	
    	 context = new AnnotationConfigWebApplicationContext();
         context.register(JwtAuthenticationProperties.class);
         context.refresh();
         
        System.out.println("getToken");
        PersonTraits person = new PersonTraits();
        person.setFirstName("john");
        person.setLastName("doe");
        TokenResource instance = new TokenResource();
        String expResult = "";
        String result = instance.getToken(person);
       //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

	@Configuration
	class JwtTestConfig {
		@Bean
		JwtAuthenticationProperties jwtAuthenticationProperties() {
			return new JwtAuthenticationProperties();
		}

		@Bean
		protected AuthenticationProvider jwtAuthenticationProvider() {
			return new JwtAuthenticationProvider(new JwtParser(jwtAuthenticationProperties()));
		}
	}
} 
