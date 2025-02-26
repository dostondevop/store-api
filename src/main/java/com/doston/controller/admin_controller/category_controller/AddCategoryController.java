package com.doston.controller.admin_controller.category_controller;

import com.doston.controller.admin_controller.BaseController;
import com.doston.dao.CategoryDao;
import com.doston.model.Category;
import com.doston.model.User;
import com.doston.service.CategoryService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/add-category")
public class AddCategoryController extends HttpServlet implements BaseController {
    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.categoryService = new CategoryService(new CategoryDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String parentId = req.getParameter("parentId");
        String encodedUser = (String) req.getAttribute("authentication");
        User user = returnUserFromJson(encodedUser);
        Category category = new Category(name, Integer.parseInt(parentId), user.getEmail());
        categoryService.addCategory(category);
        resp.sendRedirect("/admin/category-list");
    }
}
