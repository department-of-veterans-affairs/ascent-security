package gov.va.ascent.security.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import gov.va.ascent.framework.security.PersonTraits;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by vgadda on 5/5/17.
 */
public class GenerateToken {

	private static String secret = "secret";
	private static String issuer = "Vets.gov";

	public static void main(final String[] args) {
		secret = "vetsgov";
	}

	public static String generateJwt() {
		return generateJwt(person(), 900, secret, issuer);
	}

	public static String generateJwt(final PersonTraits person, final String secret, final String issuer) {
		return generateJwt(person, 900, secret, issuer);
	}

	public static String generateJwt(final PersonTraits person) {
		return generateJwt(person, 900, secret, issuer);
	}

	public static String generateJwt(final int expireInsec) {
		return generateJwt(person(), expireInsec, secret, issuer);
	}

	public static String generateJwt(final PersonTraits person, final int expireInsec, final String secret, final String issuer) {
		System.out.println("GenerateToken->expireInsec :: person: " + ReflectionToStringBuilder.toString(person));
		System.out.println("GenerateToken->expireInsec :: expireInsec: " + expireInsec);
		System.out.println("GenerateToken->expireInsec :: secret: " + secret);
		System.out.println("GenerateToken->expireInsec :: issuer: " + issuer);

		final Calendar currentTime = GregorianCalendar.getInstance();
		currentTime.clear();
		currentTime.setTimeZone(TimeZone.getTimeZone("EDT")); // debug
		currentTime.set(2018, GregorianCalendar.JULY, 16, 17, 52, 32); // debug
		final Calendar expiration = (GregorianCalendar) currentTime.clone();
		expiration.setTime(currentTime.getTime());
		expiration.add(Calendar.SECOND, expireInsec);
		System.out.println("GenerateToken->expireInsec :: expiration: "
				+ SimpleDateFormat.getDateTimeInstance(SimpleDateFormat.FULL, SimpleDateFormat.FULL).format(expiration.getTime()));

		// The JWT signature algorithm we will be using to sign the token
		final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
		System.out.println("GenerateToken->expireInsec :: signatureAlgorithm: " + signatureAlgorithm.getJcaName());

		System.out.println("GenerateToken->expireInsec :: Getting signingKey with UTF-8 secret and " + signatureAlgorithm.getJcaName()
				+ " algorithm ...");
		// We will sign our JWT with our ApiKey secret
		final Key signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getJcaName());
		System.out.println("GenerateToken->expireInsec :: signingKey: " + ReflectionToStringBuilder.reflectionToString(signingKey));

		final String generatedTokenString = Jwts.builder()
				.setHeaderParam("typ", "JWT")
				.setIssuer(issuer)
				.setIssuedAt(currentTime.getTime())
				.setId(UUID.randomUUID().toString())
				.setExpiration(expiration.getTime())

				.claim("firstName", person.getFirstName())
				.claim("middleName", person.getMiddleName())
				.claim("lastName", person.getLastName())
				.claim("prefix", person.getPrefix())
				.claim("suffix", person.getSuffix())
				.claim("birthDate", person.getBirthDate())
				.claim("gender", person.getGender())
				.claim("assuranceLevel", person.getAssuranceLevel())
				.claim("email", person.getEmail())
				.claim("dodedipnid", person.getDodedipnid())
				.claim("pnidType", person.getPnidType())
				.claim("pnid", person.getPnid())
				.claim("pid", person.getPid())
				.claim("icn", person.getIcn())
				.claim("fileNumber", person.getFileNumber())
				.claim("correlationIds", person.getCorrelationIds())
				.signWith(signatureAlgorithm, signingKey)
				.compact();

		System.out.println("GenerateToken->expireInsec :: generatedTokenString: " + generatedTokenString);
		return generatedTokenString;
	}

	public static PersonTraits person() {
		final PersonTraits personTraits = new PersonTraits();
		personTraits.setFirstName("JANE");
		personTraits.setLastName("DOE");
		personTraits.setPrefix("Ms");
		personTraits.setMiddleName("M");
		personTraits.setSuffix("S");
		personTraits.setBirthDate("1955-01-01");
		personTraits.setGender("FEMALE");
		personTraits.setAssuranceLevel(2);
		personTraits.setEmail("jane.doe@va.gov");
		personTraits.setDodedipnid("1105051936");
		personTraits.setPnidType("SS");
		personTraits.setPnid("912444689");
		personTraits.setPid("6666345");
		personTraits.setIcn("77779102");
		personTraits.setFileNumber("912444689");

		final List<String> strArray = Arrays.asList("77779102^NI^200M^USVHA^P", "912444689^PI^200BRLS^USVBA^A",
				"6666345^PI^200CORP^USVBA^A", "1105051936^NI^200DOD^USDOD^A", "912444689^SS");
		personTraits.setCorrelationIds(strArray);
		return personTraits;
	}
}
