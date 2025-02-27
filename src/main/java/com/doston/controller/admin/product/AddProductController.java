package com.doston.controller.admin.product;

import com.doston.controller.admin.BaseController;
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

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/add-product")
@MultipartConfig
public class AddProductController extends HttpServlet implements BaseController {
    private ProductService productService;
    private static final Logger LOGGER = Logger.getLogger(AddProductController.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productService = new ProductService(new ProductDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = getParameter(req, "name").orElseThrow(() -> new IllegalArgumentException("Product name is required"));
            int price = parseInteger(req, "price", "Invalid price format");
            String description = getParameter(req, "description").orElse("");
            int discount = parseInteger(req, "discount", "Invalid discount format");
            int fromDelivery = parseInteger(req, "fromDelivery", "Invalid fromDelivery format");
            int toDelivery = parseInteger(req, "toDelivery", "Invalid toDelivery format");
            String category = getParameter(req, "category").orElseThrow(() -> new IllegalArgumentException("Category is required"));

            String[] parameterNames = req.getParameterValues("paramName[]");
            String[] parameterTypes = req.getParameterValues("paramType[]");
            String[] parameterValues = req.getParameterValues("paramValue[]");

            String encodedUser = (String) req.getAttribute("authentication");
            User user = returnUserFromJson(encodedUser);

            productService.addProduct(req, parameterNames, parameterTypes, parameterValues,
                    name, price, description, discount, fromDelivery, toDelivery, user.getEmail(), category);

            resp.sendRedirect("/admin/product-list");

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, "Validation error: " + e.getMessage());
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error adding product", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }

    private Optional<String> getParameter(HttpServletRequest req, String name) {
        String value = req.getParameter(name);
        return (value == null || value.isBlank()) ? Optional.empty() : Optional.of(value);
    }

    private int parseInteger(HttpServletRequest req, String paramName, String errorMessage) {
        try {
            return Integer.parseInt(getParameter(req, paramName).orElse("0"));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}