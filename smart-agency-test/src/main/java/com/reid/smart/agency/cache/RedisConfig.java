//package com.reid.smart.agency.cache;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.serializer.RedisSerializer;
//
///**
// * <p>
// * Redis 配置
// * </p>
// *
// * @author MC_Yang
// * @version V1.0
// **/
//@Configuration
//public class RedisConfig {
//
//    @Bean
//    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
//        RedisTemplate<String, Object> template = new RedisTemplate<>();
//        template.setConnectionFactory(redisConnectionFactory);
//
//        // key 采用 String 的序列化方式
//        template.setKeySerializer(RedisSerializer.string());
//        // hash 的 key 也采用 String 的序列化方式
//        template.setHashKeySerializer(RedisSerializer.string());
//        // value 序列化方式采用 jackson
//        template.setValueSerializer(RedisSerializer.json());
//        // hash 的 value 序列化方式采用 jackson
//        template.setHashValueSerializer(RedisSerializer.json());
//
//        template.afterPropertiesSet();
//        return template;
//    }
//}
