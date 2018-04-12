/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.handler;

import static org.mockito.Mockito.mock;

import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.AuthenticationException;

/**
 *
 * @author rthota
 */
public class JwtAuthenticationEntryPointTest {
    
    public JwtAuthenticationEntryPointTest() {
    }

    /**
     * Test of commence method, of class JwtAuthenticationEntryPoint.
     */
    @Test
    public void testCommence() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
		MockHttpServletResponse response = new MockHttpServletResponse();
        JwtAuthenticationEntryPoint instance = new JwtAuthenticationEntryPoint();
        instance.commence(request, response, mock(AuthenticationException.class));
    }
    
}
