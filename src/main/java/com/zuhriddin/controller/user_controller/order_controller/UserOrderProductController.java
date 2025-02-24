package com.zuhriddin.controller.user_controller.order_controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zuhriddin.dao.ProductDao;
import com.zuhriddin.model.ProductDto;
import com.zuhriddin.service.OrderService;
import com.zuhriddin.service.ProductDtoService;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/user/order-product")
public class UserOrderProductController extends HttpServlet {
    private ProductDtoService productDtoService;
    private ObjectMapper objectMapper;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.productDtoService = new ProductDtoService(new ProductDao());
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        List<ProductDto> list = productDtoService.listProductByOrderId(id);
        resp.setContentType("html/json");
        resp.getWriter().println(objectMapper.writeValueAsString(list));
    }
}
