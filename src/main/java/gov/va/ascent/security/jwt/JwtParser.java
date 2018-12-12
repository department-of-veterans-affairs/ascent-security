package gov.va.ascent.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;

import javax.crypto.spec.SecretKeySpec;

import gov.va.ascent.framework.log.AscentLogger;
import gov.va.ascent.framework.log.AscentLoggerFactory;
import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.jwt.correlation.CorrelationIdsParser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Parse the encrypted JWT
 */
public class JwtParser {
	private static final AscentLogger LOGGER = AscentLoggerFactory.getLogger(JwtParser.class);

	/** The spring configurable properties used for authentication */
	private JwtAuthenticationProperties jwtAuthenticationProperties;

	/**
	 * Parse the JWT json into its component values
	 *
	 * @param properties
	 */
	public JwtParser(final JwtAuthenticationProperties properties) {
		this.jwtAuthenticationProperties = properties;
	}

	/**
	 * Decrypts the JWT and attempts to construct a PersonTraits object from it.
	 * If correlation id parsing fails, {@code null} is returned.
	 *
	 * @param token the encrypted JWT
	 * @return PersonTraits, or {@code null} if some issue with the correlation ids
	 */
	public PersonTraits parseJwt(final String token) {
		Claims claims = null;

		// The JWT signature algorithm we will be using to sign the token
		SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

		// We will sign our JWT with our ApiKey secret
		Key signingKey = new SecretKeySpec(jwtAuthenticationProperties.getSecret().getBytes(StandardCharsets.UTF_8),
				signatureAlgorithm.getJcaName());

		claims = Jwts.parser().setSigningKey(signingKey).requireIssuer(jwtAuthenticationProperties.getIssuer()).parseClaimsJws(token)
				.getBody();

		return getPersonFrom(claims);

	}

	/**
	 * Attempts to produce a PersonTraits object from the correlation ids.
	 * If correlation id parsing fails, {@code null} is returned.
	 *
	 * @param claims - the JWT contents
	 * @return PersonTraits, or {@code null} if some issue with the correlation ids
	 */
	@SuppressWarnings("unchecked")
	private PersonTraits getPersonFrom(final Claims claims) {
		PersonTraits personTraits = new PersonTraits();

		personTraits.setFirstName(claims.get("firstName", String.class));
		personTraits.setLastName(claims.get("lastName", String.class));
		personTraits.setPrefix(claims.get("prefix", String.class));
		personTraits.setMiddleName(claims.get("middleName", String.class));
		personTraits.setSuffix(claims.get("suffix", String.class));
		personTraits.setBirthDate(claims.get("birthDate", String.class));
		personTraits.setGender(claims.get("gender", String.class));
		personTraits.setAssuranceLevel(claims.get("assuranceLevel", Integer.class));
		personTraits.setEmail(claims.get("email", String.class));

		try {
			CorrelationIdsParser instance = new CorrelationIdsParser();
			@SuppressWarnings("unchecked")
			List<String> list = (List<String>) claims.get("correlationIds");
			instance.parseCorrelationIds(list, personTraits);

		} catch (Exception e) { // NOSONAR intentionally wide, errors are already logged
			// if there is any detected issue with the correlation ids
			personTraits = null;
		}

		return personTraits;
	}

}
