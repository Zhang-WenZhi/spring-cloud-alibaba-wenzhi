## Spring Boot 集成 JSP，并启动

```shell
java -jar target/jsp-service-0.0.1-SNAPSHOT.war
```

## macOS 手动安装 Maven

```shell
# 下载 Maven（以 3.9.6 版本为例）
curl -O https://dlcdn.apache.org/maven/maven-3/3.9.6/binaries/apache-maven-3.9.6-bin.tar.gz

# 解压到 /usr/local
sudo tar -xzf apache-maven-3.9.6-bin.tar.gz -C /usr/local

# 重命名目录（方便后续配置）
cd /usr/local
sudo mv apache-maven-3.9.6 apache-maven

# 如果使用 Zsh（默认 macOS Catalina 及以上）
nano ~/.zshrc

# 通过 Homebrew 安装无需配置
export PATH="/usr/local/opt/maven/bin:$PATH"

# 手动安装需添加以下内容
export MAVEN_HOME=/usr/local/apache-maven
export PATH="$MAVEN_HOME/bin:$PATH"

# Zsh
source ~/.zshrc

mvn -v

sudo chmod -R 755 /usr/local/apache-maven

# 删除旧版本
sudo rm -rf /usr/local/apache-maven*

```

## 简单.jsp文件

```html
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP Demo with JSTL</title>
</head>
<body>
    <h1>JSP Demo using JSTL</h1>
    <h2>Fruits List</h2>
    <ul>
        <c:forEach items="${fruits}" var="fruit">
            <li>${fruit}</li>
        </c:forEach>
    </ul>
</body>
</html>
```


## Spring Boot 3.3.3 + JSP 启动失败,原因查找

```shell
# 检查 WAR 文件结构
jar tf target/jsp-service-0.0.1-SNAPSHOT.war
# WEB-INF/jsp/beauty-login.jsp
#WEB-INF/lib/tomcat-embed-jasper-*.jar

# 检查 WAR 包中的清单文件
# 解压 WAR 文件
jar xf target/jsp-service-0.0.1-SNAPSHOT.war META-INF/MANIFEST.MF
# 查看清单内容
cat META-INF/MANIFEST.MF
# Main-Class: org.springframework.boot.loader.WarLauncher
#Start-Class: com.example.jspdemo.JspDemoApplication
```

