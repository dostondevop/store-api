package com.zuhriddin.dao;

import com.zuhriddin.model.Product;
import com.zuhriddin.model.ProductDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.*;

public class ProductDao extends DatabaseConnection {
    private static final CategoryProductDao categoryProductDao = new CategoryProductDao();

    private static final String GET_PRODUCTS = "select * from get_product(i_id := ?, i_name := ?, i_price := ?, i_images := ?::json, i_params := ?::json, i_colours := ?::json, i_description := ?, i_discount := ?, i_from_delivery := ?, i_to_delivery := ?, i_created_date := ?, i_updated_date := ?)";
    private static final String ADD_PRODUCT = "select * from add_product(i_name := ?, i_price := ?, i_images := ?::json, i_params := ?::json, i_colours := ?::json, i_description := ?, i_discount := ?, i_from_delivery := ?, i_to_delivery := ?, i_created_by := ?, i_updated_by := ?, i_path := ?)";
    private static final String DELETE_PRODUCT = "select * from delete_product(i_id := ?)";
    private static final String UPDATE_PRODUCT = "select * from update_product(i_id := ?, i_name := ?, i_price := ?, i_description := ?, i_discount := ?, i_from_delivery := ?, i_to_delivery := ?, i_updated_by := ?)";
    private static final String GET_CATEGORY_PRODUCTS = "select * from get_category_product(i_category_id := ?)";
    private static final String GET_PRODUCTS_BY_ORDER_ID = "select * from get_products_by_order_id(i_order_id := ?)";

    public List<Product> getProducts(Product product) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS)) {
            preparedStatement.setObject(1, (product.getId() == 0 ? null : product.getId()), Types.INTEGER);
            preparedStatement.setObject(2, product.getName(), Types.VARCHAR);
            preparedStatement.setObject(3, (product.getPrice() == 0 ? null : product.getPrice()), Types.INTEGER);
            preparedStatement.setObject(4, product.getImages(), Types.VARCHAR);
            preparedStatement.setObject(5, product.getParams(), Types.VARCHAR);
            preparedStatement.setObject(6, product.getColours(), Types.VARCHAR);
            preparedStatement.setObject(7, product.getDescription(), Types.VARCHAR);
            preparedStatement.setObject(8, (product.getDiscount() == 0 ? null : product.getDiscount()), Types.INTEGER);
            preparedStatement.setObject(9, (product.getFromDelivery() == 0 ? null : product.getFromDelivery()), Types.INTEGER);
            preparedStatement.setObject(10, (product.getToDelivery() == 0 ? null : product.getToDelivery()), Types.INTEGER);
            preparedStatement.setObject(11, product.getCreatedDate(), Types.TIMESTAMP);
            preparedStatement.setObject(12, product.getUpdatedDate(), Types.TIMESTAMP);

            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product result = new Product(resultSet);
                productList.add(result);
            }
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public Product addProduct(Product product, String categoryPath) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(ADD_PRODUCT)) {
            String path = categoryPath + "/" + product.getName();

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getPrice());
            preparedStatement.setString(3, product.getImages());
            preparedStatement.setString(4, product.getParams());
            preparedStatement.setString(5, product.getColours());
            preparedStatement.setString(6, product.getDescription());
            preparedStatement.setInt(7, product.getDiscount());
            preparedStatement.setInt(8, product.getFromDelivery());
            preparedStatement.setInt(9, product.getToDelivery());
            preparedStatement.setString(10, product.getCreatedBy());
            preparedStatement.setString(11, product.getCreatedBy());
            preparedStatement.setString(12, path);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            Product result = new Product(resultSet);
            categoryProductDao.addCategoryProduct(categoryPath, result.getId());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public void deleteProduct(int productId) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PRODUCT)) {
            preparedStatement.setInt(1, productId);
            preparedStatement.execute();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public Product updateProduct(Product product) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PRODUCT)) {
            preparedStatement.setInt(1, product.getId());
            preparedStatement.setString(2, product.getName());
            preparedStatement.setInt(3, product.getPrice());
            preparedStatement.setString(4, product.getDescription());
            preparedStatement.setInt(5, product.getDiscount());
            preparedStatement.setInt(6, product.getFromDelivery());
            preparedStatement.setInt(7, product.getToDelivery());
            preparedStatement.setString(8, product.getUpdatedBy());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new Product(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public List<Product> getCategoryProducts(int categoryId) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_CATEGORY_PRODUCTS)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Product> productList = new ArrayList<>();
            while (resultSet.next()) {
                Product product = new Product(resultSet);
                productList.add(product);
            }
            return productList;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public List<ProductDto> getProductsByOrderId(int orderId) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_PRODUCTS_BY_ORDER_ID)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<ProductDto> list = new ArrayList<>();
            while (resultSet.next()) {
                ProductDto productDto = new ProductDto(resultSet);
                list.add(productDto);
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }
}
