package com.wenzhi.point_service.controller;

import com.wenzhi.common_module.entity.OrderEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/point")
public class PointController {
    @GetMapping(value = "/test")
    public String test (){
        return "this is point-service";
    }

    // 用公共库Entity

    // 传递对象参数
    @PostMapping(value = "/add")
    public String addPoint(@RequestBody OrderEntity order){
        return "add point success!商品名称333："+order.getProductionName();
    }

    // 传递字符串参数
    @PostMapping(value = "/add2")
    public String addPoint2(@RequestParam("productionName") String productionName){
        return "add point success!商品名称："+productionName;
    }
}
