package com.zuhriddin.model;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.ResultSet;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Product {
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
    private String createdBy;
    private String updatedBy;
    private boolean active;
    private String path;

    public Product (ResultSet resultSet) throws Exception {
        this.id = resultSet.getInt("id");
        this.name = resultSet.getString("name");
        this.price = resultSet.getInt("price");
        this.images = resultSet.getString("images");
        this.params = resultSet.getString("params");
        this.colours = resultSet.getString("colours");
        this.description = resultSet.getString("description");
        this.discount = resultSet.getInt("discount");
        this.fromDelivery = resultSet.getInt("from_delivery");
        this.toDelivery = resultSet.getInt("to_delivery");
        this.createdDate = resultSet.getDate("created_date");
        this.updatedDate = resultSet.getDate("updated_date");
        this.createdBy = resultSet.getString("created_by");
        this.updatedBy = resultSet.getString("updated_by");
        this.path = resultSet.getString("path");
    }

    public Product(String name, int price, String images, String params, String colours, String description,
                   int discount, int fromDelivery, int toDelivery, String createdBy) {
        this.name = name;
        this.price = price;
        this.images = images;
        this.params = params;
        this.colours = colours;
        this.description = description;
        this.discount = discount;
        this.fromDelivery = fromDelivery;
        this.toDelivery = toDelivery;
        this.createdBy = createdBy;
    }

    public Product(int id, String name, int price, String description, int discount, int fromDelivery,
                   int toDelivery, String updatedBy) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.discount = discount;
        this.fromDelivery = fromDelivery;
        this.toDelivery = toDelivery;
        this.updatedBy = updatedBy;
    }
}
