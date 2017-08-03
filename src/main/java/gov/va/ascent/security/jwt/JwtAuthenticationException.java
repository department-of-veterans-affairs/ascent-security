package gov.va.ascent.security.jwt;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by vgadda on 5/4/17.
 */
public class JwtAuthenticationException extends AuthenticationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JwtAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}
