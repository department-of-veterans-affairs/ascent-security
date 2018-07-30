package gov.va.ascent.security.jwt;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * Created by vgadda on 5/4/17.
 */
public class JwtAuthenticationToken extends UsernamePasswordAuthenticationToken {
	private static final long serialVersionUID = 1L;

	private String token;

	public JwtAuthenticationToken(final String token) {
		super("N/A", "N/A");
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(final String token) {
		this.token = token;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (token == null ? 0 : token.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof JwtAuthenticationToken)) {
			return false;
		}
		final JwtAuthenticationToken other = (JwtAuthenticationToken) obj;
		if (token == null) {
			if (other.token != null) {
				return false;
			}
		} else if (!token.equals(other.token)) {
			return false;
		}
		return true;
	}

}
