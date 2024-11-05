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

        // Use StringRedisSerializer to serialize the key as a String.
        template.setKeySerializer(new StringRedisSerializer());

        // Use GenericJackson2JsonRedisSerializer to serialize the value.
        // This serializer can handle various data types and convert them to JSON.
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        // Use StringRedisSerializer to serialize the hash key as a String.
        template.setHashKeySerializer(new StringRedisSerializer());

        // Use GenericJackson2JsonRedisSerializer to serialize the hash value.
        // This ensures that complex objects inside hashes are serialized as JSON.
        template.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);

        // Use StringRedisSerializer to serialize the key as a String.
        template.setKeySerializer(new StringRedisSerializer());

        // Use StringRedisSerializer to serialize the value as a String.
        // This is useful when storing simple key-value pairs of strings.
        template.setValueSerializer(new StringRedisSerializer());

        return template;
    }




}

