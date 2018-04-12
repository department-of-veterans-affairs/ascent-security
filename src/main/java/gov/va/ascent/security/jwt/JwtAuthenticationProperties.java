package gov.va.ascent.security.jwt;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by vgadda on 5/8/17.
 */
@ConfigurationProperties(prefix = "ascent.security.jwt")
public class JwtAuthenticationProperties {
    private boolean enabled = true;
    private String header = "Authorization";
    private String secret = "secret";
    private String issuer = "Vets.gov";
    private String filterProcessUrl = "/**";
    private String[] excludeUrls = {"/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
            "/configuration/security/**", "/swagger-ui.html", "/webjars/**", "/**/token", "/**/swagger/error-keys.html"};

    public static final int AUTH_ORDER = SecurityProperties.BASIC_AUTH_ORDER - 2;
    public static final int NO_AUTH_ORDER = AUTH_ORDER + 1;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getSecret() {
        return secret;
    }
    
    public String getIssuer() {
        return issuer;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }
    
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getFilterProcessUrl() {
        return filterProcessUrl;
    }

    public void setFilterProcessUrl(String filterProcessUrl) {
        this.filterProcessUrl = filterProcessUrl;
    }

    public String[] getExcludeUrls() {
        return excludeUrls;
    }

    public void setExcludeUrls(String[] excludeUrls) {
        this.excludeUrls = excludeUrls;
    }
}
