package com.doston.controller.admin_controller.order_controller;

import com.doston.controller.admin_controller.BaseController;
import com.doston.dao.OrderDao;
import com.doston.model.Order;
import com.doston.model.User;
import com.doston.service.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/admin/order-list")
public class OrderController extends HttpServlet implements BaseController {
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = new OrderService(new OrderDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Order> orderList = orderService.listOrders();
        req.setAttribute("orderList", orderList);
        String encodedUser = (String) req.getAttribute("authentication");
        User user = returnUserFromJson(encodedUser);
        if (user.getPrivileges() != null) {
            List<String> privileges = Arrays.asList(user.getPrivileges());
            req.setAttribute("privileges", privileges);
        }
        req.getRequestDispatcher("order-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
