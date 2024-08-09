package com.example.nats_grpc_example.service;

import io.nats.client.Connection;
import io.nats.client.Message;
import io.nats.client.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.Duration;

@Service
public class NatsService {
    private final Connection natsConnection;
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
        return new String(msg.getData(), StandardCharsets.UTF_8);
    }
}
