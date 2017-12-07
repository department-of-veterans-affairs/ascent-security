package gov.va.ascent.security;

import gov.va.ascent.framework.security.PersonTraits;
import gov.va.ascent.security.jwt.JwtAuthenticationProperties;
import gov.va.ascent.security.util.GenerateToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenResource {

	@Autowired
	private JwtAuthenticationProperties jwtAuthenticationProperties;

	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = {"application/json"})
	public String getToken(@RequestBody PersonTraits person){
		return GenerateToken.generateJwt(person, jwtAuthenticationProperties.getSecret());
	}
	
	/**
	 * Registers fields that should be allowed for data binding.
	 * 
	 * @param binder
	 *            Spring-provided data binding context object.
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setAllowedFields(new String[] { "birthDate", "firstName", "lastName", "middleName", "prefix", "suffix",
				"gender", "assuranceLevel", "email", "dodedipnid", "pnidType", "pnid", "pid", "icn", "fileNumber",
				"tokenId", "correlationIds" });
	}
}
