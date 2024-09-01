package com.wenzhi.order_service.feign_client;

import com.wenzhi.common_module.entity.OrderEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// 调用积分服务的FeignClient
@FeignClient(value = "point-service")
public interface PointServiceFeignClient {

    @PostMapping(value = "/point/add")
    String addPoint(@RequestBody OrderEntity order);

    @PostMapping(value = "/point/add2")
    String addPoint2(@RequestParam("productionName") String productionName);
}