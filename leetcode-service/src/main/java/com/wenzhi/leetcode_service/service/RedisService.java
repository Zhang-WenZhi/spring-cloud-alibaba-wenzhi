package com.wenzhi.leetcode_service.service;

import com.wenzhi.leetcode_service.entity.RedisUser;
import org.redisson.api.RLock;
import org.redisson.api.RMap;
import org.redisson.api.RTopic;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
//import org.redisson.codec.JsonJacksonCodec;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedissonClient redissonClient;

    // 使用示例：获取分布式锁
    public void doWithLock() {
        RLock lock = redissonClient.getLock("myLock");
        try {
            lock.lock();  // 加锁
            // 业务逻辑...
        } finally {
            lock.unlock();  // 确保释放锁
        }
    }

    // 分布式锁
    public void safeUpdateData(String key) {
        RLock lock = redissonClient.getLock(key + "_lock");
        try {
            if (lock.tryLock(10, 30, TimeUnit.SECONDS)) { // 等待10秒，锁30秒自动释放
                // 执行需要同步的业务逻辑
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            if (lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }
    }

    // 分布式集合
    public void useDistributedMap() {
        RMap<String, Object> map = redissonClient.getMap("userCache");
        map.put("user1", new RedisUser("Alice", 30));  // 自动序列化存储
        RedisUser user = (RedisUser) map.get("user1");  // 反序列化读取
    }

    // 发布订阅
    // 1).发布消息
    public void publishMessage(String channel, String message) {
        RTopic topic = redissonClient.getTopic(channel);
        topic.publish(message);
    }

    // 2).订阅消息
    public void subscribe(String channel) {
        RTopic topic = redissonClient.getTopic(channel);
        topic.addListener(String.class, (charPattern, msg) -> {
            System.out.println("收到消息: " + msg);
        });
    }

}