package com.doston.controller.admin_controller.order_controller;

import com.doston.dao.OrderDao;
import com.doston.service.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete-order")
public class DeleteOrderController extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.orderService = new OrderService(new OrderDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        orderService.deleteOrder(Integer.parseInt(id));
        resp.sendRedirect("/admin/order-list");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
