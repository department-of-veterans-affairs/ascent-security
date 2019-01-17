package gov.va.ascent.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import gov.va.ascent.framework.swagger.SwaggerResponseMessages;
import gov.va.ascent.security.jwt.JwtAuthenticationProperties;
import gov.va.ascent.security.model.Person;
import gov.va.ascent.security.util.GenerateToken;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class TokenResource implements SwaggerResponseMessages {

	private static final String API_OPERATION_VALUE = "Get JWT Token";
	private static final String API_OPERATION_NOTES = "Get a JWT bearer token with 'person' data. "
			+ "Include MVI correlationIds if required by the target API.";
	private static final String API_PARAM_GETTOKEN_PERSON = "Identity information for the authenticated user. "
			+ "CorrelationIds may be null or an empty array if the target API does not require it. "
			+ "Otherwise, correlationIds must be the list as retrieved from MVI.";

	@Autowired
	private JwtAuthenticationProperties jwtAuthenticationProperties;

	@Value("${ascent.security.jwt.validation.required-parameters:}")
	private String[] jwtTokenRequiredParameterList;

	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = { "application/json" })
	@ApiOperation(value = API_OPERATION_VALUE, notes = API_OPERATION_NOTES)
	@ApiResponses(value = { @ApiResponse(code = 200, message = MESSAGE_200), @ApiResponse(code = 400, message = MESSAGE_400),
			@ApiResponse(code = 500, message = MESSAGE_500) })
	public String getToken(
			@ApiParam(value = API_PARAM_GETTOKEN_PERSON, required = true) @RequestBody final Person person) {
		// @ApiModel(description="Identity information for the authenticated user.")
		return GenerateToken.generateJwt(person, jwtAuthenticationProperties.getExpireInSeconds(),
				jwtAuthenticationProperties.getSecret(), jwtAuthenticationProperties.getIssuer(), jwtTokenRequiredParameterList);
	}

	/**
	 * Registers fields that should be allowed for data binding.
	 *
	 * @param binder
	 *            Spring-provided data binding context object.
	 */
	@InitBinder
	public void initBinder(final WebDataBinder binder) {
		binder.setAllowedFields("birthDate", "firstName", "lastName", "middleName", "prefix", "suffix",
				"gender", "assuranceLevel", "email", "dodedipnid", "pnidType", "pnid", "pid", "icn", "fileNumber",
				"tokenId", "correlationIds");
	}
}
