package gov.va.ascent.security;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.jwt.JwtAuthenticationProperties;
import gov.va.ascent.security.util.GenerateToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenResource {

	private final static Logger LOG = LoggerFactory.getLogger(TokenResource.class);

	@Autowired
	private JwtAuthenticationProperties jwtAuthenticationProperties;

	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = {"application/json"})
	public String getToken(@RequestBody PersonTraits person){
		return GenerateToken.generateJwt(person, jwtAuthenticationProperties.getSecret());
	}
}
