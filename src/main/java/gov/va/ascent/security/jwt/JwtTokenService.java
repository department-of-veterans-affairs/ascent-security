package gov.va.ascent.security.jwt;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Put request token into a HashMap
 *
 * Created by vgadda on 7/19/17.
 */
@Component
public class JwtTokenService {

	@Autowired
	private JwtAuthenticationProperties jwtAuthenticationProperties;

	/**
	 * Gets the JWT token from the request context Authorization header,
	 * and returns it in a HashMap.
	 * 
	 * @return Map the key and value of the JWT token header
	 */
	public Map<String, String> getTokenFromRequest() {
		Map<String, String> token = new HashMap<>();
		final HttpServletRequest request =
				((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		String tokenValue = request.getHeader(jwtAuthenticationProperties.getHeader());
		if (tokenValue != null) {
			token.put(jwtAuthenticationProperties.getHeader(), tokenValue);
		}

		return token;
	}
}
