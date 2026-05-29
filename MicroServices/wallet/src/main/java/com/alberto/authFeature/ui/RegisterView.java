package com.alberto.authFeature.ui;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.FlexComponent.JustifyContentMode;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;

@Route(value = "register", autoLayout = false)
@PageTitle("Register - MultiDigitalWallet")
public class RegisterView extends VerticalLayout {

    public RegisterView() {
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

        TextField firstName = new TextField("First name");
        TextField lastName = new TextField("Last name");
        EmailField email = new EmailField("Email address");
        email.setErrorMessage("Enter a valid email address");

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setWidth("14em");

        Icon checkIcon = VaadinIcon.CHECK.create();
        checkIcon.setClassName("strong");
        checkIcon.setVisible(false);
        passwordField.setSuffixComponent(checkIcon);

        Span passwordStrength = new Span();
        passwordStrength.addClassName("password-strength");
        passwordStrength.addClassName("weak");
        passwordField.setHelperComponent(new Div(new Text("Password strength: "), passwordStrength));

        passwordField.setValueChangeMode(ValueChangeMode.EAGER);
        passwordField.addValueChangeListener(event -> {
            String strength = getPasswordStrength(event.getValue());
            passwordStrength.setText(strength);
            passwordStrength.setClassName("password-strength " + strength);
            checkIcon.setVisible("strong".equals(strength));
        });

        PasswordField confirmPassword = new PasswordField("Confirm password");

        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.addFormRow(firstName, lastName);
        formLayout.addFormRow(email);
        formLayout.addFormRow(passwordField, confirmPassword);

        formLayout.setWidthFull();

        Button createAccount = new Button("Create account");
        createAccount.addThemeVariants(ButtonVariant.PRIMARY);
        Button cancel = new Button("Cancel");

        HorizontalLayout buttonLayout = new HorizontalLayout(createAccount, cancel);

        RouterLink loginLink = new RouterLink("Already have an account? Login", loginView.class);

        H3 registerTitle = new H3("Register");

        Div registerCard = new Div(registerTitle, formLayout, buttonLayout, loginLink);
        registerCard.addClassName("register-card");

        VerticalLayout content = new VerticalLayout(registerCard);
        content.setAlignItems(Alignment.CENTER);
        content.setSpacing(false);

        Paragraph pageFooterText = new Paragraph("© 2026 MultiDigital Wallet ltd. All rights reserved.");
        Div pageFooter = new Div(pageFooterText);
        pageFooter.addClassName("login-page-footer");

        add(navbar, content, pageFooter);
    }

    private static String getPasswordStrength(String password) {
        if (password == null || password.isBlank()) {
            return "weak";
        }
        int length = password.length();
        if (length > 9) {
            return "strong";
        }
        if (length > 5) {
            return "moderate";
        }
        return "weak";
    }
}
