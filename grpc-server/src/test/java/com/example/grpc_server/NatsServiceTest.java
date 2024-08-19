package com.example.grpc_server;

import com.example.grpc_server.service.NatsService;
import io.nats.client.Connection;
import io.nats.client.Nats;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NatsServiceTest {
    private static final Logger logger = LoggerFactory.getLogger(NatsServiceTest.class);

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
        //먼저 subject를 구독하고 있으며 대기한다.
        Thread subscriberThread = new Thread(() -> {
            try {
                natsService.subscribeMessage(subject);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        subscriberThread.start();

        // 메시지를 발행
        Thread.sleep(500); // 구독자가 준비될 시간을 줍니다.
        natsService.publishMessage(subject, message);

        // 구독 스레드가 완료되기를 기다린다.
        subscriberThread.join();
    }
}
