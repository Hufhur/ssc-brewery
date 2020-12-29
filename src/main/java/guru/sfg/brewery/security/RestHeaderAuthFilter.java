package guru.sfg.brewery.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RestHeaderAuthFilter extends AbstractAuthenticationProcessingFilter {

    public RestHeaderAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        String userName = getUserName(httpServletRequest);
        String userPassword = getPassword(httpServletRequest);

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, userPassword);
        return this.getAuthenticationManager().authenticate(token);
    }

    private String getPassword(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getHeader("api-value");
    }

    private String getUserName(HttpServletRequest request) {
        return request.getHeader("api-key");
    }
}
