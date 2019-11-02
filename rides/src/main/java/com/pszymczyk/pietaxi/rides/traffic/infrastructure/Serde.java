package com.pszymczyk.pietaxi.rides.traffic.infrastructure;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
class Serde {

    private static final Logger log = LoggerFactory.getLogger(Serde.class);

    private final ObjectMapper objectMapper;

    public Serde(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    String serialize(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Error during serialization, value: {}", value);
            return "{ \"errorMessage\": \"Exception during serialization.\" }";
        }
    }
}
