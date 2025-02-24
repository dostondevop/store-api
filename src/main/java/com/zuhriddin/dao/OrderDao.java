package com.zuhriddin.dao;

import com.zuhriddin.model.Order;
import com.zuhriddin.model.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

public class OrderDao extends DatabaseConnection {
    private static final String GET_ORDERS = "select * from get_orders(i_order_id := ?, i_user_name := ?, i_promo_code_value := ?, i_product_name := ?, i_product_price := ?, i_product_quantity := ?, i_order_status := ?, i_order_from_created_date := ?, i_order_to_created_date := ?, i_user_id := ?) limit ? offset ?";
    private static final String DELETE_ORDER = "select * from delete_order(i_id := ?)";
    private static final String CREATE_ORDER = "select * from create_order(i_order_json := ?::jsonb) as id";
    private static final String UPDATE_ORDER = "select * from update_order(i_order_json := ?::jsonb)";

    public List<Order> createOrder(String orderJson) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_ORDER)){
            preparedStatement.setString(1,orderJson);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            return getOrders(OrderDto.builder().orderId(id).build(), 1);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public List<Order> getOrders(OrderDto orderDto, int n) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ORDERS)) {
            preparedStatement.setObject(1, (orderDto.getOrderId() == 0 ? null : orderDto.getOrderId()), Types.INTEGER);
            preparedStatement.setObject(2, orderDto.getUserName(), Types.VARCHAR);
            preparedStatement.setObject(3, orderDto.getPromoCodeValue(), Types.VARCHAR);
            preparedStatement.setObject(4, orderDto.getProductName(), Types.VARCHAR);
            preparedStatement.setObject(5, (orderDto.getProductPrice() == 0 ? null : orderDto.getProductPrice()), Types.INTEGER);
            preparedStatement.setObject(6, (orderDto.getProductQuantity() == 0 ? null : orderDto.getProductQuantity()), Types.INTEGER);
            preparedStatement.setObject(7, orderDto.getOrderStatus(), Types.VARCHAR);
            preparedStatement.setObject(8, orderDto.getFromCreatedDate(), Types.TIMESTAMP);
            preparedStatement.setObject(9, orderDto.getToCreatedDate(), Types.TIMESTAMP);
            preparedStatement.setObject(10, (orderDto.getUserId() == 0 ? null : orderDto.getUserId()), Types.INTEGER);
            preparedStatement.setInt(11, 5);
            preparedStatement.setInt(12, (n - 1)*5);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Order> orderList = new ArrayList<>();
            while (resultSet.next()) {
                Order order = new Order(resultSet);
                orderList.add(order);
            }
            return orderList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public void deleteOrder(int orderId) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER)) {
            preparedStatement.setInt(1, orderId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public Order updateOrder(String orderJson) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER)) {
            preparedStatement.setString(1, orderJson);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Order(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }
}
