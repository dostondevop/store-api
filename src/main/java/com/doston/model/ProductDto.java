package com.doston.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDto {
    private int id;
    private String name;
    private int price;
    private String images;
    private String params;
    private String colours;
    private String description;
    private int discount;
    private int fromDelivery;
    private int toDelivery;
    private Date createdDate;
    private Date updatedDate;
    private int quantity;
    private String productStatus;

    public ProductDto(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt("product_id");
        this.name = resultSet.getString("product_name");
        this.price = resultSet.getInt("product_price");
        this.images = resultSet.getString("product_images");
        this.params = resultSet.getString("product_params");
        this.colours = resultSet.getString("product_colours");
        this.description = resultSet.getString("product_description");
        this.discount = resultSet.getInt("product_discount");
        this.fromDelivery = resultSet.getInt("product_from_delivery");
        this.toDelivery = resultSet.getInt("product_to_delivery");
        this.createdDate = resultSet.getDate("product_created_date");
        this.updatedDate = resultSet.getDate("product_updated_date");
        this.quantity = resultSet.getInt("product_quantity");
        this.productStatus = resultSet.getString("order_product_status");
    }
}
