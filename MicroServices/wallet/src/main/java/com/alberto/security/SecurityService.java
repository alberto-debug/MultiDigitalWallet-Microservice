package com.alberto.security;

import com.vaadin.flow.spring.security.AuthenticationContext;
import org.springframework.stereotype.Component;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Optional;

@Component
public class SecurityService {

    private final AuthenticationContext authenticationContext;
    private final AuthTokenCookieService authTokenCookieService;

    public SecurityService(AuthenticationContext authenticationContext, AuthTokenCookieService authTokenCookieService) {
        this.authenticationContext = authenticationContext;
        this.authTokenCookieService = authTokenCookieService;
    }

    public Optional<UserDetails> getAuthenticatedUser() {
        return authenticationContext.getAuthenticatedUser(UserDetails.class);
    }

    public void logout() {
        authTokenCookieService.clearToken();
        authenticationContext.logout();
    }
}
