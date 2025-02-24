package com.zuhriddin.controller.user_controller.order_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.dao.CartDao;
import com.zuhriddin.dao.OrderDao;
import com.zuhriddin.dao.wrapper.OrderRequestWrapper;
import com.zuhriddin.model.Cart;
import com.zuhriddin.model.Order;
import com.zuhriddin.model.User;
import com.zuhriddin.service.CartService;
import com.zuhriddin.service.OrderService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/user/orders")
public class UserOrderController extends HttpServlet {
    private CartService cartService;
    private OrderService orderService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.cartService = new CartService(new CartDao());
        this.orderService = new OrderService(new OrderDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Do Get");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String agreement = req.getParameter("agreement");
        boolean agree = agreement == null;
        String authentication = (String) req.getAttribute("authentication");
        String decode = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
        User user = objectMapper.readValue(decode, User.class);
        List<Cart> cartList = cartService.listUserCarts(user.getId());
        OrderRequestWrapper orderRequestWrapper = OrderRequestWrapper.convert(user.getId(), cartList, agree);
        String s = objectMapper.writeValueAsString(orderRequestWrapper);
        List<Order> order = orderService.createOrder(s);
        removeCartsFromSession(req);
        resp.sendRedirect("/user/order-list");
    }

    private void removeCartsFromSession(HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.removeAttribute("cartList");
    }
}