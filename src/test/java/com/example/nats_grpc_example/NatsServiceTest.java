package com.example.nats_grpc_example;

import com.example.nats_grpc_example.service.NatsService;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NatsServiceTest {
    private static Connection natsConnection;
    private static NatsService natsService;

    @BeforeAll
    public static void setup() throws Exception{
        natsConnection = Nats.connect("nats://localhost:4222");
        natsService = new NatsService(natsConnection);
    }

    @AfterAll
    public static void cleanup() throws Exception{
        natsConnection.close();
    }

    @Test
    public void testPublishAndSubscribe() throws Exception{
        String subject = "testSubject";
        String message = "Hello, NATS!";

        natsService.publishMessage(subject,message);
        Thread.sleep(1000);

        String receivedMessage = natsService.subscribeMessage(subject);

        assertEquals(message,receivedMessage);
    }
}
