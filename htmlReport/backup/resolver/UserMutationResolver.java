//package com.wenzhi.graphql_service.resolver;
//
//import com.wenzhi.graphql_service.dml.po.User;
//import com.wenzhi.graphql_service.dml.service.UserService;
////import graphql.kickstart.annotations.GraphQLMutationResolver;
//import graphql.kickstart.tools.GraphQLMutationResolver;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//@Component
//public class UserMutationResolver implements GraphQLMutationResolver {
//    private final UserService userService;
//
//    @Autowired
//    public UserMutationResolver(UserService userService) {
//        this.userService = userService;
//    }
//
//    // 创建用户（对应 POST）
//    public User createUser(String name, String email, Integer age) {
//        return userService.createUser(name, email, age);
//    }
//
//    // 更新用户（对应 PUT）
//    public User updateUser(Long id, String name) {
//        return userService.updateUserName(id, name);
//    }
//
//    // 删除用户（对应 DELETE）
//    public Boolean deleteUser(Long id) {
//        return userService.deleteUser(id);
//    }
//}
//
///*
//*
//* graphql-spring-boot-starter 不依赖 HTTP 方法（GET/POST 等）区分操作，而是通过：
//Query 类型 + GraphQLQueryResolver 处理 “查询”（类似 GET）。
//Mutation 类型 + GraphQLMutationResolver 处理 “增删改”（类似 POST/PUT/DELETE）。
//所有请求通过 POST 方法发送到统一的 /graphql 端点。
//*
//* 1. 发送查询请求（类似 REST 的 GET）
//* http://localhost:8080/graphql
//* {
//  "query": "query { user(id: 1) { id name email } }"
//}
//*
//* 2. 发送变更变更请求（类似 REST 的 POST/PUT/DELETE）
//* {
//  "query": "mutation { createUser(name: \"张三\", email:\"test@example.com\") { id name } }"
//}
//*
//* graphql.servlet.path=/api/graphql  # 自定义端点路径
//server.port=8081                   # 自定义端口
//*
//*
//* */
//
///*
//* fetch('http://localhost:8080/graphql', {
//  method: 'POST',
//  headers: {
//    'Content-Type': 'application/json',
//  },
//  body: JSON.stringify({
//    query: 'query { user(id: 1) { id name } }'  // 你的GraphQL查询
//  })
//})
//.then(response => response.json())
//.then(data => console.log(data))  // 打印返回结果
//.catch(error => console.error(error));
//*
//* ==> fetch 404 ???
//* */