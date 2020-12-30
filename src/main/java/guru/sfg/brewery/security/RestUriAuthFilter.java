package guru.sfg.brewery.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class RestUriAuthFilter extends RestAuthFilter {

    public RestUriAuthFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super(requiresAuthenticationRequestMatcher);
    }

    @Override
    protected String getPassword(HttpServletRequest request) {
        String pw = request.getParameter("Api-Secret");
        log.debug("getting password = " + pw);
        return pw;
    }

    @Override
    protected String getUsername(HttpServletRequest request) {
        String usr = request.getParameter("Api-Key");
        log.debug("getting user = " + usr);
        return usr;
    }
}
