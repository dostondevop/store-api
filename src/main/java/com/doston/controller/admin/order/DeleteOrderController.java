package com.doston.controller.admin.order;

import com.doston.dao.OrderDao;
import com.doston.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/admin/delete-order")
public class DeleteOrderController extends HttpServlet {
    private final OrderService orderService;

    public DeleteOrderController() {
        this.orderService = new OrderService(new OrderDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<Integer> orderId = getOrderIdFromRequest(req);

            if (orderId.isPresent()) {
                orderService.deleteOrder(orderId.get());
                resp.sendRedirect("/admin/order-list");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid order ID");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error deleting order");
        }
    }

    private Optional<Integer> getOrderIdFromRequest(HttpServletRequest req) {
        try {
            String idParam = req.getParameter("id");
            if (idParam == null || idParam.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(Integer.parseInt(idParam));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }
}