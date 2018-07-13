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
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

/**
 * Created by vgadda on 5/4/17.
 * similar to {@code UsernamePasswordAuthenticationFilter}
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	private final JwtAuthenticationProperties jwtAuthenticationProperties;

	private static final Logger LOG = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

	public JwtAuthenticationFilter(final JwtAuthenticationProperties jwtAuthenticationProperties,
			final AuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
			final AuthenticationProvider jwtAuthenticationProvider) {
		super(new IgnoredRequestMatcher(jwtAuthenticationProperties.getFilterProcessUrl(),
				jwtAuthenticationProperties.getExcludeUrls()));
		this.jwtAuthenticationProperties = jwtAuthenticationProperties;
		setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
		setAuthenticationManager(new ProviderManager(new ArrayList<>(Arrays.asList(jwtAuthenticationProvider))));
	}

	@Override
	@SuppressWarnings("squid:S1166")
	public Authentication attemptAuthentication(final HttpServletRequest request, final HttpServletResponse response)
			throws IOException, ServletException {

		System.out.println("---- attemptAuthentication ----");

		String token = request.getHeader(jwtAuthenticationProperties.getHeader());
		System.out.println("attemptAuthentication :: retrieved token from request: " + token);

		if (token == null || !token.startsWith("Bearer ")) {
			LOG.error("No JWT Token in Header");
			throw new JwtAuthenticationException("No JWT Token in Header");
		}

		token = token.substring(7);
		System.out.println("attemptAuthentication :: truncated token: " + token);

		try {
			final JwtAuthenticationToken jwtToken = new JwtAuthenticationToken(token);
			System.out.println("attemptAuthentication :: jwtToken: " + ReflectionToStringBuilder.toString(jwtToken));
			System.out.println("attemptAuthentication :: authenticationManager: "
					+ ReflectionToStringBuilder.toString(getAuthenticationManager()));
			return getAuthenticationManager().authenticate(jwtToken);
		} catch (final SignatureException signatureException) {
			System.out
					.println("attemptAuthentication :: SignatureException: " + ReflectionToStringBuilder.toString(signatureException));
			writeAuditForJwtTokenErrors(
					new StringBuffer("Tampered Token[").append(token).append("]\nSignatureException[")
							.append(signatureException.getMessage()).append("]\n").toString(),
					request);
			throw new JwtAuthenticationException("Tampered Token");
		} catch (final MalformedJwtException ex) {
			System.out.println("attemptAuthentication :: MalformedJwtException: " + ReflectionToStringBuilder.toString(ex));
			writeAuditForJwtTokenErrors(
					new StringBuffer("Malformed Token[").append(token).append(" ]\nMalformedJwtException[").append(ex.getMessage())
							.append("]\n").toString(),
					request);
			throw new JwtAuthenticationException("Malformed Token");
		}
	}

	/**
	 *
	 * @param cause - cause
	 * @param request - original request
	 */
	private void writeAuditForJwtTokenErrors(final String cause, final HttpServletRequest request) {
		String message = "";
		try {
			message = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
		} catch (final IOException e) {
			LOG.error("Error while reading the request {}", e);
		}

		final String data = cause.concat(" Request: ").concat(message);
		final AuditEventData auditData =
				new AuditEventData(AuditEvents.SECURITY, "attemptAuthentication", JwtAuthenticationFilter.class.getName());
		AuditLogger.error(auditData, data);
	}

	@Override
	protected void successfulAuthentication(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain chain, final Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);

		chain.doFilter(request, response);
	}
}

class IgnoredRequestMatcher implements RequestMatcher {
	private final RequestMatcher baselineMatches;
	private final RequestMatcher ignoreMatches;

	public IgnoredRequestMatcher(final String baselineMatches, final String[] ignoreUrls) {
		this.baselineMatches = new AntPathRequestMatcher(baselineMatches);
		this.ignoreMatches = ignoreMatchers(ignoreUrls);
	}

	public IgnoredRequestMatcher(final RequestMatcher baselineMatches, final RequestMatcher ignoreMatches) {
		this.baselineMatches = baselineMatches;
		this.ignoreMatches = ignoreMatches;
	}

	private RequestMatcher ignoreMatchers(final String[] exclusionUrls) {
		final LinkedList<RequestMatcher> matcherList = new LinkedList<>();
		for (final String url : exclusionUrls) {
			matcherList.add(new AntPathRequestMatcher(url));
		}
		return new OrRequestMatcher(matcherList);
	}

	@Override
	public boolean matches(final HttpServletRequest request) {
		if (ignoreMatches.matches(request)) {
			return false;
		} else {
			return baselineMatches.matches(request);
		}
	}
}