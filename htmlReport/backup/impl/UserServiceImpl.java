//package com.wenzhi.graphql_service.dml.service.impl;
//
//import com.wenzhi.graphql_service.dml.po.User;
//import com.wenzhi.graphql_service.dml.service.UserService;
//import org.springframework.stereotype.Service;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class UserServiceImpl implements UserService {
//    // 模拟数据库
//    private final List<User> users = new ArrayList<>();
//
//    public UserServiceImpl() {
//        // 添加一些测试数据
//        users.add(new User(1L, "张三", "zhangsan@example.com", 25));
//        users.add(new User(2L, "李四", "lisi@example.com", 30));
//        users.add(new User(3L, "王五", "wangwu@example.com", 35));
//    }
//
//    // 获取所有用户
//    public List<User> getAllUsers() {
//        return users;
//    }
//
//    // 根据ID获取用户
//    public Optional<User> getUserById(Long id) {
//        return users.stream()
//                .filter(user -> user.getId().equals(id))
//                .findFirst();
//    }
//
//    public User createUser(String name, String email, Integer age) {
//        return users.get(0);
//    }
//
//    public User updateUserName(Long id, String name) {
//        return users.get(0);
//    }
//
//    public Boolean deleteUser(Long id) {
//        return false;
//    }
//}
//
