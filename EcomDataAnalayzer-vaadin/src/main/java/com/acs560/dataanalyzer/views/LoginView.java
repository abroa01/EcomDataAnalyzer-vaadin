package com.acs560.dataanalyzer.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;

/**
 * The login form for Ecom Data Analyzer
 */
@Route("login")
@PageTitle("Login | Ecom Data Analyzer")
@AnonymousAllowed
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    private static final long serialVersionUID = 574154935938093394L;
    private final LoginForm login = new LoginForm();

    /**
     * Constructor
     */
    public LoginView() {
        addClassName("login-view");
        setSizeFull();
        setAlignItems(Alignment.CENTER);
        setJustifyContentMode(JustifyContentMode.CENTER);

        // Set the action for the login form
        login.setAction("login");

        // Add header and login form to the layout
        add(new H1("Ecom Data Analyzer"));
        
        // For development/testing purposes, you may add helper information. Remove for production.
        add(new Span("Username: user, Password: userpass"));
        add(new Span("Username: admin, Password: adminpass"));

        add(login);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        // Check if there's an authentication error and show it
        if (beforeEnterEvent.getLocation()
            .getQueryParameters()
            .getParameters()
            .containsKey("error")) {
            login.setError(true);
        }
    }
}
