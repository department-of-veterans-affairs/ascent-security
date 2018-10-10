package gov.va.ascent.security.jwt;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.List;
import javax.crypto.spec.SecretKeySpec;

import gov.va.ascent.framework.security.PersonTraits;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by vgadda on 5/4/17.
 */

public class JwtParser {

	private JwtAuthenticationProperties jwtAuthenticationProperties;

	public JwtParser(final JwtAuthenticationProperties properties) {
		this.jwtAuthenticationProperties = properties;
	}

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

	@SuppressWarnings("unchecked")
	private PersonTraits getPersonFrom(final Claims claims) {
		PersonTraits personTraits = new PersonTraits();
		personTraits.setTokenId(claims.getId());
		personTraits.setFirstName(claims.get("firstName", String.class));
		personTraits.setLastName(claims.get("lastName", String.class));
		personTraits.setPrefix(claims.get("prefix", String.class));
		personTraits.setMiddleName(claims.get("middleName", String.class));
		personTraits.setSuffix(claims.get("suffix", String.class));
		personTraits.setBirthDate(claims.get("birthDate", String.class));
		personTraits.setGender(claims.get("gender", String.class));
		personTraits.setAssuranceLevel(claims.get("assuranceLevel", Integer.class));
		personTraits.setEmail(claims.get("email", String.class));
		personTraits.setDodedipnid(claims.get("dodedipnid", String.class));
		personTraits.setPnidType(claims.get("pnidType", String.class));
		personTraits.setPnid(claims.get("pnid", String.class));
		personTraits.setPid(claims.get("pid", String.class));
		personTraits.setIcn(claims.get("icn", String.class));
		personTraits.setFileNumber(claims.get("fileNumber", String.class));
		personTraits.setCorrelationIds((List<String>) claims.get("correlationIds"));

		return personTraits;
	}

}
