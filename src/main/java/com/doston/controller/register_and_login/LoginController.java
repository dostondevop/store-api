package com.doston.controller.register_and_login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.dao.UserDao;
import com.doston.model.User;
import com.doston.model.enumaration.UserRole;
import com.doston.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/login")
public class LoginController extends HttpServlet {
    private UserService userService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = new UserService(new UserDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            resp.sendRedirect("/login?error=Invalid credentials");
            return;
        }

        User loginUser = userService.login(email, password);
        if (loginUser == null) {
            resp.sendRedirect("/login?error=Invalid credentials");
            return;
        }

        addUserToCookie(resp, loginUser);
        redirectUser(req, resp, loginUser);
    }

    private void redirectUser(HttpServletRequest req, HttpServletResponse resp, User user) throws IOException {
        HttpSession session = req.getSession();
        String redirectUrl = (String) session.getAttribute("redirectUrl");
        session.removeAttribute("redirectUrl");

        if (redirectUrl != null && !redirectUrl.isBlank()) {
            resp.sendRedirect(redirectUrl);
        } else {
            String defaultRedirect = user.getRole() == UserRole.ADMIN ? "/admin/category-list" : "/user/category-product";
            resp.sendRedirect(defaultRedirect);
        }
    }

    private void addUserToCookie(HttpServletResponse response, User user) throws IOException {
        String userJson = objectMapper.writeValueAsString(user);
        String encodedJson = URLEncoder.encode(userJson, StandardCharsets.UTF_8);

        Cookie userCookie = new Cookie("user", encodedJson);
        userCookie.setMaxAge(3 * 60);
        userCookie.setHttpOnly(true);
        userCookie.setSecure(true);

        response.addCookie(userCookie);
    }
}