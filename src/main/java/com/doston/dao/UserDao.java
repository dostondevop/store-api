package com.doston.dao;

import com.doston.model.User;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class UserDao extends DatabaseConnection {
    private static final String ADD_USER =
            "SELECT * FROM add_users(i_name := ?, i_phone_number := ?, i_password := ?, i_role := ?, i_privileges := ?::varchar[], i_email := ?)";

    private static final String GET_USER_BY_EMAIL =
            "SELECT * FROM users WHERE email = ?";

    public User addUser(User user) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_USER)) {

            Array privilegesArray = null;
            if (user.getPrivileges() != null) {
                List<String> privilegeList = Arrays.asList(user.getPrivileges());
                privilegesArray = connection.createArrayOf("VARCHAR", privilegeList.toArray());
            }

            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPhoneNumber());
            preparedStatement.setString(3, hashedPassword);
            preparedStatement.setString(4, user.getRole().name());
            preparedStatement.setArray(5, privilegesArray);
            preparedStatement.setString(6, user.getEmail());

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return new User(resultSet);
                }
            }
            throw new RuntimeException("User registration failed.");
        } catch (SQLException e) {
            throw new RuntimeException("Database error while adding user: " + e.getMessage(), e);
        }
    }

    public Optional<User> getUserByEmail(String email) {
        try (Connection connection = connection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_USER_BY_EMAIL)) {

            preparedStatement.setString(1, email);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return Optional.of(new User(resultSet));
                }
            }
            return Optional.empty();
        } catch (SQLException e) {
            throw new RuntimeException("Database error while fetching user by email: " + e.getMessage(), e);
        }
    }

    public Optional<User> authenticateUser(String email, String password) {
        Optional<User> userOptional = getUserByEmail(email);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (BCrypt.checkpw(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }
}