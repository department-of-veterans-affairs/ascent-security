/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gov.va.ascent.security.jwt;

import org.junit.Test;

/**
 *
 * @author rthota
 */
public class JwtAuthenticationExceptionTest {
    
    public JwtAuthenticationExceptionTest() {
    }
    
    @Test
    public void testSomeMethod() {
    		new JwtAuthenticationException("testmessage");
    }
    
    @Test
    public void testSomeMethod1() {
    		new JwtAuthenticationException("testmessage", new Throwable());
    }
    
}
