package com.example.Neighborhood_Walk.config;


import ClickSend.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

@Configuration
public class ClickSendConfig {

    // 从 application.properties 文件中获取 API 凭证
    @Value("${clickSend-username}")
    private String clickSendUsername;

    @Value("${clickSend-apiKey}")
    private String clickSendApiKey;

    // 创建一个单例的 ApiClient Bean
    @Bean(name = "customClickSendConfig")
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public ApiClient clickSendConfig() {
        ApiClient clickSendApiClient = new ApiClient();
        clickSendApiClient.setUsername(clickSendUsername);
        clickSendApiClient.setPassword(clickSendApiKey);
        return clickSendApiClient;
    }

}

