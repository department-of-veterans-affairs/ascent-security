package gov.va.ascent.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by vgadda on 5/4/17.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String token;

    public JwtAuthenticationToken(String token) {
        super("N/A", "N/A");
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
