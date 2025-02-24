package com.zuhriddin.dao;

import com.zuhriddin.model.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

public class CategoryDao extends DatabaseConnection{
    private static final String GET_CATEGORIES = "select * from get_category(i_id := ?, i_name := ?, i_parent_id := ?, i_created_date := ?, i_updated_date := ?)";
    private static final String ADD_CATEGORY = "select * from add_category(i_name := ?, i_created_by := ?, i_updated_by := ?, i_parent_id := ?)";
    private static final String DELETE_CATEGORY = "select * from delete_category(i_id := ?)";
    private static final String UPDATE_CATEGORY = "select * from update_category(i_id := ?, i_name := ?, i_updated_by := ?, i_parent_id := ?)";
    private static final String GET_CATEGORY_ID_BY_PATH = "select id from category where path = ?";
    private static final String GET_SUB_CATEGORIES = "select * from get_sub_categories(i_category_id := ?)";

    public List<Category> getCategories(Category category) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORIES)) {
            preparedStatement.setObject(1, (category.getId() == 0 ? null : category.getId()), Types.INTEGER);
            preparedStatement.setObject(2, category.getName(), Types.VARCHAR);
            preparedStatement.setObject(3, (category.getParentId() == 0 ? null : category.getParentId()), Types.INTEGER);
            preparedStatement.setObject(4, category.getCreatedDate(), Types.TIMESTAMP);
            preparedStatement.setObject(5, category.getUpdatedDate(), Types.TIMESTAMP);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Category> categoryList = new ArrayList<>();
            while (resultSet.next()) {
                Category result = new Category(resultSet);
                categoryList.add(result);
            }
            return categoryList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public Category addCategory(Category category) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_CATEGORY)) {
            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getCreatedBy());
            preparedStatement.setString(3, category.getCreatedBy());
            preparedStatement.setObject(4, (category.getParentId() == 0 ? null : category.getParentId()), Types.INTEGER);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Category(resultSet);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public void deleteCategory(int categoryID) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CATEGORY)) {
            preparedStatement.setInt(1, categoryID);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public Category updateCategory(Category category) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CATEGORY)) {
            preparedStatement.setInt(1, category.getId());
            preparedStatement.setString(2, category.getName());
            preparedStatement.setString(3, category.getUpdatedBy());
            preparedStatement.setObject(4, (category.getParentId() == 0 ? null : category.getParentId()), Types.INTEGER);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            System.out.println(resultSet);
            return new Category(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public int getCategoryIdByPath(String path) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_ID_BY_PATH)) {
            preparedStatement.setString(1, path);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return resultSet.getInt("id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public List<Category> getSubCategories(int categoryId) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_SUB_CATEGORIES)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Category> categoryList = new ArrayList<>();
            while (resultSet.next()) {
                Category category = new Category(resultSet);
                categoryList.add(category);
            }
            return categoryList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }
}
