//package com.wenzhi.graphql_service.resolver;
//
////import com.coxautodev.graphql.tools.GraphQLQueryResolver;
//import com.wenzhi.graphql_service.dml.po.User;
//import com.wenzhi.graphql_service.dml.service.UserService;
//import graphql.kickstart.tools.GraphQLQueryResolver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Optional;
//
//@Component
//public class UserResolver implements GraphQLQueryResolver {
//
//    private final UserService userService;
//
//    @Autowired
//    public UserResolver(UserService userService) {
//        this.userService = userService;
//    }
//
//    // 对应GraphQL中的allUsers查询
//    public List<User> allUsers() {
//        return userService.getAllUsers();
//    }
//
//    // 对应GraphQL中的user查询
//    public Optional<User> user(Long id) {
//        return userService.getUserById(id);
//    }
//}
//
//
///*
//* GraphQL 的设计目标是减少网络请求，通过一次请求获取多种资源，因此不需要通过不同 HTTP 方法区分操作类型。
//所有操作通过 POST 发送，请求体中包含完整的操作定义（查询或变更），更灵活且易于扩展
//* */