package gov.va.ascent.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;

import gov.va.ascent.framework.security.PersonTraits;

/**
 * Created by vgadda on 5/4/17.
 */

public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

	JwtParser parser;

	public JwtAuthenticationProvider(final JwtParser parser) {
		this.parser = parser;
	}

	@Override
	protected void additionalAuthenticationChecks(final UserDetails userDetails,
			final UsernamePasswordAuthenticationToken authentication) {
		// no additional checks for authentication
	}

	@Override
	protected UserDetails retrieveUser(final String username, final UsernamePasswordAuthenticationToken authentication) {
		final JwtAuthenticationToken authenticationToken = (JwtAuthenticationToken) authentication;
		final String token = authenticationToken.getToken();// pass this for verification

		final PersonTraits person = parser.parseJwt(token);
		if (person == null) {
			throw new JwtAuthenticationException("Invalid Token");
		}
		return person;
	}
}
