package com.wenzhi.leetcode_service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/code") // 读取.class文件乱码
@Slf4j
public class CodeController {
    @RequestMapping(value = "/BooleanToString", method = RequestMethod.GET)
    public String getCode() {
        // 获取当前工作目录的路径
        Path currentPath = Paths.get("").toAbsolutePath();
        log.info("当前文件所在目录的绝对路径: {}", currentPath);

        try (DirectoryStream<Path> directoryStream = java.nio.file.Files.newDirectoryStream(currentPath)) {
            System.out.println("当前目录下的文件和目录:");
            for (Path path : directoryStream) {
                System.out.println(path.getFileName());
            }
        } catch (IOException e) {
            log.error("获取当前目录下的文件和目录失败:{}", e.getMessage());
            throw new RuntimeException("获取当前目录下的文件和目录失败:", e);
        }
        return "Hello World!";
    }

    /*
    * 使用 java.io 包
    * */
    @GetMapping("/readJavaFiles")
    public String readJavaFiles(@RequestParam(value = "codeClassName", required = false) String codeClassName) {
        log.info("java.io 包, 接收到的参数: {}", codeClassName);

        StringBuilder content = new StringBuilder();
        // 获取当前类所在的类加载路径
        String basePath = this.getClass().getResource("").getPath();
        log.info("java.io 包, 当前类所在的类加载路径: {}", basePath);

        basePath = basePath.replace("/controller", "/util");

        // 构建 util 目录的路径
        String utilPath = "/" + basePath + "/";

        log.info("拼接后的路径: {}", utilPath);
        File utilDir = new File(utilPath);

        if (utilDir.exists() && utilDir.isDirectory()) {
            File[] files = utilDir.listFiles();
            log.info("io 包, 目录下的文件: {}", Arrays.toString(files));
            if (files != null) {
                for (File file : files) {
                    log.info("io 包, 判断是否为文件: {}", file);
                    if (file.isFile() && file.getName().endsWith(codeClassName + ".class")) {
                        log.info("io 包, 处理的文件: {}", file);
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                        } catch (IOException e) {
                            log.error("使用 java.io 包，获取当前目录下的文件和目录失败:{}", e.getMessage());
                            throw new RuntimeException("获取当前目录下的文件和目录失败:", e);
                        }
                    }
                }
            }
        }
        return content.toString();
    }

    /*
    * 使用 java.nio.file 包
    * */
    @GetMapping("/readJavaFilesNio")
    public String readJavaFilesNio() {
        StringBuilder content = new StringBuilder();
        // 获取当前类所在的类加载路径
        String basePath = Objects.requireNonNull(this.getClass().getResource("")).getPath();

        basePath = basePath.replace("/controller", "/util");
        log.info("处理后的路径: {}", basePath);

        // 构建 util 目录的路径
        Path utilPath = Paths.get("/" + basePath, "/");
        log.info("Nio 包, 拼接后的路径: {}", utilPath);

        try (Stream<Path> paths = Files.walk(utilPath)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".class"))
                    .forEach(p -> {
                        log.info("Nio 包, 处理的文件: {}", p);
                        try {
                            String fileContent = Files.lines(p).collect(Collectors.joining("\n"));
                            content.append(fileContent).append("\n");
                        } catch (IOException e) {
                            log.error("使用 java.nio.file 包，获取路径失败:{}", e.getMessage());
                            throw new RuntimeException("获取路径失败:", e);
                        }
                    });
        } catch (IOException e) {
            log.error("使用 java.nio.file 包，遍历失败:{}", e.getMessage());
            throw new RuntimeException("获取路径失败:", e);
        }

        return content.toString();
    }
}
