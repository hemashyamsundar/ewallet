package com.example.wallet;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.java.CommonConstants;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Properties;

@Configuration
public class UserConfig {

    @Bean
    PasswordEncoder getPE(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    ObjectMapper getMapper(){
        return new ObjectMapper();
    }

    @Bean
    Properties getProperties(){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, CommonConstants.KAFKA_PORT);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class);
        return properties;
    }

    @Bean
    ProducerFactory getProducerFactory(){
        return new DefaultKafkaProducerFactory(getProperties());
    }

    @Bean
    KafkaTemplate<String,String> getKafkaTemplate(){

        return new KafkaTemplate<>(getProducerFactory());
    }
}
