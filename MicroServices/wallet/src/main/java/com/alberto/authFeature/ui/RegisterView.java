package com.alberto.authFeature.ui;

import com.alberto.authFeature.ui.util.PasswordStrengthEvaluator;
import com.alberto.authFeature.ui.util.PasswordStrengthEvaluator.Strength;
import com.alberto.integration.AuthenticationClient;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
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
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.NotificationVariant;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Route(value = "register", autoLayout = false)
@PageTitle("Register - MultiDigitalWallet")
@AnonymousAllowed
public class RegisterView extends VerticalLayout {

    private final AuthenticationClient authenticationClient;

    private static final int MIN_PASSWORD_LENGTH = 8;

    public RegisterView(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;

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
        firstName.setRequired(true);

        TextField lastName = new TextField("Last name");
        lastName.setRequired(true);

        EmailField email = new EmailField("Email address");
        email.setRequired(true);
        email.setErrorMessage("Enter a valid email address");

        PasswordField passwordField = new PasswordField();
        passwordField.setLabel("Password");
        passwordField.setWidth("14em");
        passwordField.setRequired(true);

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
            Strength strength = PasswordStrengthEvaluator.evaluate(event.getValue());
            passwordStrength.setText(strength.label());
            passwordStrength.setClassName("password-strength " + strength.name().toLowerCase());
            checkIcon.setVisible(strength == Strength.STRONG);
        });

        PasswordField confirmPassword = new PasswordField("Confirm password");
        confirmPassword.setRequired(true);

        FormLayout formLayout = new FormLayout();
        formLayout.setAutoResponsive(true);
        formLayout.addFormRow(firstName, lastName);
        formLayout.addFormRow(email);
        formLayout.addFormRow(passwordField, confirmPassword);
        formLayout.setWidthFull();

        Button createAccount = new Button("Create account");
        createAccount.addThemeVariants(ButtonVariant.PRIMARY);
        Button cancel = new Button("Cancel");

        cancel.addClickListener(e -> UI.getCurrent().navigate("login"));

        createAccount.addClickListener(e -> {

            clearErrors(firstName, lastName, email, passwordField, confirmPassword);

            if (!isValid(firstName, lastName, email, passwordField, confirmPassword)) return;

            createAccount.setEnabled(false);
            cancel.setEnabled(false);

            final UI ui           = UI.getCurrent();
            final String fullName = firstName.getValue().trim() + " " + lastName.getValue().trim();
            final String emailVal = email.getValue().trim();

            CompletableFuture.runAsync(() -> {
                try {

                    authenticationClient.register(emailVal, passwordField.getValue(), fullName);

                    ui.access(() -> {
                        Notification n = Notification.show("Account created! Welcome to MDW 🎉", 4000, Notification.Position.TOP_CENTER);
                        n.addThemeVariants(NotificationVariant.LUMO_SUCCESS);
                        ui.navigate("login");
                    });

                } catch (Exception ex) {

                    log.error("Registration failed for email={}", emailVal, ex);

                    ui.access(() -> {
                        Notification n = Notification.show("Registration failed. Please try again later.", 5000, Notification.Position.TOP_CENTER);
                        n.addThemeVariants(NotificationVariant.LUMO_ERROR);
                    });

                } finally {

                    ui.access(() -> {
                        createAccount.setEnabled(true);
                        cancel.setEnabled(true);
                    });
                }
            });
        });

        RouterLink loginLink = new RouterLink("Already have an account? Login", LoginView.class);

        H3 registerTitle = new H3("Register");

        HorizontalLayout buttonLayout = new HorizontalLayout(createAccount, cancel);

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

    private boolean isValid(TextField firstName, TextField lastName, EmailField email,
                            PasswordField password, PasswordField confirmPassword) {
        boolean valid = true;

        if (firstName.isEmpty()) {
            firstName.setInvalid(true);
            firstName.setErrorMessage("First name is required");
            valid = false;
        }

        if (lastName.isEmpty()) {
            lastName.setInvalid(true);
            lastName.setErrorMessage("Last name is required");
            valid = false;
        }

        if (email.isEmpty() || email.isInvalid()) {
            email.setInvalid(true);
            email.setErrorMessage("A valid email address is required");
            valid = false;
        }

        if (password.isEmpty()) {
            password.setInvalid(true);
            password.setErrorMessage("Password is required");
            valid = false;
        } else if (password.getValue().length() < MIN_PASSWORD_LENGTH) {
            password.setInvalid(true);
            password.setErrorMessage("Password must be at least " + MIN_PASSWORD_LENGTH + " characters");
            valid = false;
        } else if (PasswordStrengthEvaluator.evaluate(password.getValue()) == Strength.WEAK) {
            password.setInvalid(true);
            password.setErrorMessage("Password is too weak. Add uppercase letters, numbers, or symbols.");
            valid = false;
        }

        if (!password.getValue().equals(confirmPassword.getValue())) {
            confirmPassword.setInvalid(true);
            confirmPassword.setErrorMessage("Passwords do not match");
            valid = false;
        }

        return valid;
    }

    private void clearErrors(TextField firstName, TextField lastName, EmailField email,
                             PasswordField password, PasswordField confirmPassword) {
        firstName.setInvalid(false);
        lastName.setInvalid(false);
        email.setInvalid(false);
        password.setInvalid(false);
        confirmPassword.setInvalid(false);
    }
}