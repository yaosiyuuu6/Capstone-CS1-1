package com.example.Neighborhood_Walk.config;

import com.example.Neighborhood_Walk.controller.LocationWebSocketHandler;
import com.example.Neighborhood_Walk.controller.ParentLocationWebSocketHandler;
import com.example.Neighborhood_Walk.service.ShareLocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> stringRedisTemplate;
    private final ShareLocationService shareLocationService;

    // 构造函数注入
    public WebSocketConfig(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate,
                           @Qualifier("stringRedisTemplate") RedisTemplate<String, String> stringRedisTemplate,
                           ShareLocationService shareLocationService) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.shareLocationService = shareLocationService;
    }
// 注入 RedisTemplate

//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        // Walker 端 WebSocket
//        registry.addHandler(new LocationWebSocketHandler(redisTemplate), "/ws/walker")
//                .setAllowedOrigins("*");
//
//        // Parent 端 WebSocket
//        registry.addHandler(new ParentLocationWebSocketHandler(redisTemplate), "/ws/parent")
//                .setAllowedOrigins("*");
//    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // Walker 端 WebSocket
        registry.addHandler(new LocationWebSocketHandler(redisTemplate), "/ws/walker")
                .setAllowedOrigins("*");

        // Parent 端 WebSocket
        registry.addHandler(new ParentLocationWebSocketHandler(shareLocationService, stringRedisTemplate), "/ws/parent")
                .setAllowedOrigins("*");
    }
}
