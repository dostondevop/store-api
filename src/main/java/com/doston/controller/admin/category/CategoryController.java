package com.doston.controller.admin.category;

import com.doston.controller.admin.BaseController;
import com.doston.dao.CategoryDao;
import com.doston.model.Category;
import com.doston.model.User;
import com.doston.service.CategoryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/admin/category-list")
public class CategoryController extends HttpServlet implements BaseController {
    private CategoryService categoryService;
    private static final Logger LOGGER = Logger.getLogger(CategoryController.class.getName());

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.categoryService = new CategoryService(new CategoryDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Category> categoryList = categoryService.listCategory();
            req.setAttribute("categoryList", categoryList);

            Optional<User> optionalUser = getAuthenticatedUser(req);
            optionalUser.ifPresent(user -> req.setAttribute("privileges", Arrays.asList(user.getPrivileges())));

            req.getRequestDispatcher("category-list.jsp").forward(req, resp);

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error fetching categories", e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error fetching categories");
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