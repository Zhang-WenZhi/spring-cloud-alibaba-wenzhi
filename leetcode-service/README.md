## redis macOS

```shell
wget https://download.redis.io/redis-stable.tar.gz
tar -xzvf redis-stable.tar.gz
cd redis-stable
make
make BUILD_TLS=yes
sudo make install
redis-server

```

在 Spring Boot 中高效集成 Redisson，实现分布式锁、数据存储、发布订阅等高级功能。


## 查 Maven 依赖树

```shell
mvn dependency:tree | grep jedis
#[INFO] |  \- redis.clients:jedis:jar:5.1.0:compile
mvn dependency:tree | grep -E '(jedis|redisson|netty)'

# 检查 Redis 状态
systemctl status redis

# 开放防火墙端口（Linux）
sudo ufw allow 6379
```

## Spring Security 配置

```java
package com.wenzhi.leetcode_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
```

