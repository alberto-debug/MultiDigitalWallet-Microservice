package com.alberto.authFeature.ui;

import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "", autoLayout = false)
@PageTitle("Login Page")
public class loginView extends VerticalLayout {


    loginView(){



        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        LoginForm loginForm = new LoginForm();
        loginForm.setAction("login");

        VerticalLayout content = new VerticalLayout(loginForm);
        content.setAlignItems(Alignment.CENTER);
        content.setSpacing(false);

//        ProgressBar progressBar = new ProgressBar();
//        progressBar.setIndeterminate(true);

        add(content);

    }

}
