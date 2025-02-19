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
public class OrderServiceTest {

//    @Autowired // 注入真实的被测对象
//    private OrderService orderService;
//
//    @MockBean // 替换 Spring 上下文中的 PaymentGateway 为 Mock
//    private PaymentGateway mockGateway;

    @Mock // 创建 Mock 对象
    private PaymentGateway mockGateway;

    @InjectMocks // 将 Mock 对象注入到被测类中
    private OrderService orderService;

    public OrderServiceTest() {
        // 初始化 Mockito 注解
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testProcessOrder_ValidOrder() {
        // 1. 创建有效订单
        Order validOrder = new Order(100.0, true);

        // 2. 调用被测方法
        boolean result = orderService.processOrder(validOrder);

        // 3. 断言结果
        assertTrue(result);

        // 4. 验证依赖的交互行为
        verify(mockGateway).charge(100.0);
    }

    @Test
    public void testProcessOrder_InvalidOrder() {
        // 1. 创建无效订单
        Order invalidOrder = new Order(100.0, false);

        // 2. 调用被测方法
        boolean result = orderService.processOrder(invalidOrder);

        // 3. 断言结果
        assertFalse(result);

        // 4. 验证依赖未被调用
        verify(mockGateway, never()).charge(anyDouble());
    }
}