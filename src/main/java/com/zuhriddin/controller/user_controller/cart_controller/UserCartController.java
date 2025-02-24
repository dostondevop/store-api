package com.zuhriddin.controller.user_controller.cart_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.dao.CartDao;
import com.zuhriddin.model.Cart;
import com.zuhriddin.model.User;
import com.zuhriddin.service.CartService;
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

@WebServlet("/user/carts")
public class UserCartController extends HttpServlet {
    private CartService cartService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.cartService = new CartService(new CartDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authentication = (String) req.getAttribute("authentication");
        String decode = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
        User user = objectMapper.readValue(decode, User.class);
        List<Cart> cartList = cartService.listUserCarts(user.getId());
        req.setAttribute("carts", cartList);
        req.getRequestDispatcher("carts.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
