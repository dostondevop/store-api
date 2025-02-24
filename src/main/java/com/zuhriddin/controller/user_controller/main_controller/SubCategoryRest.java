package com.zuhriddin.controller.user_controller.main_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/user/sub-category")
public class SubCategoryRest extends HttpServlet {
    private CategoryService categoryService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.categoryService = new CategoryService(new CategoryDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        List<Category> subCategories = categoryService.getSubCategories(Integer.parseInt(id));
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.println(objectMapper.writeValueAsString(subCategories));
    }
}
