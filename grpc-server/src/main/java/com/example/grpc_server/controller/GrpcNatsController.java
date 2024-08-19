package com.example.grpc_server.controller;

import com.example.grpc_server.service.NatsService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GrpcNatsController {

    private final NatsService natsService;

    @Autowired
    public GrpcNatsController(NatsService natsService) {
        this.natsService = natsService;
    }

    @PostConstruct
    public void init() throws Exception {
        // 애플리케이션 시작 시 자동으로 구독 메서드를 실행
        subscribeFromNats("testSubject");
    }
    @GetMapping("/publish")
    public String publishToNats(@RequestParam String subject, @RequestParam String message) throws Exception {
        natsService.publishMessage(subject, message);
        return "Published message: " + message;
    }

    @GetMapping("/subscribe")
    public void subscribeFromNats(@RequestParam String subject) throws Exception {
         natsService.subscribeMessage(subject);
    }
}
