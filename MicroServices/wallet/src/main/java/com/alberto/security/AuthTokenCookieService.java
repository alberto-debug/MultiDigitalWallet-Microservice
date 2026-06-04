package com.alberto.security;

import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinServletResponse;
import jakarta.servlet.http.Cookie;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;

@Component
public class AuthTokenCookieService {

    private static final String COOKIE_NAME = "AUTH_TOKEN";

    public void writeToken(String token, Instant expiresAt) {
        var response = VaadinService.getCurrentResponse();
        var request = VaadinService.getCurrentRequest();
        
        if (response instanceof VaadinServletResponse servletResponse) {
            ResponseCookie.ResponseCookieBuilder builder = ResponseCookie.from(COOKIE_NAME, token)
                    .httpOnly(true)
                    .path("/")
                    .sameSite("Lax");
            
            if (expiresAt != null) {
                Duration duration = Duration.between(Instant.now(), expiresAt);
                builder.maxAge(duration);
            }
            
            if (request instanceof VaadinServletRequest servletRequest) {
                if (servletRequest.getHttpServletRequest().isSecure()) {
                    builder.secure(true);
                }
            }
            
            servletResponse.getHttpServletResponse().addHeader("Set-Cookie", builder.build().toString());
        }
    }

    public String readToken() {
        var request = VaadinService.getCurrentRequest();
        if (request instanceof VaadinServletRequest servletRequest) {
            Cookie[] cookies = servletRequest.getHttpServletRequest().getCookies();
            if (cookies == null) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (COOKIE_NAME.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    public void clearToken() {
        writeToken("", Instant.EPOCH);
    }
}
