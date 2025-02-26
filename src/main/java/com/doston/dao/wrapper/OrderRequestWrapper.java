package com.doston.dao.wrapper;

import com.doston.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderRequestWrapper{
    private int promoCodeId;
    private int userId;
    private boolean agreement;
    private List<Product> products;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    private class Product {
        private int productId;
        private int quantity;
    }


    public static OrderRequestWrapper convert(int userId, List<Cart> cartList, boolean agreement) {
        List<Product> list = cartList.stream().map(cart -> new OrderRequestWrapper ().new Product(cart.getProductId(), cart.getQuantity())).toList();
        return OrderRequestWrapper.builder()
                .userId(userId)
                .promoCodeId(1)
                .products(list)
                .agreement(agreement)
                .build();
    }
}
