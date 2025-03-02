package com.wenzhi.leetcode_service.mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MyService {
    private final MyDependency dependency;

    @Autowired // 通过构造器注入
    public MyService(MyDependency dependency) {
        this.dependency = dependency;
    }

    public String doSomething() {
        dependency.prepare(); // 依赖调用
        return "real result";
    }
}
