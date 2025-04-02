package com.wenzhi.leetcode_service.util;

import org.lionsoul.ip2region.xdb.Searcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;

public class AddressUtil {
    final static Logger logger = LoggerFactory.getLogger(AddressUtil.class);
    private static final String DB_FILE_NAME = "ip2region/ip2region.xdb";
    private static Searcher searcher;

    static {
        try {
            // 使用类加载器获取文件输入流
            InputStream inputStream = AddressUtil.class.getClassLoader().getResourceAsStream(DB_FILE_NAME);
            if (inputStream == null) {
                logger.error("无法从类路径加载 ip2region.xdb 文件");
            }
            // 创建临时文件
            Path tempFilePath = Files.createTempFile("ip2region", ".xdb");
            // 将输入流复制到临时文件
            Files.copy(inputStream, tempFilePath, StandardCopyOption.REPLACE_EXISTING);
            // 使用临时文件创建 Searcher
            searcher = Searcher.newWithFileOnly(tempFilePath.toString());
        } catch (IOException e) {
            logger.error("初始化 Searcher 时出错", e);
        }
    }

    public static String getCityInfo(String ip) {
        if (searcher == null) {
            return "无法解析地址信息";
        }
        try {
            long sTime = System.nanoTime();
            String region = searcher.search(ip);
            long cost = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - sTime);
            logger.info("region: {}, ioCount: {}, took: {} μs", region, searcher.getIOCount(), cost);
            return region;
        } catch (Exception e) {
            logger.error("解析 IP 地址时出错", e);
            return "无法解析地址信息";
        }
    }

    public static void main(String[] args) {
        logger.info(getCityInfo("204.16.111.255"));
    }
}