package com.doston.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {
    private int orderId;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private String orderStatus;
    private String promoCodeValue;
    private String promoCodeType;
    private Date promoCodeStartDate;
    private int promoCodeDays;
    private int promoCodeFixedAmount;
    private int promoCodePercent;
    private int promoCodeMinAmount;
    private LocalDateTime orderCreatedDate;
    private LocalDateTime orderUpdatedDate;

    public Order (ResultSet resultSet) throws SQLException {
        this.orderId = resultSet.getInt("order_id");
        this.userName = resultSet.getString("user_name");
        this.userPhoneNumber = resultSet.getString("user_phone_number");
        this.userEmail = resultSet.getString("user_email");
        this.orderStatus = resultSet.getString("order_status");
        this.promoCodeValue = resultSet.getString("promo_code_value");
        this.promoCodeType = resultSet.getString("promo_code_type");
        this.promoCodeStartDate = resultSet.getDate("promo_code_start_date");
        this.promoCodeDays = resultSet.getInt("promo_code_days");
        this.promoCodeFixedAmount = resultSet.getInt("promo_code_fixed_amount");
        this.promoCodePercent = resultSet.getInt("promo_code_percent");
        this.promoCodeMinAmount = resultSet.getInt("promo_code_min_amount");
        this.orderCreatedDate = resultSet.getObject("order_created_date", LocalDateTime.class);
        this.orderUpdatedDate = resultSet.getObject("order_updated_date", LocalDateTime.class);
    }

    public Order(int orderId, String userName, String promoCodeValue, String orderStatus) {
        this.orderId = orderId;
        this.userName = userName;
        this.promoCodeValue = promoCodeValue;
        this.orderStatus = orderStatus;
    }
}
