package com.alberto.authFeature.ui;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLayout;


@Route(value = "", autoLayout = false)
@PageTitle("Login Page")
public class loginView extends VerticalLayout implements RouterLayout {

    loginView(){

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

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        VerticalLayout content = new VerticalLayout(loginForm);
        content.setAlignItems(Alignment.CENTER);
        content.setSpacing(false);

        Paragraph pageFooterText = new Paragraph("© 2026 MultiDigital Wallet ltd. All rights reserved.");
        Div pageFooter = new Div(pageFooterText);
        pageFooter.addClassName("login-page-footer");

        add(navbar, content, pageFooter);

    }


}
