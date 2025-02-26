package com.doston.service;

import com.doston.dao.UserDao;
import com.doston.model.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;

    public User registerUser(User user) {
        return userDao.addUser(user);
    }

    public User login (String email, String password) {
        return userDao.checkUser(email, password);
    }
}
