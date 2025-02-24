package com.zuhriddin.controller.admin_controller.order_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.dao.OrderDao;
import com.zuhriddin.model.Order;
import com.zuhriddin.service.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/update-order")
public class UpdateOrderController extends HttpServlet {
    private OrderService orderService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = new OrderService(new OrderDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String order = new String(req.getInputStream().readAllBytes());
        System.out.println(order);
        Order order1 = orderService.updateOrder(order);
        System.out.println(order1);
        resp.setContentType("application/json");
        resp.getWriter().write(objectMapper.writeValueAsString(order1));
        System.out.println(objectMapper.writeValueAsString(order1));
    }
}
