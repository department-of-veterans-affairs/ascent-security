/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import gov.va.ascent.security.config.AscentSecurityTestConfig;

/**
 *
 * @author rthota
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AscentSecurityTestConfig.class)
public class JwtTokenServiceTest {

	@Autowired
	JwtTokenService jwtTokenService;
	
	public JwtTokenServiceTest() {
	}

	/**
	 * Test of getTokenFromRequest method, of class JwtTokenService.
	 */
	@Test
	public void testGetTokenFromRequest() {
		System.out.println("getTokenFromRequest");
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		request.setSession(session);
		MockitoAnnotations.initMocks(this);
		request.addHeader("Authorization", "test");
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Map<String, String> result = jwtTokenService.getTokenFromRequest();
		System.out.println("size is:" + result.size());
		assertEquals(1,result.size());
		assertTrue(result.containsKey("Authorization"));
	}
	
	
	/**
	 * Test of getTokenFromRequest method, of class JwtTokenService.
	 */
	@Test
	public void testGetTokenFromRequestZeroResult() {
		System.out.println("getTokenFromRequest");
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpSession session = new MockHttpSession();
		request.setSession(session);
		MockitoAnnotations.initMocks(this);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
		
		Map<String, String> result = jwtTokenService.getTokenFromRequest();
		System.out.println("size is:" + result.size());
		assertEquals(0,result.size());
	}
}
