package com.doston;

import com.doston.dao.OrderDao;
import com.doston.model.Order;
import com.doston.model.OrderDto;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
        OrderDto order = new OrderDto();
        List<Order> orders = orderDao.getOrders(order, 2);
        System.out.println(orders);
    }
}
