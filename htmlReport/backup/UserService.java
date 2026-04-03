package com.wenzhi.graphql_service.dml.service;

import com.wenzhi.graphql_service.dml.po.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public List<User> getAllUsers();

    public Optional<User> getUserById(Long id);

    public User createUser(String name, String email, Integer age);

    public User updateUserName(Long id, String name);

    public Boolean deleteUser(Long id);
}
