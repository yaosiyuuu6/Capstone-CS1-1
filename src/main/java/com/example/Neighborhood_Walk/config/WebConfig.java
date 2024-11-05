package com.example.Neighborhood_Walk.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow all origins to access the API to solve CORS issues
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:8080", "http://192.168.1.101:8080/", "http://44.200.151.81", "https://www.communea.net")

                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }
}

