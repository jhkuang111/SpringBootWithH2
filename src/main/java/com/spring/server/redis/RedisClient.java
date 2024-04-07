package com.spring.server.redis;

import com.spring.server.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

@Configuration
public class RedisClient {

    Logger logger = LoggerFactory.getLogger(RedisClient.class);

    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisFactory) {
        logger.info("Initiating Redis Template");
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(redisFactory);
        // Add some specific configuration here. Key serializers, etc.
        return template;
    }
}
