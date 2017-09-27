/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

/**
 *
 * @author rthota
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = testconfig.class)
public class JwtTokenServiceTest extends AbstractJUnit4SpringContextTests {

	public JwtTokenServiceTest() {
	}

	@Mock
	private AnnotationConfigWebApplicationContext context;

	@Before
	public void before() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		request.setSession(session);
		MockitoAnnotations.initMocks(this);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
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
	 * Test of getTokenFromRequest method, of class JwtTokenService.
	 */
	@Test
	public void testGetTokenFromRequest() {
		System.out.println("getTokenFromRequest");

		JwtTokenService instance = new JwtTokenService();
		Map<String, String> result = instance.getTokenFromRequest();
		assertNotNull(result);
	}
}

@Configuration
@ComponentScan(basePackages = "gov.va.ascent.security.jwt.JwtTokenService")
class testconfig {
	@Bean
	JwtAuthenticationProperties jwtAuthenticationProperties() {
		return new JwtAuthenticationProperties();
	}

	@Bean
	protected AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider(new JwtParser(jwtAuthenticationProperties()));
	}
}
