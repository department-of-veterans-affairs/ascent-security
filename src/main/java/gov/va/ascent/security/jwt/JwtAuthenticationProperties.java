package gov.va.ascent.security.jwt;

import org.springframework.boot.actuate.autoconfigure.ManagementServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by vgadda on 5/8/17.
 */
@Component
@ConfigurationProperties(prefix = "ascent.security.jwt")
public class JwtAuthenticationProperties {
    private boolean enabled = true;
    private String header = "Authorization";
    private String secret = "secret";
    private String filterProcessUrl = "/**";
    private String[] excludeUrls = {"/v2/api-docs/**", "/configuration/ui/**", "/swagger-resources/**",
            "/configuration/security/**", "/swagger-ui.html", "/webjars/**", "/**/token"};

    public static final int AUTH_ORDER = ManagementServerProperties.BASIC_AUTH_ORDER + 3;
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

    public void setSecret(String secret) {
        this.secret = secret;
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
