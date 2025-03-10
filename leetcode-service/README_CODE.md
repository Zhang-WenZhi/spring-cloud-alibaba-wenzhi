## 模板试图配置【不管用】 备份

```java

package com.wenzhi.leetcode_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.util.Properties;

@Configuration
public class ViewResolverConfig {

    // JSP 视图解析器
    @Bean
    public ViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        resolver.setOrder(1);
        // resolver.setViewNames(new String[]{"*"}); // 处理所有无扩展名请求
        resolver.setViewNames(new String[]{"*.jsp"}); // 处理所有无扩展名请求
        return resolver;
    }

    // Thymeleaf 模板解析器
    @Bean
    public ClassLoaderTemplateResolver thymeleafTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/thymeleaf/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML");
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(2); // 优先级低于 JSP
        return templateResolver;
    }

    // Thymeleaf 模板引擎
    @Bean
    public SpringTemplateEngine thymeleafTemplateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(thymeleafTemplateResolver());
        return templateEngine;
    }

    // Thymeleaf 视图解析器
    @Bean
    public ThymeleafViewResolver thymeleafViewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(thymeleafTemplateEngine());
        viewResolver.setCharacterEncoding("UTF-8");
        viewResolver.setOrder(2); // 优先级低于 JSP
        viewResolver.setViewNames(new String[]{"*.html"}); // 仅处理 .html 文件
        return viewResolver;
    }

    // FreeMarker 配置器
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setTemplateLoaderPath("classpath:templates/freemarker/");

        // 设置 FreeMarker 的配置属性，包括字符编码
        Properties settings = new Properties();
        settings.setProperty("default_encoding", "UTF-8");
        settings.setProperty("output_encoding", "UTF-8");
        configurer.setFreemarkerSettings(settings);

        return configurer;
    }

    // FreeMarker 视图解析器
    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setOrder(3); // 优先级低于 JSP 和 Thymeleaf
        resolver.setViewNames(new String[]{"*.ftl"}); // 仅处理 .ftl 文件
        return resolver;
    }
}
```


## testExportRiskByIdUrl【失败】 代码备份

```shell
mvn clean install -DskipTests=true
mvn clean package -Dmaven.test.skip=true
# VM options设置为GBK编码
java -Dfile.encoding=GBK -jar target/leetcode-service-0.0.1-SNAPSHOT.jar
```

```shell
package com.wenzhi.leetcode_service.controller;
import com.wenzhi.leetcode_service.config.SecurityConfig;
import com.wenzhi.leetcode_service.entity.RiskEntity;
import com.wenzhi.leetcode_service.entity.dto.RiskByIdDto;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.service.PoiExcelExportService;
import com.wenzhi.leetcode_service.service.RiskService;


import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.util.Collections;

import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.entity.message.Response;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(PoiExcelExportController.class)
@ContextConfiguration(classes = {SecurityConfig.class}) // 引入 SecurityConfig 配置类
@Slf4j
public class PoiExcelExportControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RiskService riskService;

    @MockBean
    private PoiExcelExportService excelExportService;

    @Test
    public void testExportRiskById() throws IOException {
        // 创建 Mock 对象
        RiskService mockRiskService = Mockito.mock(RiskService.class);
        PoiExcelExportService mockExcelExportService = Mockito.mock(PoiExcelExportService.class);
        PoiExcelExportController riskController = new PoiExcelExportController(mockRiskService, mockExcelExportService);

        // 准备测试数据
        RiskByIdDto dto = new RiskByIdDto();
        dto.setId(1L);
        Request<RiskByIdDto> request = new Request<>();
        request.setBody(dto);

        RiskEntity mockRisk = new RiskEntity(1L, "Test Risk", "Test Description");
        when(mockRiskService.getRiskById(1L)).thenReturn(mockRisk);

        HttpServletResponse mockResponse = new MockHttpServletResponse();

        // 调用控制器方法
        Response<String> result = riskController.exportRiskById(request, mockResponse);

        // 验证响应结果
        assertEquals(HttpStatus.OK.value(), result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals("导出成功", result.getData());

        // 验证服务方法是否被调用
        verify(mockRiskService, times(1)).getRiskById(1L);
        verify(mockExcelExportService, times(1)).exportRiskToExcel(Collections.singletonList(mockRisk), mockResponse);
    }

    @Test
    public void testExportRiskByIdUrl() throws Exception {
        // 模拟服务返回数据
        RiskEntity mockRisk = new RiskEntity(1L, "Test Risk", "Description");
        when(riskService.getRiskById(1L)).thenReturn(mockRisk);

        // 构造符合新格式的请求体
        String requestBody = "{\"header\": {\"clientType\": \"APP\", \"token\": \"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9\", \"timestamp\": 1640995600, \"version\": \"v1.0\"}, \"body\": {\"id\": 1}}";

        // 执行请求
        MvcResult result = mockMvc.perform(post("/excelExport/exportRiskById")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andReturn();

        // 验证服务方法是否被调用
        verify(riskService, Mockito.times(1)).getRiskById(1L);
        verify(excelExportService, Mockito.times(1)).exportRiskToExcel(Collections.singletonList(mockRisk), Mockito.any(HttpServletResponse.class));

        // 解析响应内容
        String responseContent = result.getResponse().getContentAsString();
        log.info("响应内容: " + responseContent);

        // 可以进一步验证响应的内容
        // 这里假设 Response 类有对应的 JSON 序列化和反序列化方法
        // 实际使用时需要根据具体情况处理
        // Response<String> response = objectMapper.readValue(responseContent, Response.class);
        // assertEquals("导出成功", response.getData());
    }
}

```

## SecurityConfig 2025-03-105 备份


```java
package com.wenzhi.leetcode_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * UserServiceImpl中使用了BCryptPasswordEncoder加密密码，这里配置BCryptPasswordEncoder
     * */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 将Spring Boot版本回退至2.7.x或更早版本（如2.7.10），此时WebSecurityConfigurerAdapter仍有效
    // extends WebSecurityConfigurerAdapter @EnableWebSecurity
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable() // 关闭 CSRF 保护（生产环境需谨慎）
//                .authorizeRequests()
//                .anyRequest().permitAll() // 允许所有请求无需认证
//                .and()
//                .logout().permitAll(); // 允许注销
//    }


    // 在Spring Boot 3.x版本中，WebSecurityConfigurerAdapter类已被官方弃用。
    // 若你使用的是spring-boot-starter-security 3.3.3（对应Spring Security 6.x）
    /*
     * 不加这个，spring-boot-starter-security生成了一个密码，用户名user，都是登陆报错的
     * **/
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((auth) -> auth
                        .anyRequest().permitAll() // 允许所有请求无需认证
                // authenticated() 所有请求都需要认证
        );
        return http.build();
    }
}

```


## mybatis 配置记录

```yaml
mybatis:
  mapper-locations: classpath:mapper/*.xml
  type-aliases-package: com.wenzhi.leetcode_service.entity
  configuration:
    map-underscore-to-camel-case: true     # 开启驼峰命名转换
    default-fetch-size: 100
    default-statement-timeout: 30

```