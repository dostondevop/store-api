package com.zuhriddin;

import com.zuhriddin.dao.OrderDao;
import com.zuhriddin.model.Order;
import com.zuhriddin.model.OrderDto;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
        OrderDto order = new OrderDto();
        List<Order> orders = orderDao.getOrders(order, 2);
        System.out.println(orders);
    }
}
