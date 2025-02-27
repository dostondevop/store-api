package com.doston.controller.admin.category;

import com.doston.dao.CategoryDao;
import com.doston.service.CategoryService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/delete-category")
public class DeleteCategoryController extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.categoryService = new CategoryService(new CategoryDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        categoryService.deleteCategory(Integer.parseInt(id));
        resp.sendRedirect("/admin/category-list");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
}