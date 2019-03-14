package ua.in.ukravto.phones.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinService;
import ua.in.ukravto.phones.repo.utils.CookieManager;

import javax.servlet.http.Cookie;

@Route("main")
public class MainView extends VerticalLayout {

    private final CookieManager mCookieManager;

    public MainView() {
        this.mCookieManager = new CookieManager(VaadinService.getCurrentResponse());
        add("TEST");

        Cookie cookie = mCookieManager.getCookieByName("UKRAUTO");

        if (cookie == null){
            UI.getCurrent().navigate("");
        }

    }
}
