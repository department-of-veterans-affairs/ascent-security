/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.junit.Test;
import org.springframework.security.core.Authentication;

/**
 *
 * @author rthota
 */
public class JwtAuthenticationSuccessHandlerTest {
    
    public JwtAuthenticationSuccessHandlerTest() {
    }
    

    /**
     * Test of onAuthenticationSuccess method, of class JwtAuthenticationSuccessHandler.
     */
    @Test
    public void testOnAuthenticationSuccess() throws Exception {
        HttpServletRequest request = null;
        HttpServletResponse response = null;
        Authentication authentication = null;
        JwtAuthenticationSuccessHandler instance = new JwtAuthenticationSuccessHandler();
        instance.onAuthenticationSuccess(request, response, authentication);
    }
    
}
