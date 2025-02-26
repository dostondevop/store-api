package com.doston.service;

import com.doston.dao.OrderDao;
import com.doston.model.Order;
import com.doston.model.OrderDto;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
public class OrderService {
    private final OrderDao orderDao;

    public List<Order> listOrders () {
        return orderDao.getOrders(new OrderDto(), 1);
    }

    public void deleteOrder(int orderId) {
        orderDao.deleteOrder(orderId);
    }

    public List<Order> createOrder(String orderJson) {
        return orderDao.createOrder(orderJson);
    }

    public List<Order> listOrdersByUserId(int userId, int n) {
        return orderDao.getOrders(OrderDto.builder().userId(userId).build(), n);
    }

    public Order updateOrder (String orderJson) {
        return orderDao.updateOrder(orderJson);
    }
}
