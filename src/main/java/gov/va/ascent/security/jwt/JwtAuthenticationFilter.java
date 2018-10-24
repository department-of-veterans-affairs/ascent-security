package gov.va.ascent.security.jwt;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import gov.va.ascent.framework.audit.AuditEventData;
import gov.va.ascent.framework.audit.AuditEvents;
import gov.va.ascent.framework.audit.AuditLogger;
import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * Configure springboot starter for the Ascent platform.
 * Similar to {@code UsernamePasswordAuthenticationFilter}
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private JwtAuthenticationProperties jwtAuthenticationProperties;

	private static final AscentLogger LOG = AscentLoggerFactory.getLogger(JwtAuthenticationFilter.class);

	/**
	 * Create the filter.
	 * 
	 * @param jwtAuthenticationProperties
	 * @param jwtAuthenticationSuccessHandler
	 * @param jwtAuthenticationProvider
	 */
	public JwtAuthenticationFilter(JwtAuthenticationProperties jwtAuthenticationProperties,
			AuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
			AuthenticationProvider jwtAuthenticationProvider) {
		super(new IgnoredRequestMatcher(jwtAuthenticationProperties.getFilterProcessUrl(),
				jwtAuthenticationProperties.getExcludeUrls()));
		this.jwtAuthenticationProperties = jwtAuthenticationProperties;
		setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		setAuthenticationManager(new ProviderManager(new ArrayList<>(Arrays.asList(jwtAuthenticationProvider))));
	}

	@Override
	@SuppressWarnings("squid:S1166")
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		String token = request.getHeader(jwtAuthenticationProperties.getHeader());
		if (token == null || !token.startsWith("Bearer ")) {
			LOG.error("No JWT Token in Header");
			throw new JwtAuthenticationException("No JWT Token in Header");
		}

		token = token.substring(7);

		try {
			return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
		} catch (SignatureException signatureException) {
			writeAuditForJwtTokenErrors(
					new StringBuffer("Tampered Token[").append(token).append("]\nSignatureException[")
							.append(signatureException.getMessage()).append("]\n").toString(),
					request);
			throw new JwtAuthenticationException("Tampered Token");
		} catch (MalformedJwtException ex) {
			writeAuditForJwtTokenErrors(
					new StringBuffer("Malformed Token[").append(token).append(" ]\nMalformedJwtException[").append(ex.getMessage())
							.append("]\n").toString(),
					request);
			throw new JwtAuthenticationException("Malformed Token");
		}
	}

	/**
	 * Audit any errors.
	 * 
	 * @param cause - cause
	 * @param request - original request
	 */
	private void writeAuditForJwtTokenErrors(String cause, HttpServletRequest request) {
		String message = "";
		try {
			message = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			LOG.error("Error while reading the request {}", e);
		}

		String data = cause.concat(" Request: ").concat(message);
		AuditEventData auditData =
				new AuditEventData(AuditEvents.SECURITY, "attemptAuthentication", JwtAuthenticationFilter.class.getName());
		AuditLogger.error(auditData, data);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		chain.doFilter(request, response);
	}
}

/**
 * Set rules for what requests should be ignored.
 */
class IgnoredRequestMatcher implements RequestMatcher {
	private RequestMatcher baselineMatches;
	private RequestMatcher ignoreMatches;

	/**
	 * Requests to ignore based on ant path configurations.
	 * 
	 * @param baselineMatches
	 * @param ignoreUrls
	 */
	public IgnoredRequestMatcher(String baselineMatches, String[] ignoreUrls) {
		this.baselineMatches = new AntPathRequestMatcher(baselineMatches);
		this.ignoreMatches = ignoreMatchers(ignoreUrls);
	}

	/**
	 * Requests to ignore based on ant path configurations.
	 * 
	 * @param baselineMatches
	 * @param ignoreMatches
	 */
	public IgnoredRequestMatcher(RequestMatcher baselineMatches, RequestMatcher ignoreMatches) {
		this.baselineMatches = baselineMatches;
		this.ignoreMatches = ignoreMatches;
	}

	/**
	 * Add exclusion URLs to the list.
	 * 
	 * @param exclusionUrls
	 * @return RequestMatcher
	 */
	private RequestMatcher ignoreMatchers(String[] exclusionUrls) {
		LinkedList<RequestMatcher> matcherList = new LinkedList<>();
		for (String url : exclusionUrls) {
			matcherList.add(new AntPathRequestMatcher(url));
		}
		return new OrRequestMatcher(matcherList);
	}

	@Override
	public boolean matches(HttpServletRequest request) {
		if (ignoreMatches.matches(request)) {
			return false;
		} else {
			return baselineMatches.matches(request);
		}
	}
}