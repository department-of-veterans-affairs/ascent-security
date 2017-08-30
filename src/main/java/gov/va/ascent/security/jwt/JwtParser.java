package gov.va.ascent.security.jwt;

import java.util.Date;

import gov.va.ascent.framework.security.PersonTraits;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

/**
 * Created by vgadda on 5/4/17.
 */

public class JwtParser {

    private static final Logger LOG = LoggerFactory.getLogger(JwtParser.class);

    private JwtAuthenticationProperties jwtAuthenticationProperties;

    public JwtParser(JwtAuthenticationProperties properties){
        this.jwtAuthenticationProperties = properties;
    }

    public PersonTraits parseJwt(String token){
        PersonTraits person = null;

        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(jwtAuthenticationProperties.getSecret())
                    .requireIssuer("Vets.gov")
                    .parseClaimsJws(token).getBody();

        }catch (JwtException ex){
            LOG.error("Unable to parse JWT token:", ex);
            return person;
        }
        person = getPersonFrom(claims);
        return person;
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
        personTraits.setDodedipnid(claims.get("dodedipnid", String.class));
        personTraits.setPnidType(claims.get("pnidType", String.class));
        personTraits.setPnid(claims.get("pnid", String.class));
        personTraits.setPid(claims.get("pid", String.class));
        personTraits.setIcn(claims.get("icn", String.class));
        personTraits.setFileNumber(claims.get("fileNumber", String.class));
        return personTraits;
    }


}
