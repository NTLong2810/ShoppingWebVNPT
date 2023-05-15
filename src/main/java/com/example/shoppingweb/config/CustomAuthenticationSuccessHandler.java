package com.example.shoppingweb.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        HttpSession session = request.getSession();

        String username = authentication.getName();
        String redirectUrl = "/home";

        for (GrantedAuthority authority : authentication.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_CUSTOMER")) {
                redirectUrl = "/home";
                break;
            } else if (authority.getAuthority().equals("ROLE_SELLER")) {
                redirectUrl = "/homeseller";
                break;
            } else if (authority.getAuthority().equals("ROLE_ADMIN")) {
                redirectUrl = "/homeadmin";
                break;
            }
        }

        session.setAttribute("username", username);
        response.sendRedirect(redirectUrl);
    }
}
