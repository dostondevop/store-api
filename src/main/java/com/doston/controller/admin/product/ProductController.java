package com.doston.controller.admin.product;

import com.doston.controller.admin.BaseController;
import com.doston.dao.ProductDao;
import com.doston.model.User;
import com.doston.service.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/product-list")
public class ProductController extends HttpServlet implements BaseController {
    private ProductService productService;
    private static final Logger LOGGER = Logger.getLogger(ProductController.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService(new ProductDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("productList", productService.listProducts());
            req.setAttribute("categories", productService.listCategoryPaths());

            getAuthenticatedUser(req).ifPresent(user -> {
                List<String> privileges = Arrays.asList(user.getPrivileges());
                req.setAttribute("privileges", privileges);
            });

            req.getRequestDispatcher("product-list.jsp").forward(req, resp);
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching product list", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error retrieving product data");
        }
    }

    @SneakyThrows
    private Optional<User> getAuthenticatedUser(HttpServletRequest req) {
        String encodedUser = (String) req.getAttribute("authentication");
        return Optional.ofNullable(encodedUser)
                .map(BaseController.super::returnUserFromJson);
    }
}