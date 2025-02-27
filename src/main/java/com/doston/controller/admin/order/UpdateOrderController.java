package com.doston.controller.admin.order;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.dao.OrderDao;
import com.doston.model.Order;
import com.doston.service.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/update-order")
public class UpdateOrderController extends HttpServlet {
    private OrderService orderService;
    private ObjectMapper objectMapper;
    private static final Logger LOGGER = Logger.getLogger(UpdateOrderController.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = new OrderService(new OrderDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");

        try {
            Order order = parseRequest(req);

            if (order == null || order.getId() == 0) {
                sendErrorResponse(resp, HttpServletResponse.SC_BAD_REQUEST, "Invalid order data");
                return;
            }

            Order updatedOrder = orderService.updateOrder(new String(req.getInputStream().readAllBytes()));

            sendSuccessResponse(resp, updatedOrder);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating order", e);
            sendErrorResponse(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private Order parseRequest(HttpServletRequest req) throws IOException {
        return objectMapper.readValue(req.getInputStream(), Order.class);
    }

    private void sendSuccessResponse(HttpServletResponse resp, Order order) throws IOException {
        String jsonResponse = objectMapper.writeValueAsString(order);
        resp.setStatus(HttpServletResponse.SC_OK);
        resp.getWriter().write(jsonResponse);
    }

    private void sendErrorResponse(HttpServletResponse resp, int statusCode, String message) throws IOException {
        resp.setStatus(statusCode);
        resp.getWriter().write("{\"error\": \"" + message + "\"}");
    }
}