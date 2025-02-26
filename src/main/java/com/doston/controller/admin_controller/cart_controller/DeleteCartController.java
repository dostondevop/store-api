package com.doston.controller.admin_controller.cart_controller;

import com.doston.dao.CartDao;
import com.doston.service.CartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/admin/delete-cart")
public class DeleteCartController extends HttpServlet {
    private final CartService cartService;

    public DeleteCartController() {
        this.cartService = new CartService(new CartDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Optional<String> idParam = Optional.ofNullable(req.getParameter("id"));

        if (idParam.isPresent() && !idParam.get().isBlank()) {
            try {
                int cartId = Integer.parseInt(idParam.get());
                boolean isDeleted = cartService.deleteCart(cartId);

                if (isDeleted) {
                    resp.sendRedirect("/admin/cart-list");
                } else {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Cart item not found.");
                }
            } catch (NumberFormatException e) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid ID format.");
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID parameter is required.");
        }
    }
}