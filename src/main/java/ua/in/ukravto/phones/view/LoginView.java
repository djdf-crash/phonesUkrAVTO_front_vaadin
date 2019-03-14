package ua.in.ukravto.phones.view;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import retrofit2.Response;
import ua.in.ukravto.phones.domain.ResponseToken;
import ua.in.ukravto.phones.repo.PhonesAPI;
import ua.in.ukravto.phones.repo.RestClientBuilder;
import ua.in.ukravto.phones.repo.utils.CookieManager;

import java.io.IOException;

@Route
public class LoginView extends VerticalLayout {

    private final PhonesAPI api;
    private final LoginForm loginForm;
    private final CookieManager mCookieManager;

    public LoginView() {
        this.mCookieManager = new CookieManager(VaadinService.getCurrentResponse());
        this.api = RestClientBuilder.getService();
        this.loginForm = new LoginForm();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        add(loginForm);
        loginForm.addLoginListener((ComponentEventListener<AbstractLogin.LoginEvent>) loginEvent -> {
            try {
                Response<ResponseToken<String>> resp = api.logIn(loginEvent.getUsername(), "111000").execute();
                mCookieManager.createCookie("UKRAVTO","TEST", 36000);
                UI.getCurrent().navigate("main");
                Notification.show(resp.body().getError(), 1000, Notification.Position.TOP_CENTER);
            } catch (IOException e) {
                Notification.show(e.getMessage(), 2500, Notification.Position.TOP_CENTER);
            }

        });
    }
}
