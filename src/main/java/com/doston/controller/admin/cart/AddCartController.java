package com.doston.controller.admin.cart;

import com.doston.dao.CartDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.doston.model.Cart;
import com.doston.model.User;
import com.doston.service.CartService;
import com.doston.service.CookieCartService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@WebServlet("/admin/add-cart")
public class AddCartController extends HttpServlet {

    private final CartService cartService;
    private final ObjectMapper objectMapper;
    private final CookieCartService cookieCartService;

    public AddCartController() {
        this.cartService = new CartService(new CartDao());
        this.objectMapper = new ObjectMapper();
        this.cookieCartService = new CookieCartService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            User user = extractAuthenticatedUser(req);
            ProductRequestDto productRequest = parseProductRequest(req);

            if (cookieCartService.checkCart(productRequest.getProductId(), req, user.getId())) {
                Cart cart = new Cart(user.getId(), productRequest.getProductId(), 1);
                Cart savedCart = cartService.addCart(cart);
                cookieCartService.addCartToCookie(savedCart, resp, req);
                sendSuccessResponse(resp);
            } else {
                sendErrorResponse(resp);
            }
        } catch (Exception e) {
            sendServerErrorResponse(resp, e);
        }
    }

    private User extractAuthenticatedUser(HttpServletRequest req) throws IOException {
        String authentication = (String) req.getAttribute("authentication");
        String decodedJson = URLDecoder.decode(authentication, StandardCharsets.UTF_8);
        return objectMapper.readValue(decodedJson, User.class);
    }

    private ProductRequestDto parseProductRequest(HttpServletRequest req) throws IOException {
        return objectMapper.readValue(req.getInputStream(), ProductRequestDto.class);
    }

    private void sendSuccessResponse(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\":\"success\"}");
    }

    private void sendErrorResponse(HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.getWriter().write("{\"status\":\"error\"}");
    }

    private void sendServerErrorResponse(HttpServletResponse resp, Exception e) throws IOException {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        resp.setContentType("application/json");
        resp.getWriter().write(String.format("{\"error\":\"%s\"}", e.getMessage()));
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private static class ProductRequestDto {
        private int productId;
    }
}