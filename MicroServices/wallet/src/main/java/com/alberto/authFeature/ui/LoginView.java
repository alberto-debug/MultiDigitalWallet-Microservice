package com.alberto.authFeature.ui;

import com.alberto.security.SecurityService;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;


@Route(value = "login", autoLayout = false)
@PageTitle("Login Page")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements RouterLayout, BeforeEnterObserver {

    private final SecurityService securityService;

    LoginForm loginForm = new LoginForm();

    LoginView(SecurityService securityService){
        this.securityService = securityService;

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

        loginForm.setAction("login");

        RouterLink registerLink = new RouterLink("Create account", RegisterView.class);

        VerticalLayout content = new VerticalLayout(loginForm, registerLink);
        content.setAlignItems(Alignment.CENTER);
        content.setSpacing(false);

        Paragraph pageFooterText = new Paragraph("© 2026 MultiDigital Wallet ltd. All rights reserved.");
        Div pageFooter = new Div(pageFooterText);
        pageFooter.addClassName("login-page-footer");

        add(navbar, content, pageFooter);


    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {

//        if (securityService.getAuthenticatedUser().isPresent()) {
//            event.forwardTo(TaskListView.class);
//            return;
//        }

        if (event.getLocation()

                .getQueryParameters()
                .getParameters()
                .containsKey("error")){
            loginForm.setError(true);
        }
    }


}
