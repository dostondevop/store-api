package com.doston.service;

import com.doston.dao.CartDao;
import com.doston.model.Cart;
import com.doston.model.CartDto;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.*;

@RequiredArgsConstructor
public class CartService {
    private final CartDao cartDao;

    public List<Cart> listCarts() {
        return cartDao.getCarts(new Cart());
    }

    public Cart addCart(Cart cart) {
        return cartDao.addCart(cart);
    }

    public boolean deleteCart(int cartId) {
        return cartDao.deleteCart(cartId);
    }

    public List<Cart> listCartsForUser() {
        List<Cart> carts = cartDao.getCarts(new Cart());
        setFirstImageToImages(carts);
        return carts;
    }

    private void setFirstImageToImages(List<Cart> cartList) {
        for (Cart cart: cartList) {
            JSONArray jsonArray = new JSONArray(cart.getProductImages());
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            String path = jsonObject.getString("path");
            cart.setProductImages(path);
        }
    }

    public List<Cart> listUserCarts(int userId) {
        List<Cart> cartList = cartDao.getCarts(Cart.builder().userId(userId).build());
        setFirstImageToImages(cartList);
        return cartList;
    }

    public void updateCart(List<CartDto> cartDtoList) {
        cartDtoList.forEach(c -> {
            cartDao.updateCart(c.getId(), c.getQuantity());
        });
    }

    public void addToCart(Cart cart) {
        cartDao.addCart(cart);
    }
}