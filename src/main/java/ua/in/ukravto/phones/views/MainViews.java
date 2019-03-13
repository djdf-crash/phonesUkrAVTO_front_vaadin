package ua.in.ukravto.phones.views;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainViews extends VerticalLayout {

    private LoginForm loginForm = new LoginForm();

    public MainViews() {
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        add(loginForm);
    }
}
