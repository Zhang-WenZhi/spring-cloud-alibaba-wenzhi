package com.wenzhi.leetcode_service.mockito;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest
public class MyServiceTest {

//    @Autowired // 注入真实的被测对象
//    private MyService myService;
//
//    @MockBean // 替换 Spring 上下文中的 MyDependency 为 Mock
//    private MyDependency mockDependency;

    @Mock // 创建 Mock 对象
    private MyDependency mockDependency;

    @InjectMocks // 将 Mock 对象注入到被测类中
    private MyService myService;

    public MyServiceTest() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void testDoSomething() {
        // 1. 定义 Mock 行为（可选）
        // when(mockDependency.prepare()).thenAnswer(...);

        // 2. 调用被测方法
        String result = myService.doSomething();

        // 3. 断言结果
        assertEquals("real result", result);

        // 4. 验证依赖的交互行为
        verify(mockDependency).prepare();
    }
}