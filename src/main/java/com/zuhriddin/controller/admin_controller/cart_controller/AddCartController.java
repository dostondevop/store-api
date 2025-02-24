package com.zuhriddin.controller.admin_controller.cart_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.dao.CartDao;
import com.zuhriddin.model.Cart;
import com.zuhriddin.model.User;
import com.zuhriddin.service.CartService;
import com.zuhriddin.service.CookieCartService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@WebServlet("/admin/add-cart")
public class AddCartController extends HttpServlet {
    private CartService cartService;
    private ObjectMapper objectMapper;
    private CookieCartService cookieCartService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.cartService = new CartService(new CartDao());
        this.objectMapper = new ObjectMapper();
        this.cookieCartService = new CookieCartService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authentication = (String) req.getAttribute("authentication");
        String decodedJson = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
        User user = objectMapper.readValue(decodedJson, User.class);
        ProductRequestDto productRequestDto = objectMapper.readValue(req.getInputStream(), ProductRequestDto.class);
        if (cookieCartService.checkCart(productRequestDto.productId, req, user.getId())) {
            Cart cart = new Cart(user.getId(), productRequestDto.getProductId(), 1);
            Cart cart1 = cartService.addCart(cart);
            cookieCartService.addCartToCookie(cart1, resp, req);
        } else {
            resp.setContentType("html/json");
            resp.getWriter().println("{\"position\":\"error\"}");
        }
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class ProductRequestDto {
        private int productId;
    }
}
