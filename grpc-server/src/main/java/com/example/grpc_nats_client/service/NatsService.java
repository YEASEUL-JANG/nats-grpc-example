<<<<<<<< HEAD:src/main/java/com/example/grpc_server/service/NatsService.java
package com.example.grpc_server.service;
========
package com.example.grpc_nats_client.service;
>>>>>>>> origin/master:src/main/java/com/example/grpc_nats_client/service/NatsService.java

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class NatsService {
    private final Connection natsConnection;
    private static final Logger logger = LoggerFactory.getLogger(NatsService.class);

    @Autowired
    public NatsService(Connection natsConnection) {
        this.natsConnection = natsConnection;
    }
    public void publishMessage(String subject, String message) throws Exception {
        natsConnection.publish(subject, message.getBytes(StandardCharsets.UTF_8));
    }

    public String subscribeMessage(String subject) throws Exception {
        Subscription subscription = natsConnection.subscribe(subject);
        Message msg = subscription.nextMessage(Duration.ofSeconds(5)); //5초 타임아웃 설정
        logger.info("Received message: {}", msg);
        if (msg == null) {
            throw new RuntimeException("No message received within the timeout period.");
        }
        return new String(msg.getData(), StandardCharsets.UTF_8);
    }
}
