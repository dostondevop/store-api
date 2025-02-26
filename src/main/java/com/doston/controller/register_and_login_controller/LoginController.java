package com.doston.controller.register_and_login_controller;

import com.fasterxml.jackson.core.JsonProcessingException;
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

        User login = userService.login(email, password);
        addUsernameToCookie(resp, login);

        if (login.getRole().equals(UserRole.ADMIN)) {
            redirectBySession(req, resp, "/admin/category-list");
        } else {
            redirectBySession(req, resp, "/user/category-product");
        }
    }

    private void redirectBySession(HttpServletRequest req, HttpServletResponse resp, String url) throws IOException {
        HttpSession session = req.getSession();
        Object redirectUrl = session.getAttribute("redirectUrl");
        if (redirectUrl != null) {
            resp.sendRedirect((String) redirectUrl);
        } else {
            resp.sendRedirect(url);
            session.removeAttribute("redirectUrl");
        }
    }

    protected void addUsernameToCookie(HttpServletResponse response, User user) throws JsonProcessingException {
        String userJson = objectMapper.writeValueAsString(user);
        String encodedJson = URLEncoder.encode(userJson, StandardCharsets.UTF_8);

        Cookie cookie = new Cookie("user", encodedJson);
        cookie.setMaxAge(3 * 60);
        response.addCookie(cookie);
    }
}
