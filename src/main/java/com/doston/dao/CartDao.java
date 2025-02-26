package com.doston.dao;

import com.doston.model.Cart;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

public class CartDao extends DatabaseConnection {
    private static final String GET_CARTS = "select * from get_cart(i_id := ?, i_user_id := ?, i_product_id := ?, i_quantity := ?, i_created_date := ?, i_updated_date := ?)";
    private static final String ADD_CART = "select * from add_cart(i_user_id := ?, i_product_id := ?, i_quantity := ?)";
    private static final String DELETE_CART = "select * from delete_cart(i_id := ?)";
    private static final String UPDATE_CART = "select * from update_cart(i_id := ?, i_quantity := ?)";

    public List<Cart> getCarts(Cart cart) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CARTS)) {
            preparedStatement.setObject(1, (cart.getId() == 0 ? null : cart.getId()), Types.INTEGER);
            preparedStatement.setObject(2, (cart.getUserId() == 0 ? null : cart.getUserId()), Types.INTEGER);
            preparedStatement.setObject(3, (cart.getProductId() == 0 ? null : cart.getProductId()), Types.INTEGER);
            preparedStatement.setObject(4, (cart.getQuantity() == 0 ? null : cart.getQuantity()), Types.INTEGER);
            preparedStatement.setObject(5, cart.getCreatedDate(), Types.TIMESTAMP);
            preparedStatement.setObject(6, cart.getUpdatedDate(), Types.TIMESTAMP);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Cart> cartList = new ArrayList<>();
            while (resultSet.next()) {
                Cart result = new Cart(resultSet);
                cartList.add(result);
            }
            return cartList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public Cart addCart(Cart cart) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CART)) {
            preparedStatement.setInt(1, cart.getUserId());
            preparedStatement.setInt(2, cart.getProductId());
            preparedStatement.setInt(3, cart.getQuantity());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Cart(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public boolean deleteCart (int cartId) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CART)) {
            preparedStatement.setInt(1, cartId);
            return preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public void updateCart(int cartId, int quantity) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CART)) {
            preparedStatement.setInt(1, cartId);
            preparedStatement.setInt(2, quantity);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }
}
