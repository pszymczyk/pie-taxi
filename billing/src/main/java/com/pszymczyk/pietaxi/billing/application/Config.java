package com.pszymczyk.pietaxi.billing.application;

import java.time.Clock;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.pszymczyk.pietax.infrastructure.Serde;

@Configuration
class Config {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }

    @Bean
    Serde serde() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return new Serde(objectMapper);
    }

}
