package gov.va.ascent.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import gov.va.ascent.security.jwt.JwtAuthenticationProperties;
import gov.va.ascent.security.jwt.JwtAuthenticationProvider;
import gov.va.ascent.security.jwt.JwtParser;

@Configuration
@ComponentScan(basePackages = {"gov.va.ascent.security", "gov.va.ascent.security.jwt"})
public class AscentSecurityTestConfig {
	@Bean
	JwtAuthenticationProperties jwtAuthenticationProperties() {
		return new JwtAuthenticationProperties();
	}

	@Bean
	protected AuthenticationProvider jwtAuthenticationProvider() {
		return new JwtAuthenticationProvider(new JwtParser(jwtAuthenticationProperties()));
	}
}