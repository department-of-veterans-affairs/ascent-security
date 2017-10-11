package gov.va.ascent.security.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by vgadda on 7/19/17.
 */

@Component
public class JwtTokenService {

    @Autowired
    private JwtAuthenticationProperties jwtAuthenticationProperties;

    public Map<String, String> getTokenFromRequest(){
        Map<String, String> token = new HashMap<>();
        final HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String tokenValue = request.getHeader(jwtAuthenticationProperties.getHeader());
        if(tokenValue != null) {
            token.put(jwtAuthenticationProperties.getHeader(), tokenValue);
        }

        return token;
    }
}
