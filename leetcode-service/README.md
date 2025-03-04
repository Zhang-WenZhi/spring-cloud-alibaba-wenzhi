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


## MacOS Watt Toolkit

```shell
sudo chmod +a 'user:此处请修改为您当前的用户名:allow write' /etc/hosts
# 如果命令执行后，仍然提示 hosts 错误 请尝试执行下方命令
sudo chmod +a 'user:此处请修改为您当前的用户名:allow write' /etc/hosts
```


## 在 Spring Boot 中，可以通过配置RestTemplate来信任所有证书（不推荐在生产环境使用，仅用于测试和开发环境快速验证）

```java
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.ssl.SSLContexts;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

public class CustomRestTemplate {
    public static RestTemplate createRestTemplate() {
        try {
            SSLContext sslContext = SSLContexts.custom()
                   .loadTrustMaterial(null, (arg0, arg1) -> true)
                   .build();
            HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
            factory.setSSLContext(sslContext);
            factory.setHostnameVerifier(NoopHostnameVerifier.INSTANCE);
            return new RestTemplate(factory);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
```