```shell
zhangwenzhi@zhangwenzhideMacBook-Pro jsp-service % jar tf target/jsp-service-0.0.1-SNAPSHOT.war
META-INF/
META-INF/MANIFEST.MF
WEB-INF/
WEB-INF/classes/
WEB-INF/classes/org/
WEB-INF/classes/org/wenzhi/
WEB-INF/classes/org/wenzhi/jsp_service/
WEB-INF/classes/org/wenzhi/jsp_service/controller/
WEB-INF/classes/WEB-INF/
WEB-INF/classes/WEB-INF/jsp/
WEB-INF/lib/
WEB-INF/jsp/
WEB-INF/classes/org/wenzhi/jsp_service/controller/JspDemoController.class
WEB-INF/classes/org/wenzhi/jsp_service/JspServiceApplication.class
WEB-INF/classes/WEB-INF/jsp/beauty-login.jsp
WEB-INF/classes/application.yml
WEB-INF/lib/spring-aop-6.1.12.jar
WEB-INF/lib/jul-to-slf4j-2.0.16.jar
WEB-INF/lib/tomcat-annotations-api-10.1.28.jar
WEB-INF/lib/spring-jcl-6.1.12.jar
WEB-INF/lib/spring-boot-starter-3.3.3.jar
WEB-INF/lib/spring-beans-6.1.12.jar
WEB-INF/lib/tomcat-embed-core-10.1.28.jar
WEB-INF/lib/logback-classic-1.5.7.jar
WEB-INF/lib/snakeyaml-2.2.jar
WEB-INF/lib/spring-boot-starter-tomcat-3.3.3.jar
WEB-INF/lib/jakarta.servlet.jsp.jstl-api-3.0.1.jar
WEB-INF/lib/tomcat-embed-jasper-10.1.28.jar
WEB-INF/lib/jakarta.el-api-5.0.0.jar
WEB-INF/lib/jakarta.servlet.jsp.jstl-3.0.1.jar
WEB-INF/lib/micrometer-observation-1.13.3.jar
WEB-INF/lib/spring-boot-starter-web-3.3.3.jar
WEB-INF/lib/jackson-core-2.17.2.jar
WEB-INF/lib/spring-context-6.1.12.jar
WEB-INF/lib/spring-web-6.1.12.jar
WEB-INF/lib/tomcat-embed-websocket-10.1.28.jar
WEB-INF/lib/spring-core-6.1.12.jar
WEB-INF/lib/micrometer-commons-1.13.3.jar
WEB-INF/lib/spring-boot-starter-logging-3.3.3.jar
WEB-INF/lib/spring-boot-starter-json-3.3.3.jar
WEB-INF/lib/ecj-3.33.0.jar
WEB-INF/lib/jackson-databind-2.17.2.jar
WEB-INF/lib/jackson-annotations-2.17.2.jar
WEB-INF/lib/spring-expression-6.1.12.jar
WEB-INF/lib/slf4j-api-2.0.16.jar
WEB-INF/lib/jackson-datatype-jsr310-2.17.2.jar
WEB-INF/lib/tomcat-embed-el-10.1.28.jar
WEB-INF/lib/spring-webmvc-6.1.12.jar
WEB-INF/lib/log4j-api-2.23.1.jar
WEB-INF/lib/log4j-to-slf4j-2.23.1.jar
WEB-INF/lib/jackson-datatype-jdk8-2.17.2.jar
WEB-INF/lib/jakarta.annotation-api-2.1.1.jar
WEB-INF/lib/spring-boot-autoconfigure-3.3.3.jar
WEB-INF/lib/jackson-module-parameter-names-2.17.2.jar
WEB-INF/lib/spring-boot-3.3.3.jar
WEB-INF/lib/logback-core-1.5.7.jar
WEB-INF/lib/jakarta.servlet-api-6.0.0.jar
WEB-INF/jsp/beauty-login.jsp
META-INF/maven/org.wenzhi/jsp-service/pom.xml
META-INF/maven/org.wenzhi/jsp-service/pom.properties
zhangwenzhi@zhangwenzhideMacBook-Pro jsp-service % java -jar target/jsp-service.war
Error: Unable to access jarfile target/jsp-service.war
zhangwenzhi@zhangwenzhideMacBook-Pro jsp-service % java -jar target/jsp-service-0.0.1-SNAPSHOT.war
target/jsp-service-0.0.1-SNAPSHOT.war中没有主清单属性
zhangwenzhi@zhangwenzhideMacBook-Pro jsp-service % jar xf target/jsp-service-0.0.1-SNAPSHOT.war META-INF/MANIFEST.MF
zhangwenzhi@zhangwenzhideMacBook-Pro jsp-service % cat META-INF/MANIFEST.MF
Manifest-Version: 1.0
Created-By: Maven WAR Plugin 3.4.0
Build-Jdk-Spec: 17
Main-Class: org.springframework.boot.loader.launch.WarLauncher
Start-Class: com.wenzhi.jsp_service.JspServiceApplication
Spring-Boot-Version: 3.4.3
Spring-Boot-Classes: WEB-INF/classes/
Spring-Boot-Lib: WEB-INF/lib/
Spring-Boot-Classpath-Index: WEB-INF/classpath.idx
Spring-Boot-Layers-Index: WEB-INF/layers.idx

zhangwenzhi@zhangwenzhideMacBook-Pro jsp-service % java -jar target/jsp-service-0.0.1-SNAPSHOT.war

```