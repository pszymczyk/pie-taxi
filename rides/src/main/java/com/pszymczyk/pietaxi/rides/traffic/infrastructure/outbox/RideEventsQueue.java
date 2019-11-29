package com.pszymczyk.pietaxi.rides.traffic.infrastructure.outbox;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.tape.QueueFile;

@Component
class RideEventsQueue {

    private static final Logger log = LoggerFactory.getLogger(RideEventsQueue.class);

    private final ObjectMapper objectMapper;
    private final String pathname;
    private QueueFile queueFile;

    public RideEventsQueue(ObjectMapper objectMapper, @Value("${tape.queue.directory}") String pathname) {
        this.objectMapper = objectMapper;
        this.pathname = pathname;
    }

    @PostConstruct
    void initQueue() throws IOException {
        File file = new File(pathname + "/ride.queue");
        try {
            queueFile = new QueueFile(file);
        } catch (EOFException eofException) {
            //wrong initialized file
            file.delete();
            queueFile = new QueueFile(file);
        }
    }

    void add(RidesEvent ridesEvent) {
        try {
            queueFile.add(objectMapper.writeValueAsBytes(ridesEvent));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
