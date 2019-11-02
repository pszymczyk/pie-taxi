package com.pszymczyk.pietaxi.rides;

import java.time.Clock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class RidesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RidesApplication.class, args);
	}

	@Bean
	Clock clock() {
		return Clock.systemDefaultZone();
	}
}
