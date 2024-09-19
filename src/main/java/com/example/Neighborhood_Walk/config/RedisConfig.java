package com.example.Neighborhood_Walk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        // 使用 StringRedisSerializer 来序列化 key
        template.setKeySerializer(new StringRedisSerializer());

        // 使用 GenericJackson2JsonRedisSerializer 来序列化 value，能够处理多种数据类型
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // 使用 StringRedisSerializer 来序列化 Hash key 和 Hash value
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }
}

