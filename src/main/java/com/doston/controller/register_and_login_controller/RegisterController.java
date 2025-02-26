package com.doston.controller.register_and_login_controller;

import com.doston.dao.UserDao;
import com.doston.model.User;
import com.doston.model.enumaration.UserRole;
import com.doston.service.UserService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/register")
public class RegisterController extends HttpServlet {
    private UserService userService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.userService = new UserService(new UserDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String phoneNumber = req.getParameter("phoneNumber");
        String password = req.getParameter("password");
        String email = req.getParameter("email");
        String role = req.getParameter("role");

        UserRole userRole = UserRole.valueOf(role);
        String[] privileges = req.getParameterValues("privileges");

        User user = new User(name, phoneNumber, password, userRole, email, privileges);
        userService.registerUser(user);
        resp.sendRedirect("index.jsp");
    }
}
