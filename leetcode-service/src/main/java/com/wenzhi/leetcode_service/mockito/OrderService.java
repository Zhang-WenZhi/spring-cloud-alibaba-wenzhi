package com.wenzhi.leetcode_service.mockito;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Resource // 按名称注入（也可用 @Autowired）
    private PaymentGateway paymentGateway;

    public boolean processOrder(Order order) {
        if (order.isValid()) {
            paymentGateway.charge(order.getAmount());
            return true;
        }
        return false;
    }
}


