package com.wenzhi.leetcode_service.controller;

import com.wenzhi.leetcode_service.entity.dto.Ip2RegionDto;
import com.wenzhi.leetcode_service.entity.message.Request;
import com.wenzhi.leetcode_service.entity.message.Response;
import com.wenzhi.leetcode_service.service.ip2region.Ip;
import com.wenzhi.leetcode_service.util.AddressUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/ip2region")
public class Ip2RegionController {
    final Logger logger = LoggerFactory.getLogger(getClass());

    private static final Pattern IP_PATTERN = Pattern.compile("^((25[0-5]|2[0-4]\\d|[01]?\\d\\d?)\\.){3}(25[0-5]|2[0-4]\\d|[01]?\\d\\d?)$");


    /*
    * 过滤 IPv6：ifconfig | grep -v inet6  # 仅显示 IPv4 地址
    * 4. 使用 ip 命令（推荐）：ip addr show dev en0  # 显示 en0 接口的详细信息
    * 3. 获取特定接口的 IP 地址：ipconfig getifaddr en0  # en0 是 Wi-Fi 接口，en1 通常是以太网接口
    * 2. 仅显示活动接口的 IPv4 地址：ifconfig | grep "inet " | grep -v "127.0.0.1"
     * */
    @PostMapping("/search")
    @Ip
    public Response<String> search(@RequestBody @Valid Request<Ip2RegionDto> request) {
        String ip = request.getBody().getIp();
        logger.info("search ip: " + ip);
        if (ip == null || ip.trim().isEmpty()) {
            return Response.error(400, "IP 地址不能为空");
        }
        if (!IP_PATTERN.matcher(ip).matches()) {
            return Response.error(400, "输入的 IP 地址格式不正确");
        }
        String region = AddressUtil.getCityInfo(ip);
        if ("无法解析地址信息".equals(region)) {
            return Response.error(500, region);
        }
        return Response.success(region);
    }

    @PostMapping("/comment")
    public Response<String> comment(HttpServletRequest request) {
        String clientIp = getClientIp(request);
        if (clientIp != null && IP_PATTERN.matcher(clientIp).matches()) {
            return Response.success(clientIp);
        }
        return Response.error(500, "无法获取有效的 IP 地址");
    }

    private String getClientIp(HttpServletRequest request) {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_CLIENT_IP",
                "HTTP_CLIENT_IP"
        };
        for (String header : headers) {
            String ip = request.getHeader(header);
            if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
                int index = ip.indexOf(',');
                if (index != -1) {
                    ip = ip.substring(0, index);
                }
                if (IP_PATTERN.matcher(ip).matches()) {
                    return ip;
                }
            }
        }
        String remoteAddr = request.getRemoteAddr();
        if (IP_PATTERN.matcher(remoteAddr).matches()) {
            return remoteAddr;
        }
        return null;
    }
}


/*
 * 若接口部署在代理服务器（如 Nginx）后，需在代理配置中添加：
 * proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
 * 此配置可保留原始 IP 并追加代理 IP，确保接口能正确解析
 * */