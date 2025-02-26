package com.doston.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Cart {
    private int id;
    private int userId;
    private String userName;
    private String userPhoneNumber;
    private String userEmail;
    private int productId;
    private String productName;
    private int productPrice;
    private String productImages;
    private String productParams;
    private String productColours;
    private String productDescription;
    private int productDiscount;
    private int productFromDelivery;
    private int productToDelivery;
    private int quantity;
    private Date createdDate;
    private Date updatedDate;

    public Cart(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("id");
        this.userId = resultSet.getInt("user_id");
        this.userName = resultSet.getString("user_name");
        this.userPhoneNumber = resultSet.getString("user_phone_number");
        this.userEmail = resultSet.getString("user_email");
        this.productId = resultSet.getInt("product_id");
        this.productName = resultSet.getString("product_name");
        this.productPrice = resultSet.getInt("product_price");
        this.productImages = resultSet.getString("product_images");
        this.productParams = resultSet.getString("product_params");
        this.productColours = resultSet.getString("product_colours");
        this.productDescription = resultSet.getString("product_description");
        this.productDiscount = resultSet.getInt("product_discount");
        this.productFromDelivery = resultSet.getInt("product_from_delivery");
        this.productToDelivery = resultSet.getInt("product_to_delivery");
        this.quantity = resultSet.getInt("quantity");
        this.createdDate = resultSet.getDate("created_date");
        this.updatedDate = resultSet.getDate("updated_date");
    }

    public Cart(int userId, int productId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.quantity = quantity;
    }
}
