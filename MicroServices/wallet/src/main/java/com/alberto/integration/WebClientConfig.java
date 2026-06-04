package com.alberto.integration;

import com.alberto.security.AuthTokenCookieService;
import com.alberto.security.JwtTokenService;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;

@Configuration
public class WebClientConfig {

    private static final String AUTH_TOKEN_KEY = "authToken";

    private final JwtTokenService jwtTokenService;
    private final AuthTokenCookieService authTokenCookieService;

    public WebClientConfig(JwtTokenService jwtTokenService, AuthTokenCookieService authTokenCookieService) {
        this.jwtTokenService = jwtTokenService;
        this.authTokenCookieService = authTokenCookieService;
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .filter(authHeaderFilter());
    }

    private ExchangeFilterFunction authHeaderFilter() {
        return (request, next) -> {
            String token = getTokenFromSession();
            if (token == null || token.isBlank()) {
                token = authTokenCookieService.readToken();
            }
            if (token == null || token.isBlank() || !jwtTokenService.isValid(token)) {
                return next.exchange(request);
            }
            ClientRequest authorized = ClientRequest.from(request)
                    .header("Authorization", "Bearer " + token)
                    .build();
            return next.exchange(authorized);
        };
    }

    private String getTokenFromSession() {
        try {
            VaadinSession session = VaadinSession.getCurrent();
            if (session == null) {
                return null;
            }
            Object token = session.getAttribute(AUTH_TOKEN_KEY);
            return token instanceof String value ? value : null;
        } catch (RuntimeException ex) {
            return null;
        }
    }
}
