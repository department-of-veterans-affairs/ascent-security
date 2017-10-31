package gov.va.ascent.security.jwt;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.va.ascent.framework.security.PersonTraits;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

/**
 * Created by vgadda on 5/4/17.
 */

public class JwtParser {

    private JwtAuthenticationProperties jwtAuthenticationProperties;

    public JwtParser(JwtAuthenticationProperties properties){
        this.jwtAuthenticationProperties = properties;
    }

    public PersonTraits parseJwt(String token){

        Claims claims = null;
        claims = Jwts.parser().setSigningKey(jwtAuthenticationProperties.getSecret())
                .requireIssuer("Vets.gov")
                .parseClaimsJws(token).getBody();
        return getPersonFrom(claims);

    }

    private PersonTraits getPersonFrom(final Claims claims){
        PersonTraits personTraits = new PersonTraits();
        personTraits.setTokenId(claims.getId());
        personTraits.setFirstName(claims.get("firstName", String.class));
        personTraits.setLastName(claims.get("lastName", String.class));
        personTraits.setPrefix(claims.get("prefix", String.class));
        personTraits.setMiddleName(claims.get("middleName", String.class));
        personTraits.setSuffix(claims.get("suffix", String.class));
        personTraits.setBirthDate(claims.get("birthDate", Date.class));
        personTraits.setGender(claims.get("gender", String.class));
        personTraits.setAssuranceLevel(claims.get("assuranceLevel", String.class));
        personTraits.setEmail(claims.get("email", String.class));
        
        CorrelationIdsParser instance = new CorrelationIdsParser();
        List<String> list = (List<String>) claims.get("correlationIds");
        Map<String, String> result = instance.parseCorrelationIds(list);
        
        personTraits.setCorrelationIds(list);
        personTraits.setDodedipnid(result.get("dodedipnid"));
        personTraits.setPnidType(result.get("pnidType"));
        personTraits.setPnid(result.get("pnid"));
        personTraits.setPid(result.get("pid"));
        personTraits.setIcn(result.get("icn"));
        personTraits.setFileNumber(result.get("fileNumber"));
        
        return personTraits;
    }

}
