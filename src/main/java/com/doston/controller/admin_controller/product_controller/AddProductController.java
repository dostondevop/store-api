package com.doston.controller.admin_controller.product_controller;

import com.doston.controller.admin_controller.BaseController;
import com.doston.dao.ProductDao;
import com.doston.model.User;
import com.doston.service.ProductService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.*;

@WebServlet("/admin/add-product")
@MultipartConfig
public class AddProductController extends HttpServlet implements BaseController {
    private ProductService productService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService(new ProductDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        int price = Integer.parseInt(req.getParameter("price"));
        String description = req.getParameter("description");
        int discount = Integer.parseInt(req.getParameter("discount"));
        int fromDelivery = Integer.parseInt(req.getParameter("fromDelivery"));
        int toDelivery = Integer.parseInt(req.getParameter("toDelivery"));
        String category = req.getParameter("category");

        String[] parameterNames = req.getParameterValues("paramName[]");
        String[] parameterTypes = req.getParameterValues("paramType[]");
        String[] parameterValues = req.getParameterValues("paramValue[]");

        String encodedUser = (String) req.getAttribute("authentication");
        User user = returnUserFromJson(encodedUser);

        productService.addProduct(req, parameterNames, parameterTypes, parameterValues,
                name, price, description, discount, fromDelivery, toDelivery, user.getEmail(), category);
        resp.sendRedirect("/admin/product-list");
    }
}
