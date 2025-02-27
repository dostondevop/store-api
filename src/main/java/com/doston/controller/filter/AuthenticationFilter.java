package com.doston.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    private static final List<String> PUBLIC_ROUTES = Arrays.asList("/", "/login", "/register");

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI();

        if (PUBLIC_ROUTES.contains(requestURI)) {
            filterChain.doFilter(request, response);
            return;
        }

        String user = getUserFromCookies(request);

        if (user == null) {
            redirectToLogin(request, response, requestURI);
            return;
        }

        refreshAuthCookie(response, user);
        request.setAttribute("authentication", user);
        filterChain.doFilter(request, response);
    }

    private String getUserFromCookies(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("user".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    private void redirectToLogin(HttpServletRequest request, HttpServletResponse response, String requestURI) throws IOException {
        HttpSession session = request.getSession();
        session.setAttribute("redirectUrl", requestURI);
        response.sendRedirect("/login");
    }

    private void refreshAuthCookie(HttpServletResponse response, String user) {
        Cookie cookie = new Cookie("user", user);
        cookie.setMaxAge(180);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}