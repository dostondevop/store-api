package com.doston.controller.admin_controller.product_controller;

import com.doston.controller.admin_controller.BaseController;
import com.doston.dao.ProductDao;
import com.doston.model.Product;
import com.doston.model.User;
import com.doston.service.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

@WebServlet("/admin/product-list")
public class ProductController extends HttpServlet implements BaseController {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService(new ProductDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Product> productList = productService.listProducts();
        List<String> listCategoryPaths = productService.listCategoryPaths();
        req.setAttribute("productList", productList);
        req.setAttribute("categories", listCategoryPaths);
        String encodedUser = (String) req.getAttribute("authentication");
        User user = returnUserFromJson(encodedUser);
        if (user.getPrivileges() != null) {
            List<String> privileges = Arrays.asList(user.getPrivileges());
            req.setAttribute("privileges", privileges);
        }

        req.getRequestDispatcher("product-list.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
