package org.smarterbalanced.itemviewerservice.web.controllers;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Cookie;

import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

public class SuccessRedirectHandler implements AuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        System.out.println("Authentication success! Checking headers...");

        System.out.println("Request headers:");
        Enumeration<String> requestHeaders = request.getHeaderNames();
        for (String currentHeader : Collections.list(requestHeaders)){
            Collection<String> currentHeaderInfo = response.getHeaders(currentHeader);
            for(String headerInfo : currentHeaderInfo){
                System.out.println("Header Name: " + currentHeader + "; Header value: " + headerInfo);
            }
        }

        System.out.println("Response headers:");
        Collection<String> responseHeaders = response.getHeaderNames();
        for (String currentHeader : responseHeaders){
            Collection<String> currentHeaderInfo = response.getHeaders(currentHeader);
            for(String headerInfo : currentHeaderInfo){
                System.out.println("Header Name: " + currentHeader + "; Header value: " + headerInfo);
            }
        }



        addSameSiteCookieAttribute(response);    // add SameSite=strict to Set-Cookie attribute
        response.sendRedirect("/");
    }

    private void addSameSiteCookieAttribute(HttpServletResponse response) {


        Collection<String> headersLower = response.getHeaders("set-cookie");
        Collection<String> headersUpper = response.getHeaders("Set-Cookie");
        boolean firstHeader = true;

        if (headersLower.isEmpty()){
            System.out.println("Unable to find any headers with the name 'set-cookie'.");
        }
        if (headersUpper.isEmpty()){
            System.out.println("Unable to find any headers with the name 'Set-Cookie'.");
        }
        for (String headerLower : headersLower) { // there can be multiple Set-Cookie attributes
            System.out.println("Header: " + headerLower);
            if (firstHeader) {
                response.setHeader("set-cookie", String.format("%s; %s", headerLower, "Secure;SameSite=None"));
                firstHeader = false;
                continue;
            }
            response.addHeader("set-cookie", String.format("%s; %s", headerLower, "Secure;SameSite=None"));
        }
        for (String headerUpper : headersUpper) { // there can be multiple Set-Cookie attributes
            System.out.println("Header: " + headerUpper);
            if (firstHeader) {
                response.setHeader("Set-Cookie", String.format("%s; %s", headerUpper, "Secure;SameSite=None"));
                firstHeader = false;
                continue;
            }
            response.addHeader("Set-Cookie", String.format("%s; %s", headerUpper, "Secure;SameSite=None"));
        }
    }
}
