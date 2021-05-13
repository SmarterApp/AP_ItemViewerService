package org.smarterbalanced.itemviewerservice.web.controllers;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;

import org.springframework.stereotype.Component;
import org.springframework.core.annotation.Order;

@Component
@Order(1)
public class SecurityFilter implements javax.servlet.Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // wrap the response
        response = new SecureCookieSetter((HttpServletResponse)response);

        System.out.println("Getting the request session id...");
        // touch the session
        ((HttpServletRequest) request).getSession();
        System.out.println("  Got the request session id: " + ((HttpServletRequest)request).getSession().getId());

        System.out.println("Setting the JSESSIONID header cookie to have SameSite=None;Secure;");
        // overwriting the cookie with Secure and HttpOnly attribute set
        ((HttpServletResponse)response).setHeader("Set-Cookie", "JSESSIONID=" + ((HttpServletRequest)request).getSession().getId() + ";Path=/;Secure;SameSite=None;");

        System.out.println("  JSESSIONID header cookie has been set with SameSite=None;Secure;");
        chain.doFilter(request, response);
    }

    public class SecureCookieSetter extends HttpServletResponseWrapper {

        public SecureCookieSetter(HttpServletResponse response) {
            super(response);
        }

        @Override
        public void addCookie(Cookie cookie) {
            cookie.setSecure(true);
            super.addCookie(cookie);
        }

        @Override
        public void addHeader(String name, String value) {
            if ((name.equals("Set-Cookie")) && (!value.matches("(^|.*;)\\s*Secure"))) {
                value = value + ";Secure;HttpOnly;SameSite=None;";
            }
            super.addHeader(name, value);
        }

        @Override
        public void setHeader(String name, String value) {
            if ((name.equals("Set-Cookie")) && (!value.matches("(^|.*;)\\s*Secure"))) {
                value = value + ";Secure;HttpOnly;SameSite=None;";
            }
            super.setHeader(name, value);
        }

    }
}