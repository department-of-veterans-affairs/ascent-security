package gov.va.ascent.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

/**
 * Created by vgadda on 5/4/17.
 * similar to {@code UsernamePasswordAuthenticationFilter}
 */
public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtAuthenticationProperties jwtAuthenticationProperties;

    private static final Logger LOG = LoggerFactory.getLogger(JwtParser.class);

    public JwtAuthenticationFilter(JwtAuthenticationProperties jwtAuthenticationProperties, AuthenticationSuccessHandler jwtAuthenticationSuccessHandler,
                                   AuthenticationProvider jwtAuthenticationProvider) {
        super(new IgnoredRequestMatcher(jwtAuthenticationProperties.getFilterProcessUrl(), jwtAuthenticationProperties.getExcludeUrls()));
        this.jwtAuthenticationProperties = jwtAuthenticationProperties;
        setAuthenticationSuccessHandler(jwtAuthenticationSuccessHandler);
        setAuthenticationManager(new ProviderManager(new ArrayList<>(Arrays.asList(jwtAuthenticationProvider))));
    }



    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String token = request.getHeader(jwtAuthenticationProperties.getHeader());
        if(token == null || !token.startsWith("Bearer ")){
            LOG.error("No JWT Token in Header");
            throw new JwtAuthenticationException("No JWT Token in Header");
        }

        token = token.substring(7);

        return getAuthenticationManager().authenticate(new JwtAuthenticationToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);

        chain.doFilter(request, response);
    }
}

 class IgnoredRequestMatcher implements RequestMatcher {
    private RequestMatcher baselineMatches;
    private RequestMatcher ignoreMatches;

    public IgnoredRequestMatcher(String baselineMatches, String[] ignoreUrls) {
        this.baselineMatches = new AntPathRequestMatcher(baselineMatches);
        this.ignoreMatches = ignoreMatchers(ignoreUrls);
    }

    public IgnoredRequestMatcher(RequestMatcher baselineMatches, RequestMatcher ignoreMatches) {
        this.baselineMatches = baselineMatches;
        this.ignoreMatches = ignoreMatches;
    }

    private RequestMatcher ignoreMatchers(String[] exclusionUrls){
        LinkedList<RequestMatcher> matcherList = new LinkedList<>();
        for (String url : exclusionUrls) {
            matcherList.add(new AntPathRequestMatcher(url));
        }
        return new OrRequestMatcher(matcherList);
    }

    @Override
    public boolean matches(HttpServletRequest request) {
        if (ignoreMatches.matches(request)) {
            return false;
        } else {
            return baselineMatches.matches(request);
        }
    }
}