package com.wenzhi.order_service.controller;

import com.wenzhi.common_module.entity.OrderEntity;
import com.wenzhi.order_service.feign_client.PointServiceFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/order")
@RefreshScope
public class OrderController {
    // 通过注解@Value注入配置信息 nacos
    @Value("${config.info}")
    private String configInfo;

    @Autowired
    private PointServiceFeignClient pointServiceFeignClient;

    @GetMapping(value = "/test")
    public String test (){
        return "this is order-service";
    }

    @GetMapping(value = "/test/getConfigInfo")
    public String getConfigInfo (){
        return configInfo;
    }

    // 通过OpenFeign调用point-service的接口
    @PostMapping(value = "/add")
    public String addOrder(){
        OrderEntity order = new OrderEntity();
        order.setId("123");
        order.setProductionName("水杯");
        String res = pointServiceFeignClient.addPoint(order);
        return res;
    }

    @PostMapping(value = "/add2")
    public String addOrder2(){
        String res = pointServiceFeignClient.addPoint2("水杯2");
        return res;
    }

}
