package com.techelevator.dao;

import com.techelevator.model.UpdatePasswordDto;
import com.techelevator.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();

    User getUserById(int userId);

    User findByUsername(String username);

    int findIdByUsername(String username);

    boolean create(String username, String password, String role);

    boolean updatePassword(UpdatePasswordDto user);

    List<String> getAllUsernames();
}
