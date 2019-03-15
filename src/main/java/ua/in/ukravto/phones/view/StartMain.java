package ua.in.ukravto.phones.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinResponse;
import com.vaadin.flow.server.VaadinServletResponse;
import ua.in.ukravto.phones.repo.utils.ConstantAPI;
import ua.in.ukravto.phones.repo.utils.CookieManager;

import javax.servlet.http.Cookie;

@Route("")
public class StartMain extends VerticalLayout {

    private final CookieManager mCookieManager;

    public StartMain() {
        this.mCookieManager = new CookieManager((VaadinServletResponse) VaadinResponse.getCurrent());
        Cookie cookie = mCookieManager.getCookieByName(ConstantAPI.COOKIE_NAME);
        if (cookie == null){
            UI.getCurrent().navigate("login");
            UI.getCurrent().getPage().reload();
        }else {
            UI.getCurrent().navigate("main");
            UI.getCurrent().getPage().reload();
        }
    }
}
