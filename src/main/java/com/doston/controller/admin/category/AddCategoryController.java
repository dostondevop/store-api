package com.doston.controller.admin.category;

import com.doston.controller.admin.BaseController;
import com.doston.model.Category;
import com.doston.model.User;
import com.doston.service.CategoryService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Optional;

@WebServlet("/admin/add-category")
public class AddCategoryController extends HttpServlet implements BaseController {

    private final CategoryService categoryService;

    public AddCategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String name = Optional.ofNullable(req.getParameter("name")).orElse("").trim();
            String parentIdStr = Optional.ofNullable(req.getParameter("parentId")).orElse("0").trim();
            String encodedUser = (String) req.getAttribute("authentication");

            if (name.isEmpty() || encodedUser == null) {
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid category data");
                return;
            }

            User user = BaseController.super.returnUserFromJson(encodedUser);
            int parentId = parseParentId(parentIdStr);

            Category category = new Category(name, parentId, user.getEmail());
            categoryService.addCategory(category);

            resp.sendRedirect("/admin/category-list");
        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error adding category");
        }
    }

    private int parseParentId(String parentIdStr) {
        try {
            return Integer.parseInt(parentIdStr);
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}