package com.alberto.authFeature.ui;

import com.alberto.integration.AuthenticationClient;
import com.alberto.security.AuthTokenCookieService;
import com.alberto.security.SecurityService;
import com.alberto.security.JwtTokenService;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinService;
import com.vaadin.flow.server.VaadinServletRequest;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;

import java.util.List;


@Route(value = "login", autoLayout = false)
@PageTitle("Login Page")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements RouterLayout, BeforeEnterObserver {

    private final AuthenticationClient authenticationClient;

    private final SecurityService securityService;

    private final JwtTokenService jwtTokenService;

    private final AuthTokenCookieService authTokenCookieService;

    LoginForm loginForm = new LoginForm();

    LoginView(AuthenticationClient authenticationClient, SecurityService securityService, JwtTokenService jwtTokenService, AuthTokenCookieService authTokenCookieService){
        this.authenticationClient = authenticationClient;
        this.securityService = securityService;
        this.jwtTokenService = jwtTokenService;
        this.authTokenCookieService = authTokenCookieService;

        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        HorizontalLayout navbar = new HorizontalLayout();
        navbar.addClassName("login-navbar");

        Image navbarIcon = new Image("./images/wallet.png", "Wallet");
        navbarIcon.addClassName("login-navbar-icon");

        Span navbarTitle = new Span("MultiDigital Wallet");
        navbarTitle.addClassName("login-navbar-title");

        navbar.add(navbarIcon, navbarTitle);

        LoginI18n i18n = LoginI18n.createDefault();
        i18n.getForm().setUsername("Email");
        i18n.getForm().setTitle("Login");
        if (i18n.getHeader() == null) {
            i18n.setHeader(new LoginI18n.Header());
        }
        i18n.getHeader().setTitle("Login");
        loginForm.setI18n(i18n);

        loginForm.addLoginListener(event -> handleLogin(event.getUsername(), event.getPassword()));

        RouterLink registerLink = new RouterLink("Create account", RegisterView.class);

        VerticalLayout content = new VerticalLayout(loginForm, registerLink);
        content.setAlignItems(Alignment.CENTER);
        content.setSpacing(false);

        Paragraph pageFooterText = new Paragraph("© 2026 MultiDigital Wallet ltd. All rights reserved.");
        Div pageFooter = new Div(pageFooterText);
        pageFooter.addClassName("login-page-footer");

        add(navbar, content, pageFooter);
    }

    private void handleLogin(String email, String password) {
        try {
            var response = authenticationClient.response(email, password);
            if (response == null || response.token() == null || response.token().isBlank()) {
                loginForm.setError(true);
                return;
            }

            if (!jwtTokenService.isValid(response.token())) {
                loginForm.setError(true);
                return;
            }

            String username = response.userData().name();
            String role = response.userData().roles().stream().findFirst().orElse("USER");
            String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;


            org.springframework.security.core.userdetails.User userDetails =
                    new org.springframework.security.core.userdetails.User(
                            username,
                            "",
                            List.of(new SimpleGrantedAuthority(authority))
                    );

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContext context = SecurityContextHolder.createEmptyContext();
            context.setAuthentication(authToken);

            SecurityContextHolder.setContext(context);


            VaadinSession.getCurrent().setAttribute("authToken", response.token());
            VaadinSession.getCurrent().setAttribute("authUsername", username);
            VaadinSession.getCurrent().setAttribute("authRole", role);
            VaadinSession.getCurrent().setAttribute("authUserId", response.userData().id());
            VaadinSession.getCurrent().setAttribute("authEmail", response.userData().email());

            authTokenCookieService.writeToken(response.token(), jwtTokenService.getExpiresAt(response.token()));

            var request = VaadinService.getCurrentRequest();
            if (request instanceof VaadinServletRequest servletRequest) {
                HttpSession session = servletRequest.getHttpServletRequest().getSession(true);
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, context);
            }

            UI.getCurrent().navigate("tasks");

        } catch (Exception ex) {
            ex.printStackTrace();
            loginForm.setError(true);
        }
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

        if (securityService.getAuthenticatedUser().isPresent()) {
            event.forwardTo("tasks");
            return;
        }

        if (event.getLocation()

                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
            loginForm.setError(true);
        }
    }


}
