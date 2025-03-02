package com.doston.controller.user.cart;

import com.doston.dao.CartDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.service.CartService;
import com.doston.model.Cart;
import com.doston.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

@WebServlet("/user/carts")
public class UserCartController extends HttpServlet {
    private final CartService cartService;
    private final ObjectMapper objectMapper;

    public UserCartController() {
        this.cartService = new CartService(new CartDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userOptional = extractAuthenticatedUser(req);

        if (userOptional.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User authentication required.");
            return;
        }

        User user = userOptional.get();
        List<Cart> cartList = cartService.listUserCarts(user.getId());

        req.setAttribute("carts", cartList);
        req.getRequestDispatcher("carts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<User> userOptional = extractAuthenticatedUser(req);

        if (userOptional.isEmpty()) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User authentication required.");
            return;
        }

        User user = userOptional.get();
        Cart cart = objectMapper.readValue(req.getReader(), Cart.class);
        cart.setUserId(user.getId());

        cartService.addToCart(cart);

        resp.setStatus(HttpServletResponse.SC_CREATED);
        resp.getWriter().write("Cart item added successfully.");
    }

    private Optional<User> extractAuthenticatedUser(HttpServletRequest req) {
        try {
            String authentication = (String) req.getAttribute("authentication");
            if (authentication == null || authentication.isBlank()) {
                return Optional.empty();
            }
            String decodedAuth = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
            return Optional.of(objectMapper.readValue(decodedAuth, User.class));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}