package com.doston.controller.admin.order;

import com.doston.controller.admin.BaseController;
import com.doston.dao.OrderDao;
import com.doston.model.User;
import com.doston.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@WebServlet("/admin/order-list")
public class OrderController extends HttpServlet implements BaseController {
    private final OrderService orderService;

    public OrderController() {
        this.orderService = new OrderService(new OrderDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("orderList", orderService.listOrders());
            addUserPrivilegesToRequest(req);
            req.getRequestDispatcher("order-list.jsp").forward(req, resp);
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching orders");
        }
    }

    private void addUserPrivilegesToRequest(HttpServletRequest req) {
        Optional.ofNullable((String) req.getAttribute("authentication"))
                .map(encodingJson -> {
                    try {
                        return BaseController.super.returnUserFromJson(encodingJson);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .map(User::getPrivileges)
                .map(List::of)
                .ifPresent(privilegesList -> req.setAttribute("privileges", privilegesList));
    }
}