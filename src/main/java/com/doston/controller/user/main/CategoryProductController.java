package com.doston.controller.user.main;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.dao.CartDao;
import com.doston.dao.CategoryDao;
import com.doston.model.Cart;
import com.doston.model.Category;
import com.doston.model.User;
import com.doston.service.CartService;
import com.doston.service.CategoryService;
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

@WebServlet("/user/category-product")
public class CategoryProductController extends HttpServlet {
    private CategoryService categoryService;
    private CartService cartService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.categoryService = new CategoryService(new CategoryDao());
        this.cartService = new CartService(new CartDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String authentication = (String) req.getAttribute("authentication");
        String decode = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
        User user = objectMapper.readValue(decode, User.class);
        List<Category> list = categoryService.getMainCategories();
        String cartList = (String) req.getSession().getAttribute("cartList");
        List<Cart> carts;
        if (cartList != null) {
            carts = objectMapper.readValue(cartList, new TypeReference<>() {});
        } else {
            carts = cartService.listUserCarts(user.getId());
        }
        req.setAttribute("cartsCount", carts.size());
        req.setAttribute("categories", list);
        req.getRequestDispatcher("category-product.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}