package com.wenzhi.leetcode_service.controller;


import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/redisson")
@Slf4j
public class RedissonController {

    @Autowired
    private RedissonClient redissonClient;

    // 用于模拟共享资源
    private static final String SHARED_RESOURCE_KEY = "shared_counter";

    /**
     * 测试分布式锁接口
     * 示例：http://localhost:8080/redisson/lock
     */
    @GetMapping("/lock")
    public String testDistributedLock() {
        RLock lock = redissonClient.getLock("my_distributed_lock");
        try {
            // 尝试获取锁，最多等待3秒，锁自动释放时间10秒
            if (lock.tryLock(3, 10, TimeUnit.SECONDS)) {
                // 模拟操作共享资源（如修改Redis中的值）
                RMap<String, Integer> map = redissonClient.getMap(SHARED_RESOURCE_KEY);
                int current = map.getOrDefault("count", 0);
                map.put("count", current + 1);
                return "Lock acquired! Count updated to: " + (current + 1);
            } else {
                return "Failed to acquire lock";
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Lock interrupted";
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    /**
     * 测试数据存储（Map）
     * 示例：http://localhost:8080/redisson/map?key=name&value=Alice
     */
    @PostMapping("/map")
    public String testMap(@RequestParam String key, @RequestParam String value) {
        log.info("Setting key '{}' to value '{}'", key, value);
        RMap<String, String> map = redissonClient.getMap("test_map");
        map.put(key, value);
        return "Key '" + key + "' set to: " + value;
    }

    @GetMapping("/map")
    public String getMapValue(@RequestParam String key) {
        log.info("Getting value for key '{}'", key);
        RMap<String, String> map = redissonClient.getMap("test_map");
        return "Value of '" + key + "': " + map.get(key);
    }

    /**
     * 测试发布订阅
     * 发布消息：http://localhost:8080/redisson/publish?message=Hello
     * 订阅消息会自动打印到控制台
     */
    @PostMapping("/publish")
    public String publishMessage(@RequestParam String message) {
        RTopic topic = redissonClient.getTopic("test_topic");
        topic.publish(message);
        return "Message published: " + message;
    }

    // 订阅初始化（在应用启动时订阅）
    @Autowired
    public void initSubscription() {
        RTopic topic = redissonClient.getTopic("test_topic");
        topic.addListener(String.class, (channel, msg) -> {
            System.out.println("[Subscriber] Received message: " + msg);
        });
    }
}

