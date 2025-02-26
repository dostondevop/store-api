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

@WebServlet("/admin/update-product")
public class UpdateProductController extends HttpServlet implements BaseController {
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
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String price = req.getParameter("price");
        String description = req.getParameter("description");
        String discount = req.getParameter("discount");
        String fromDelivery = req.getParameter("fromDelivery");
        String toDelivery = req.getParameter("toDelivery");
        String encodedUser = (String) req.getAttribute("authentication");
        User user = returnUserFromJson(encodedUser);

        Product product = new Product(Integer.parseInt(id), name, Integer.parseInt(price), description,
                Integer.parseInt(discount), Integer.parseInt(fromDelivery), Integer.parseInt(toDelivery), user.getEmail());
        productService.updateProduct(product);
        resp.sendRedirect("/admin/product-list");
    }
}
