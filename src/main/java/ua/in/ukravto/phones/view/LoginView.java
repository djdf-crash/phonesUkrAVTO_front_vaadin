package ua.in.ukravto.phones.view;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinServletResponse;
import retrofit2.Response;
import ua.in.ukravto.phones.domain.ResponseAPI;
import ua.in.ukravto.phones.repo.PhonesAPI;
import ua.in.ukravto.phones.repo.RestClientBuilder;
import ua.in.ukravto.phones.repo.utils.ConstantAPI;
import ua.in.ukravto.phones.repo.utils.CookieManager;

import java.io.IOException;

@Route("login")
public class LoginView extends VerticalLayout {

    private final PhonesAPI api;
    private final LoginForm loginForm;

    public LoginView() {

        setDefaultHorizontalComponentAlignment(Alignment.CENTER);

        this.api = RestClientBuilder.getService();
        this.loginForm = new LoginForm();

        setAlignItems(Alignment.CENTER);

        loginForm.setForgotPasswordButtonVisible(false);

        add(loginForm);
        loginForm.addLoginListener((ComponentEventListener<AbstractLogin.LoginEvent>) loginEvent -> {
            try {
                Response<ResponseAPI<String>> resp = api.logIn(loginEvent.getUsername(), UI.getCurrent().getSession().getBrowser().getAddress()).execute();

                if (resp.isSuccessful() && resp.body() != null && resp.body().getError().isEmpty()) {
                    loginForm.setError(false);

                    CookieManager cookieManager = new CookieManager((VaadinServletResponse) VaadinResponse.getCurrent());
                    cookieManager.createCookie(ConstantAPI.COOKIE_NAME, resp.body().getBody(), 36000);

                    UI.getCurrent().getSession().setAttribute("token",resp.body().getBody());
                    UI.getCurrent().navigate(MainView.class);
                    UI.getCurrent().getPage().reload();
                }else {
                    Notification.show(resp.body().getError(), 3000, Notification.Position.TOP_CENTER);
                    loginForm.setError(true);
                }

            } catch (IOException e) {
                Notification.show(e.getMessage(), 3000, Notification.Position.TOP_CENTER);
                loginForm.setError(true);
            }

        });
    }
}
