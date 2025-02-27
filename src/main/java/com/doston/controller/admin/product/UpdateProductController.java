package com.doston.controller.admin.product;

import com.doston.controller.admin.BaseController;
import com.doston.dao.ProductDao;
import com.doston.model.Product;
import com.doston.model.User;
import com.doston.service.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/update-product")
public class UpdateProductController extends HttpServlet implements BaseController {
    private ProductService productService;
    private static final Logger LOGGER = Logger.getLogger(UpdateProductController.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService(new ProductDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<Product> productOptional = parseProductFromRequest(req);
            if (productOptional.isEmpty()) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid product data");
                return;
            }

            productService.updateProduct(productOptional.get());
            resp.sendRedirect("/admin/product-list");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error updating product", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error updating product");
        }
    }

    private Optional<Product> parseProductFromRequest(HttpServletRequest req) {
        try {
            String id = req.getParameter("id");
            String name = req.getParameter("name");
            String price = req.getParameter("price");
            String description = req.getParameter("description");
            String discount = req.getParameter("discount");
            String fromDelivery = req.getParameter("fromDelivery");
            String toDelivery = req.getParameter("toDelivery");

            Optional<User> optionalUser = getAuthenticatedUser(req);
            return optionalUser.map(user -> new Product(
                    Integer.parseInt(id),
                    name,
                    Integer.parseInt(price),
                    description,
                    Integer.parseInt(discount),
                    Integer.parseInt(fromDelivery),
                    Integer.parseInt(toDelivery),
                    user.getEmail()
            ));

        } catch (NumberFormatException e) {
            LOGGER.log(Level.WARNING, "Invalid number format in request parameters", e);
            return Optional.empty();
        }
    }

    private Optional<User> getAuthenticatedUser(HttpServletRequest req) {
        String encodedUser = (String) req.getAttribute("authentication");
        if (encodedUser != null) {
            try {
                return Optional.of(BaseController.super.returnUserFromJson(encodedUser));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return Optional.empty();
    }
}