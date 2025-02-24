package com.zuhriddin.controller.user_controller;

import com.zuhriddin.dao.CategoryDao;
import com.zuhriddin.model.Category;
import com.zuhriddin.service.CategoryService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/example")
public class ExampleController extends HttpServlet {
    private CategoryService categoryService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.categoryService = new CategoryService(new CategoryDao());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Category> mainCategories = categoryService.getMainCategories();
        req.setAttribute("categories", mainCategories);
        req.getRequestDispatcher("example.jsp").forward(req, resp);
    }
}
