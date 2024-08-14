package com.example.grpc_nats_client.controller;

import com.example.grpc_nats_client.service.GrpcClientService;
import com.example.grpc_nats_client.service.NatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NatsGrpcController {

    private final NatsService natsService;
    private final GrpcClientService grpcClientService;

    @Autowired
    public NatsGrpcController(NatsService natsService, GrpcClientService grpcClientService) {
        this.natsService = natsService;
        this.grpcClientService = grpcClientService;
    }

    @GetMapping("/grpc")
    public String callGrpcService(@RequestParam String name){
        return grpcClientService.sayHello(name);
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
