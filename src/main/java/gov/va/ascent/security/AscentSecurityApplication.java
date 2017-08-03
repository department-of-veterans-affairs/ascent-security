package gov.va.ascent.security;

import java.security.Principal;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import gov.va.ascent.security.jwt.JwtAuthenticationProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import gov.va.ascent.security.jwt.PersonTraits;
import gov.va.ascent.security.util.GenerateToken;

@SpringBootApplication
public class AscentSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(AscentSecurityApplication.class, args);
	}
}

@RestController
class UserResource{

	private final static Logger LOG = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private JwtAuthenticationProperties jwtAuthenticationProperties;

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public PersonTraits getPerson(Principal principal){
		logHeaders();
		PersonTraits auth = (PersonTraits)((UsernamePasswordAuthenticationToken) principal).getPrincipal();
		return auth;
	}

	private void logHeaders(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		while(headerNames.hasMoreElements()){
			String headerName = headerNames.nextElement();
			LOG.info(String.format("Header %s : %s ", headerName, request.getHeader(headerName)));
		}
	}

	@RequestMapping(value = "/token", method = RequestMethod.POST, consumes = {"application/json"})
	public String getToken(@RequestBody PersonTraits person){
		return GenerateToken.generateJwt(person, jwtAuthenticationProperties.getSecret());
	}
}
