package com.doston.controller.register_and_login;

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
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;
import java.util.Optional;

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
        String[] privileges = req.getParameterValues("privileges");

        if (!validateInputs(name, phoneNumber, password, email, role)) {
            resp.sendRedirect("/register?error=Invalid input");
            return;
        }

        UserRole userRole;
        try {
            userRole = UserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            resp.sendRedirect("/register?error=Invalid role");
            return;
        }

        Optional<User> existingUser = userService.findByEmail(email);
        if (existingUser.isPresent()) {
            resp.sendRedirect("/register?error=Email already registered");
            return;
        }

        String hashedPassword = hashPassword(password);

        User user = new User(name, phoneNumber, hashedPassword, userRole, email, privileges);
        userService.registerUser(user);

        resp.sendRedirect("/login?success=Registered successfully");
    }

    private boolean validateInputs(String name, String phoneNumber, String password, String email, String role) {
        return name != null && !name.isBlank()
                && phoneNumber != null && !phoneNumber.isBlank()
                && password != null && password.length() >= 6
                && email != null && email.contains("@")
                && role != null;
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }
}