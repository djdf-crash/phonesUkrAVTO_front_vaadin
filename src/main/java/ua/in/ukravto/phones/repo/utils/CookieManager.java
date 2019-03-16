package ua.in.ukravto.phones.repo.utils;

import com.vaadin.flow.server.VaadinRequest;
import com.vaadin.flow.server.VaadinService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieManager {

    private HttpServletResponse response;

    public CookieManager(HttpServletResponse response){
        this.response = response;
    }

    public Cookie getCookieByName(final String name) {
        // Fetch all cookies from the request
        Cookie[] cookies = ((HttpServletRequest)VaadinRequest.getCurrent()).getCookies();

        // Iterate to find cookie by its name
        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                return cookie;
            }
        }
        return null;
    }

    public Cookie createCookie(final String name, final String value, final int maxAge) {

        // Create a new cookie
        final Cookie cookie = new Cookie(name, value);

        cookie.setMaxAge(maxAge);

        // Set the cookie path.
        cookie.setPath(((HttpServletRequest)VaadinRequest.getCurrent()).getContextPath());

        // Save cookie
        addCookie(cookie);

        return cookie;
    }

    private void addCookie(Cookie cookie){
        response.addCookie(cookie);
    }

    public Cookie updateCookieValue(final String name, final String value) {
        // Create a new cookie
        Cookie cookie = getCookieByName(name);

        cookie.setValue(value);

        // Save cookie
        addCookie(cookie);

        return cookie;
    }

    public void destroyCookieByName(final String name) {
        Cookie cookie = getCookieByName(name);

        if (cookie != null) {
            cookie.setValue(null);
            // By setting the cookie maxAge to 0 it will deleted immediately
            cookie.setMaxAge(0);
            cookie.setPath(VaadinService.getCurrentRequest().getContextPath());
            addCookie(cookie);
        }
    }
}
