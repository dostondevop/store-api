package com.doston.dao;

import com.doston.model.User;

import java.sql.Array;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class UserDao extends DatabaseConnection{
    private static final String ADD_USER = "select * from add_users(i_name := ?, i_phone_number := ?, i_password := ?, i_role := ?, i_privileges := ?::varchar[], i_email := ?)";
    private static final String GET_USER = "select * from check_user_existence(i_password := ?, i_email := ?)";

    public User addUser(User user) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {

            Array varchar = null;
            if (user.getPrivileges() != null) {
                List<String> list = Arrays.asList(user.getPrivileges());
                varchar = connection.createArrayOf("VARCHAR", list.toArray());
            }

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, user.getPassword());
            preparedStatement.setString(4, String.valueOf(user.getRole()));
            preparedStatement.setArray(5, varchar);
            preparedStatement.setString(6, user.getEmail());
            System.out.println(preparedStatement);

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            return new User(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }

    public User checkUser(String email, String password) {
        try (Connection connection = connection();
            PreparedStatement preparedStatement = connection.prepareStatement(GET_USER)) {
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, email);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new User(resultSet);
            } else {
                throw new RuntimeException("Email or password is incorrect.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Connection is not exist.");
        }
    }
}
