package com.doston.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.dao.CartDao;
import com.doston.model.Cart;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class CookieCartService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final CartService cartService = new CartService(new CartDao());

    public void addCartToCookie(Cart cart, HttpServletResponse response, HttpServletRequest request) throws IOException {
        HttpSession httpSession = request.getSession();
        String carts = (String) httpSession.getAttribute("cartList");
        boolean database = false;
        List<Cart> cartList;
        if (carts == null) {
            List<Cart> list = cartService.listUserCarts(cart.getUserId());
            if (list == null) {
                cartList = new ArrayList<>();
            } else {
                cartList = list;
                database = true;
            }
        } else {
            cartList = objectMapper.readValue(carts, new TypeReference<>() {
            });
        }
        if (!database) {
            cartList.add(cart);
        }
        String s = objectMapper.writeValueAsString(cartList);
        HttpSession session = request.getSession();
        session.setAttribute("cartList", s);
        PrintWriter writer = response.getWriter();
        writer.println(s);
    }

    public boolean checkCart(int productId, HttpServletRequest request, int userId) throws JsonProcessingException {
        HttpSession session = request.getSession();
        String cartList = (String) session.getAttribute("cartList");
        List<Cart> carts;
        if (cartList != null) {
            carts = objectMapper.readValue(cartList, new TypeReference<>() {});
        } else {
            carts = cartService.listUserCarts(userId);
        }
        Cart cart = carts.stream().filter(c -> c.getProductId() == productId)
                .findFirst().orElse(null);
        return cart == null;
    }
}
