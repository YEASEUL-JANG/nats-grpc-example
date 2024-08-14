package com.example.grpc_server.controller;

import com.example.grpc_server.service.NatsService;
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

    @GetMapping("/publish")
    public String publishToNats(@RequestParam String subject, @RequestParam String message) throws Exception {
        natsService.publishMessage(subject, message);
        return "Published message: " + message;
    }

    @GetMapping("/subscribe")
    public String subscribeFromNats(@RequestParam String subject) throws Exception {
        return natsService.subscribeMessage(subject);
    }
}
