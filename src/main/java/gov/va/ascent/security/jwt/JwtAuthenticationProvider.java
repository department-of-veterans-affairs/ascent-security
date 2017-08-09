package gov.va.ascent.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Created by vgadda on 5/4/17.
 */

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{

    private JwtParser parser;

    public JwtAuthenticationProvider(JwtParser parser) {
        this.parser = parser;
    }

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        //no additional checks for authentication
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
        String token = authenticationToken.getToken();//pass this for verification

        PersonTraits person =  parser.parseJwt(token);
        if(person == null){
            throw new JwtAuthenticationException("Invalid Token");
        }
        return person;
    }
}
