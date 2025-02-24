package com.zuhriddin.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CategoryProductDao extends DatabaseConnection{
    private static final String GET_CATEGORY_BY_PATH = "select id from category where path = ?";
    private static final String ADD_CATEGORY_PRODUCT = "select * from add_category_product(i_category_id := ?, i_product_id := ?)";

    public void addCategoryProduct(String path, int productId) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_BY_PATH);
             PreparedStatement preparedStatement1 = connection.prepareStatement(ADD_CATEGORY_PRODUCT)) {
            preparedStatement.setString(1, path);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int id = resultSet.getInt("id");
            preparedStatement1.setInt(1, id);
            preparedStatement1.setInt(2, productId);
            ResultSet resultSet1 = preparedStatement1.executeQuery();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }
}
