package com.doston.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderDto {
    private int orderId;
    private int userId;
    private String userName;
    private String promoCodeValue;
    private String productName;
    private int productPrice;
    private int productQuantity;
    private String orderStatus;
    private Date fromCreatedDate;
    private Date toCreatedDate;
}
