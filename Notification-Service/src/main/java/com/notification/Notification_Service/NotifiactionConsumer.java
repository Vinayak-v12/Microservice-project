package com.notification.Notification_Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Executors;

import org.springframework.data.redis.connection.stream.Consumer;
import org.springframework.data.redis.connection.stream.MapRecord;
import org.springframework.data.redis.connection.stream.ReadOffset;
import org.springframework.data.redis.connection.stream.StreamOffset;
import org.springframework.data.redis.connection.stream.StreamReadOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.Task.TaskService.DTO.EmailDto;
import com.notification.Notification_Service.Service.EmailService;

import jakarta.annotation.PostConstruct;

@Service
public class NotifiactionConsumer {

    private final EmailService emailService;
    private final StringRedisTemplate redisTemplate;

    private static final String STREAM_KEY = "email-stream"; // Stream name
    private static final String CONSUMER_GROUP = "my-group";
    private static final String CONSUMER_NAME = "consumer1";
    private static final long MAX_STREAM_LENGTH=50;

    public NotifiactionConsumer(EmailService emailService, StringRedisTemplate redisTemplate) {
        this.emailService = emailService;
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    public void start() {
        // 1️ Create consumer group if not exists
        try {
            redisTemplate.opsForStream().createGroup(STREAM_KEY, CONSUMER_GROUP);
            System.out.println("✅ Consumer group created: " + CONSUMER_GROUP);
        } catch (Exception e) {
            System.out.println("ℹ️ Group might already exist: " + e.getMessage());
        }

        // 2️Start listening to the stream
        Executors.newSingleThreadExecutor().submit(() -> {
            while (true) {
                try {
                    List<MapRecord<String, Object, Object>> messages =
                        redisTemplate.opsForStream().read(
                            Consumer.from(CONSUMER_GROUP, CONSUMER_NAME),
                            StreamReadOptions.empty().count(10).block(Duration.ofSeconds(5)),
                            StreamOffset.create(STREAM_KEY, ReadOffset.lastConsumed())
                        );

                    if (messages != null && !messages.isEmpty()) {
                        for (MapRecord<String, Object, Object> msg : messages) {
                            EmailDto email = new EmailDto();
                            email.setUserEmail((String) msg.getValue().get("userEmail"));
                            email.setMessage((String) msg.getValue().get("message"));
                            email.setDeadline(LocalDateTime.parse((String) msg.getValue().get("deadline")));

                            emailService.send(email);
                            System.out.println("📧 Email sent to: " + email.getUserEmail());

                            //  Acknowledge processed message
                            redisTemplate.opsForStream().acknowledge(CONSUMER_GROUP, msg);
                            


                            //  Trim stream to MAX_STREAM_LENGTH
                            redisTemplate.opsForStream().trim(STREAM_KEY, MAX_STREAM_LENGTH);
                        }
                    }

                    Thread.sleep(2000); // small pause
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
