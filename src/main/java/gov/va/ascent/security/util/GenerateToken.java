package gov.va.ascent.security.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

import javax.crypto.spec.SecretKeySpec;

import gov.va.ascent.framework.security.PersonTraits;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Created by vgadda on 5/5/17.
 */
public class GenerateToken {
    
	private static String secret = "secret";

    public static void main(String[] args) {
        secret = "vetsgov";
    }

    public static String generateJwt(){
        return generateJwt(person(),900, secret);
    }

    public static String generateJwt(PersonTraits person, String secret){
        return generateJwt(person, 900, secret);
    }

    public static String generateJwt(PersonTraits person){
        return generateJwt(person, 900, secret);
    }

    public static String generateJwt(int expireInsec){
        return generateJwt(person(), expireInsec, secret);
    }


    public static String generateJwt(PersonTraits person, int expireInsec, String secret){
        Calendar currentTime = GregorianCalendar.getInstance();
        Calendar expiration = GregorianCalendar.getInstance();
        expiration.setTime(currentTime.getTime());
        expiration.add(Calendar.SECOND, expireInsec);
        
        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        
        //We will sign our JWT with our ApiKey secret
        Key signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getJcaName());
	
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("Vets.gov")
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
        		.signWith(signatureAlgorithm, signingKey).compact();
    }

    
    public static PersonTraits person(){
        PersonTraits personTraits = new PersonTraits();
        personTraits.setFirstName("JANE");
        personTraits.setLastName("DOE");
        personTraits.setPrefix("Ms");
        personTraits.setMiddleName("M");
        personTraits.setSuffix("S");
        personTraits.setBirthDate(new Date(939465704000L));
        personTraits.setGender("FEMALE");
        personTraits.setAssuranceLevel("2");
        personTraits.setEmail("jane.doe@va.gov");
        personTraits.setDodedipnid("1105051936");
        personTraits.setPnidType("SS");
        personTraits.setPnid("912444689");
        personTraits.setPid("6666345");
        personTraits.setIcn("77779102");
        personTraits.setFileNumber("912444689");
        
        List<String> strArray = Arrays.asList("77779102^NI^200M^USVHA^P","912444689^PI^200BRLS^USVBA^A","6666345^PI^200CORP^USVBA^A","1105051936^NI^200DOD^USDOD^A","912444689^SS");
        personTraits.setCorrelationIds(strArray);
        return personTraits;
    }
}
