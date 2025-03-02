package com.doston.controller.user.cart;

import com.doston.dao.CartDao;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.model.CartDto;
import com.doston.model.User;
import com.doston.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/user/update-cart")
public class UserUpdateCartController extends HttpServlet {
    private final CartService cartService;
    private final ObjectMapper objectMapper;

    public UserUpdateCartController() {
        this.cartService = new CartService(new CartDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userOptional = extractAuthenticatedUser(req);

        if (userOptional.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User authentication required.");
            return;
        }

        try {
            List<CartDto> cartDtoList = objectMapper.readValue(req.getInputStream(), new TypeReference<>() {});
            cartService.updateCart(cartDtoList);
            resp.sendRedirect(req.getContextPath() + "/user/carts");
        } catch (IOException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid cart update data.");
        }
    }

    private Optional<User> extractAuthenticatedUser(HttpServletRequest req) {
        try {
            String authentication = (String) req.getAttribute("authentication");
            if (authentication == null || authentication.isBlank()) {
                return Optional.empty();
            }
            return Optional.of(objectMapper.readValue(authentication, User.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}