package gov.va.ascent.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import gov.va.ascent.security.handler.JwtAuthenticationEntryPoint;
import gov.va.ascent.security.jwt.JwtAuthenticationFilter;
import gov.va.ascent.security.jwt.JwtAuthenticationProperties;

/**
 * Created by vgadda on 5/4/17.
 */

@Configuration
@ComponentScan(basePackages = {"gov.va.ascent.security"})
public class AscentSecurityConfiguration {

    @Configuration
    @ConditionalOnProperty(prefix = "ascent.security.jwt", name = "enabled", matchIfMissing = true)
    @Order(JwtAuthenticationProperties.AUTH_ORDER)
    protected static class JwtWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {
        @Autowired
        private AuthenticationSuccessHandler jwtAuthenticationSuccessHandler;
        @Autowired
        private AuthenticationProvider jwtAuthenticationProvider;
        @Autowired
        private JwtAuthenticationProperties jwtAuthenticationProperties;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/token**").permitAll()
                    .antMatchers(jwtAuthenticationProperties.getFilterProcessUrl()).authenticated()
                    .and()
                    .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint())
                    .and()
                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and().csrf().disable();
            http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
            http.headers().cacheControl();
        }

        @Bean
        protected AuthenticationEntryPoint authenticationEntryPoint(){
            return new JwtAuthenticationEntryPoint();
        }

        @Bean
        protected JwtAuthenticationFilter jwtAuthenticationFilter(){
            return  new JwtAuthenticationFilter(jwtAuthenticationProperties, jwtAuthenticationSuccessHandler, jwtAuthenticationProvider);
        }
    }

    @Configuration
    @ConditionalOnProperty(prefix = "ascent.security.jwt", name = "enabled", havingValue = "false")
    @Order(JwtAuthenticationProperties.AUTH_ORDER)
    protected static class JwtNoWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationProperties jwtAuthenticationProperties;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(jwtAuthenticationProperties.getFilterProcessUrl());
        }

    }

    @Configuration
    @Order(JwtAuthenticationProperties.NO_AUTH_ORDER)
    protected static class NoWebSecurityConfigurerAdapter
            extends WebSecurityConfigurerAdapter {

        @Autowired
        private JwtAuthenticationProperties jwtAuthenticationProperties;

        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().antMatchers(jwtAuthenticationProperties.getExcludeUrls());
        }

    }

}


