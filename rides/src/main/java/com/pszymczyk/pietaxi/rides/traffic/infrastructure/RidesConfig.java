package com.pszymczyk.pietaxi.rides.traffic.infrastructure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableScheduling
class RidesConfig {

    @Bean
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
