package com.zuhriddin.controller.admin_controller.product_controller;

import com.zuhriddin.controller.admin_controller.BaseController;
import com.zuhriddin.dao.CategoryDao;
import com.zuhriddin.dao.ProductDao;
import com.zuhriddin.model.Category;
import com.zuhriddin.model.Product;
import com.zuhriddin.model.User;
import com.zuhriddin.service.CategoryService;
import com.zuhriddin.service.ProductService;
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
