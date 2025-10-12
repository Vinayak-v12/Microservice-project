package com.notification.Notification_Service;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;

@Configuration
public class RedisConfig {

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName("redis-11140.crce214.us-east-1-3.ec2.redns.redis-cloud.com");
        config.setPort(11140);
        config.setPassword("vQhtUsSw9ZQ9TsEb8BO2SvOVXBiJeK1H");

        
        LettuceClientConfiguration clientConfig = LettuceClientConfiguration.builder()
                .useSsl()           
                .build();

        return new LettuceConnectionFactory(config,clientConfig);
       

    }

    @Bean
    public StringRedisTemplate redisTemplate() {
        return new StringRedisTemplate(redisConnectionFactory());
    }
}
