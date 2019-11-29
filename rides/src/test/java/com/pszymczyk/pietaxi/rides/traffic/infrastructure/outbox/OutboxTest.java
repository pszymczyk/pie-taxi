package com.pszymczyk.pietaxi.rides.traffic.infrastructure.outbox;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.Clock;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pszymczyk.pietaxi.model.PassengerId;
import com.pszymczyk.pietaxi.model.RideId;
import com.pszymczyk.pietaxi.rides.traffic.model.CorruptedRideFinished;
import com.pszymczyk.pietaxi.rides.traffic.model.Distance;
import com.pszymczyk.pietaxi.rides.traffic.model.DriverId;
import com.pszymczyk.pietaxi.rides.traffic.model.RideEvents;
import com.pszymczyk.pietaxi.rides.traffic.model.RideFinished;
import com.squareup.tape.QueueFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, properties = "tape.queue.directory=./src/test/resources/tape")
class OutboxTest {

    private static final File QUEUE_FILE = new File("./src/test/resources/tape/ride.queue");

    @Autowired
    RideEventsSender rideEventsSender;

    @Autowired
    RideEvents rideEvents;

    @Autowired
    Clock clock;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    RideEventsEntityCrudRepository rideEventsEntityCrudRepository;

    @AfterEach
    void cleanup() throws Exception {
        clearQueue();
    }

    @Test
    void Should_send_events_to_queue() throws Exception {
        //given Outbox with two events
        RideFinished rideFinished = saveRideFinishedInOutbox();
        CorruptedRideFinished corruptedRideFinished = saveCorruptedRideFinishedInOutbox();

        //when send them to queue
        rideEventsSender.send();

        //then
        assertOutboxIsEmpty();

        //and
        assertRideFinishedIsInQueue(rideFinished);
        assertCorruptedRideFinishedIsInQueue(corruptedRideFinished);
    }

    private RideFinished saveRideFinishedInOutbox() {
        RideFinished rideFinished = new RideFinished(
                clock.instant(),
                new RideId(UUID.randomUUID()),
                new PassengerId("kazik"),
                new DriverId("janek"),
                Distance.zero(),
                clock.instant().minus(2, ChronoUnit.HOURS),
                clock.instant().minus(1, ChronoUnit.HOURS));
        rideEvents.publish(rideFinished);
        return rideFinished;
    }

    private CorruptedRideFinished saveCorruptedRideFinishedInOutbox() {
        CorruptedRideFinished corruptedRideFinished = new CorruptedRideFinished(
                UUID.randomUUID(),
                clock.instant(),
                new RideId(UUID.randomUUID()));
        rideEvents.publish(corruptedRideFinished);
        return corruptedRideFinished;
    }

    private void assertOutboxIsEmpty() {
        assertTrue(rideEventsEntityCrudRepository.findAllByProcessedTimeIsNull().isEmpty());
    }

    private void assertRideFinishedIsInQueue(RideFinished rideFinished) throws IOException {
        QueueFile queueFile = new QueueFile(new File("./src/test/resources/tape/ride.queue"));
        RidesEvent rideEvent = objectMapper.readValue(queueFile.peek(), RidesEvent.class);
        assertEquals(rideEvent.eventId, rideFinished.getEventId());
        assertEquals(rideEvent.occurrenceTime, rideFinished.getOccurrenceTime());
        assertEquals(rideEvent.getType(), "RideFinished");
        assertNotNull(rideEvent.getPayload());
        queueFile.remove();
    }

    private void assertCorruptedRideFinishedIsInQueue(CorruptedRideFinished corruptedRideFinished) throws IOException {
        QueueFile queueFile = new QueueFile(new File("./src/test/resources/tape/ride.queue"));
        RidesEvent rideEvent = objectMapper.readValue(queueFile.peek(), RidesEvent.class);
        assertEquals(rideEvent.eventId, corruptedRideFinished.getEventId());
        assertEquals(rideEvent.occurrenceTime, corruptedRideFinished.getOccurrenceTime());
        assertEquals(rideEvent.getType(), "CorruptedRideFinished");
        assertNotNull(rideEvent.getPayload());
        queueFile.remove();
    }

    private void clearQueue() throws Exception {
        PrintWriter writer = new PrintWriter(QUEUE_FILE);
        writer.print("");
        writer.close();
    }
}
