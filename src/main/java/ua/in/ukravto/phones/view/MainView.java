package ua.in.ukravto.phones.view;

import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.login.AbstractLogin;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import retrofit2.Response;
import ua.in.ukravto.phones.domain.ResponseToken;
import ua.in.ukravto.phones.repo.PhonesAPI;
import ua.in.ukravto.phones.repo.RestClientBuilder;

import java.io.IOException;

@Route
public class MainView extends VerticalLayout {

    private final PhonesAPI api;
    private final LoginForm loginForm;

    public MainView() {
        this.api = RestClientBuilder.getService();
        this.loginForm = new LoginForm();
        setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        setAlignItems(Alignment.CENTER);
        add(loginForm);
        loginForm.addLoginListener((ComponentEventListener<AbstractLogin.LoginEvent>) loginEvent -> {
//            api.logIn(loginEvent.getUsername(), "111000").enqueue(new Callback<ResponseToken>() {
//                @Override
//                public void onResponse(Call<ResponseToken> call, Response<ResponseToken> response) {
//                    Notification.show(response.toString());
//                }
//
//                @Override
//                public void onFailure(Call<ResponseToken> call, Throwable throwable) {
//                    Notification.show(throwable.toString());
//                }
//            });
            try {
                Response<ResponseToken<String>> resp = api.logIn(loginEvent.getUsername(), "111000").execute();
                Notification.show(resp.body().getError(), 1000, Notification.Position.TOP_CENTER);
            } catch (IOException e) {
                Notification.show(e.getMessage(), 250, Notification.Position.TOP_CENTER);
            }
        });
    }
}
