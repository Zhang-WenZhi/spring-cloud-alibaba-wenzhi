package com.wenzhi.leetcode_service.service;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import redis.clients.jedis.*;
import redis.clients.jedis.csc.Cache;

import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JedisService {
    /**
     * 测试jedis连接
     * */
    public void jedis1() {
        // 连接到本地 Redis（默认端口6379）
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            // 认证（如果Redis有密码）
            // jedis.auth("your-password");

            // 测试连接
            System.out.println("Redis Ping: " + jedis.ping());

            // 写入数据
            jedis.set("test_key", "Hello Jedis");
            System.out.println("Value: " + jedis.get("test_key"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 附加配置
     * */
    // (1) 连接池配置
    public void jedis2() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(100);
        poolConfig.setMaxIdle(20);

        try (JedisPool jedisPool = new JedisPool(poolConfig, "localhost", 6379);
             Jedis jedis = jedisPool.getResource()) {
            jedis.set("pool_key", "Jedis Pool Works");
            System.out.println(jedis.get("pool_key"));
        }
    }

    // (2) 集群模式
    public void jedis3() {
        HostAndPort node = new HostAndPort("127.0.0.1", 7000);
        try (JedisCluster jedisCluster = new JedisCluster(Collections.singleton(node))) {
            jedisCluster.set("cluster_key", "Cluster Value");
            System.out.println(jedisCluster.get("cluster_key"));
        }
    }

    /**
     * 基础用法（单机模式）
     * */
    // (1) 直接创建连接
    public static void main(String[] args) {
        // 创建连接（默认端口6379）
        try (Jedis jedis = new Jedis("localhost", 6379)) {
            // 认证（如果Redis设置了密码）
            jedis.auth("your-password");

            // 字符串操作
            jedis.set("key1", "Hello Jedis");
            String value = jedis.get("key1");
            System.out.println(value); // 输出: Hello Jedis

            // 哈希操作
            jedis.hset("user:1001", "name", "Alice");
            String name = jedis.hget("user:1001", "name");
            System.out.println(name); // 输出: Alice

            // 列表操作
            jedis.lpush("messages", "msg1", "msg2");
            List<String> messages = jedis.lrange("messages", 0, -1);
            System.out.println(messages); // 输出: [msg2, msg1]
        } // try-with-resources 自动关闭连接
    }

    // (2) 连接池管理: 生产环境推荐使用连接池，避免频繁创建/销毁连接：
    public class JedisPoolDemo {
        private static final JedisPool jedisPool;

        static {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(100);      // 最大连接数
            config.setMaxIdle(20);         // 最大空闲连接
            config.setMinIdle(5);          // 最小空闲连接
            config.setTestOnBorrow(true);  // 取连接时验证可用性

            // 创建连接池（格式：redis://user:password@host:port/database）
            jedisPool = new JedisPool(config, "localhost", 6379, 5000, "your-password");
        }

        public static void executeExample() {
            try (Jedis jedis = jedisPool.getResource()) {
                jedis.set("pool_key", "Jedis Pool Works");
                System.out.println(jedis.get("pool_key"));
            }
        }

        public static void mainJedis4(String[] args) {
            executeExample();
            jedisPool.close(); // 应用关闭时销毁连接池
        }
    }


    /**
     * 3. 集群模式: 若使用 Redis Cluster，需用 JedisCluster 类：
     * */
    /*public class JedisClusterDemo {
        public static void mainJedis5(String[] args) {
            // 配置集群节点
            HostAndPort node = new HostAndPort("127.0.0.1", 7000);
            // 过时代码：旧版本 Jedis 使用 JedisClusterConfig 类，但在 Jedis 4.x/5.x 中已被废弃。
            //正确配置方式：直接通过 JedisClientConfig 或 JedisPoolConfig 配置参数。
            JedisClusterConfig config = new JedisClusterConfig();
            config.setMaxTotal(100);
            config.setMaxAttempts(3); // 操作失败重试次数

            try (JedisCluster jedisCluster = new JedisCluster(
                    Collections.singleton(node), 5000, 5000, 3, "password", config)) {

                // 集群操作（自动路由到正确节点）
                jedisCluster.set("cluster_key", "Cluster Value");
                System.out.println(jedisCluster.get("cluster_key"));
            }
        }
    }*/
    class JedisClusterDemo {
        public static void jedisClusterDemoMain(String[] args) {
            // 1. 配置连接池参数
            JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(100);   // 最大连接数
            poolConfig.setMaxIdle(20);     // 最大空闲连接

            // 2. 构建集群节点列表
            HostAndPort clusterNode = new HostAndPort("127.0.0.1", 7000);

            // 3. 创建 JedisCluster 实例
            try (JedisCluster jedisCluster = new JedisCluster(
                    (Set<HostAndPort>) Collections.singleton(clusterNode),  // 集群节点列表
                    (JedisClientConfig) DefaultJedisClientConfig.builder()
                            .connectionTimeoutMillis(5000)   // 连接超时（毫秒）
                            .socketTimeoutMillis(5000)       // 读写超时（毫秒）
                            .password("your-password")       // Redis 密码
                            .build(),
                    (Cache) poolConfig  // 连接池配置
            )) {
                // 执行操作
                jedisCluster.set("cluster_key", "Cluster Value");
                System.out.println(jedisCluster.get("cluster_key"));
            }
        }
    }

    /**
     * 4. 高级功能
     * */
    // (1) 事务（Transaction）
    /*public void jedis6() {
        // jedisPool ??
        try (Jedis jedis = jedisPool.getResource()) {
            Transaction tx = jedis.multi(); // 开启事务
            tx.set("tx_key1", "value1");
            tx.incr("tx_counter");
            tx.exec(); // 提交事务
        }
    }*/


    // (2) Pipeline（批量操作）
    /*public void jedis7() {
        try (Jedis jedis = jedisPool.getResource()) {
            Pipeline pipeline = jedis.pipelined();
            for (int i = 0; i < 1000; i++) {
                pipeline.set("pipeline_key_" + i, "value_" + i);
            }
            pipeline.sync(); // 批量提交
        }
    }*/

    // (3) Lua脚本
    /*public void jedis8() {
        String script = "return redis.call('get', KEYS[1])";
        try (Jedis jedis = jedisPool.getResource()) {
            Object result = jedis.eval(script, Collections.singletonList("key1"), Collections.emptyList());
            System.out.println(result);
        }
    }*/


    // Jedis 和 Redisson 可以在同一个项目中一起使用
    public class HybridDemo {
        private final JedisPool jedisPool;
        private final RedissonClient redissonClient;

        public HybridDemo(JedisPool jedisPool, RedissonClient redissonClient) {
            this.jedisPool = jedisPool;
            this.redissonClient = redissonClient;
        }

        public void processWithLock(String key) {
            // 使用 Redisson 加锁
            RLock lock = redissonClient.getLock(key + "_lock");
            try (Jedis jedis = jedisPool.getResource()) {
                lock.lock();
                // 使用 Jedis 执行简单命令
                jedis.incr(key);
            } finally {
                lock.unlock();
            }
        }
    }


}




