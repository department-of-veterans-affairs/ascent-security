package gov.va.ascent.security;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.handler.JwtAuthenticationSuccessHandler;
import gov.va.ascent.security.jwt.*;
import gov.va.ascent.security.util.GenerateToken;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by vgadda on 8/1/17.
 */
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = JwtTestConfig.class)
public class JwtAuthenticationFIlterTests {

    @Autowired
    JwtAuthenticationProperties properties;

    @Autowired
    AuthenticationProvider provider;

    @Test
    public void testNormalOperation() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("POST", "/user");
        request.addHeader("Authorization", "Bearer "+ GenerateToken.generateJwt());

        JwtAuthenticationFilter filter = new JwtAuthenticationFilter(properties,
                new JwtAuthenticationSuccessHandler(),provider);


        Authentication result = filter.attemptAuthentication(request,
                new MockHttpServletResponse());
        Assert.assertTrue(result != null);
        Assert.assertEquals((((PersonTraits) result.getPrincipal())), GenerateToken.person());
    }

}

@Configuration
class JwtTestConfig {
    @Bean
    JwtAuthenticationProperties jwtAuthenticationProperties(){
        return new JwtAuthenticationProperties();
    }

    @Bean
    protected AuthenticationProvider jwtAuthenticationProvider(){
        return new JwtAuthenticationProvider(new JwtParser(jwtAuthenticationProperties()));
    }
}