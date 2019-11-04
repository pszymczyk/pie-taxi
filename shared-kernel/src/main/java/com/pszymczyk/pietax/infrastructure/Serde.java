package com.pszymczyk.pietax.infrastructure;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class Serde {

    private static final Logger log = LoggerFactory.getLogger(Serde.class);

    private final ObjectMapper objectMapper;

    public Serde(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public String serialize(Object value) {
        try {
            return objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            log.error("Error during serialization, value: {}", value);
            return "{ \"errorMessage\": \"Exception during serialization.\" }";
        }
    }

    public Map<String, String> deserialize(String payload) {
        try {
            return objectMapper.readValue(payload, TypeFactory.defaultInstance().constructMapType(HashMap.class, String.class, String.class));
        } catch (JsonProcessingException e) {
            log.error("Error during serialization, value: {}", payload);
            return Collections.emptyMap();
        }
    }
}
