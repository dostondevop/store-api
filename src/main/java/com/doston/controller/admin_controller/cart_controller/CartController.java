package com.doston.controller.admin_controller.cart_controller;

import com.doston.controller.admin_controller.BaseController;
import com.doston.dao.CartDao;
import com.doston.model.Cart;
import com.doston.model.User;
import com.doston.service.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/cart-list")
public class CartController extends HttpServlet implements BaseController {

    private CartService cartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        this.cartService = new CartService(new CartDao()); // Prefer Dependency Injection
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Cart> cartList = cartService.listCarts();
            req.setAttribute("cartList", cartList);

            Optional.ofNullable(req.getAttribute("authentication"))
                    .map(Object::toString)
                    .map(encodingJson -> {
                        try {
                            return returnUserFromJson(encodingJson);
                        } catch (JsonProcessingException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .ifPresent(user -> {
                        req.setAttribute("user", user);
                        req.setAttribute("privileges", Arrays.asList(user.getPrivileges()));
                    });

            req.getRequestDispatcher("/WEB-INF/views/admin/cart-list.jsp").forward(req, resp);
        } catch (Exception e) {
            log("Error retrieving cart list", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the request.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "POST method is not supported for this endpoint.");
    }
}