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

    // Constructor injection to initialize dependencies
    public WebSocketConfig(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate,
                           @Qualifier("stringRedisTemplate") RedisTemplate<String, String> stringRedisTemplate,
                           ShareLocationService shareLocationService) {
        this.redisTemplate = redisTemplate;
        this.stringRedisTemplate = stringRedisTemplate;
        this.shareLocationService = shareLocationService;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // WebSocket handler for Walker (sender) side
        registry.addHandler(new LocationWebSocketHandler(redisTemplate), "/ws/walker")
                .setAllowedOrigins("*");  // Allow cross-origin requests from any domain

        // WebSocket handler for Parent (receiver) side
        registry.addHandler(new ParentLocationWebSocketHandler(shareLocationService, stringRedisTemplate), "/ws/parent")
                .setAllowedOrigins("*");  // Allow cross-origin requests from any domain
    }

}
