package com.doston.controller.user_controller.order_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/user/order-list")
public class UserOrderListController extends HttpServlet {
    private OrderService orderService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = new OrderService(new OrderDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authentication = (String) req.getAttribute("authentication");
        int n = Integer.parseInt(req.getParameter("n"));
        String decode = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
        User user = objectMapper.readValue(decode, User.class);
        List<Order> orders = orderService.listOrdersByUserId(user.getId(), n);
        req.setAttribute("orders", orders);
        req.getRequestDispatcher("orders.jsp").forward(req, resp);
    }
}
