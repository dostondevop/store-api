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
import java.util.Optional;

@WebServlet("/admin/update-category")
public class UpdateCategoryController extends HttpServlet implements BaseController {
    private final CategoryService categoryService;

    public UpdateCategoryController() {
        this.categoryService = new CategoryService(new CategoryDao());
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Optional<Category> categoryOpt = parseCategoryFromRequest(req);
            if (categoryOpt.isPresent()) {
                categoryService.updateCategory(categoryOpt.get());
                resp.sendRedirect("/admin/category-list");
            } else {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category data");
            }
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Failed to update category");
        }
    }

    private Optional<Category> parseCategoryFromRequest(HttpServletRequest req) {
        try {
            int id = Integer.parseInt(req.getParameter("id"));
            String name = req.getParameter("name");
            int parentId = Integer.parseInt(req.getParameter("parentId"));
            String encodedUser = (String) req.getAttribute("authentication");

            if (name == null || name.trim().isEmpty() || encodedUser == null) {
                return Optional.empty();
            }

            User user = BaseController.super.returnUserFromJson(encodedUser);
            return Optional.of(new Category(id, name, parentId, user.getEmail()));

        } catch (NumberFormatException e) {
            return Optional.empty();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}