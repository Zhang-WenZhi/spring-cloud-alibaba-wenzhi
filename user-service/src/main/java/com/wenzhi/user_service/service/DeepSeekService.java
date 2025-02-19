package com.wenzhi.user_service.service;

import com.wenzhi.user_service.config.DeepSeekConfig;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeepSeekService {

    @Autowired
    private DeepSeekConfig deepSeekConfig;

    public String sendRequest(String jsonInput) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(deepSeekConfig.getUrl());
            httpPost.setHeader("Authorization", "Bearer " + deepSeekConfig.getKey());
            httpPost.setHeader("Content-Type", "application/json");

            StringEntity entity = new StringEntity(jsonInput);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                HttpEntity responseEntity = response.getEntity();
                return EntityUtils.toString(responseEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
