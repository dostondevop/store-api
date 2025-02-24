package com.zuhriddin.controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String requestURI = request.getRequestURI();
        if (requestURI.equals("/") || requestURI.equals("/login") || requestURI.equals("/register")) {
            filterChain.doFilter(request, response);
        } else {

            Cookie[] cookies = request.getCookies();
            String user = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    user = cookie.getValue();
                }
            }
            if (user == null) {
                HttpSession session = request.getSession();
                session.setAttribute("redirectUrl", requestURI);
                response.sendRedirect("/login");
                return;
            }
            addUsernameToCookie(response, user);
            request.setAttribute("authentication", user);
            filterChain.doFilter(request, response);
        }
    }

    protected void addUsernameToCookie(HttpServletResponse response, String user) {
        Cookie cookie = new Cookie("user", user);
        cookie.setMaxAge(3*60);
        response.addCookie(cookie);
    }
}
