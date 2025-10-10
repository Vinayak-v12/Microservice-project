package com.Task.TaskService.Service;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.Task.TaskService.DTO.EmailDto;

import jakarta.annotation.PostConstruct;

@Service
public class NotificationProducer {
	
    private final StringRedisTemplate redisTemplate;
    private static final String STREAM_KEY = "email-stream";

    public NotificationProducer(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    
    @PostConstruct
    public void testRedisConnection() {
        try {
            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
            System.out.println("Redis connected: " + pong);
        } catch (Exception e) {
            System.err.println("Redis connection failed: " + e.getMessage());
        }
    }


    public void send(EmailDto event) {
        // 1️⃣ Convert EmailDto to key-value map
        Map<String, String> message = new HashMap<>();
        message.put("userEmail", event.getUserEmail());
        message.put("message", event.getMessage());
        message.put("deadline", event.getDeadline().toString());

        // 2️⃣ Add message to Redis Stream
        redisTemplate.opsForStream().add(STREAM_KEY, message);

        System.out.println("Published event to Redis Stream: " + message);
    }
	

}
//private KafkaTemplate<String, EmailDto> kafkaTemplate;//class from dependency
//
//
//public NotificationProducer(KafkaTemplate<String, EmailDto> kafkaTemplate) {
//	this.kafkaTemplate=kafkaTemplate;
//}
//
//
//public void send(EmailDto event) {
//	
//	kafkaTemplate.send("Mytopic",event);
//}
