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

    /**
     * gRPC 서버에 메시지를 전송하고 응답을 반환한다.
     * @param name
     * @return
     */
    @GetMapping("/grpc")
    public String callGrpcService(@RequestParam String name){
        return grpcClientService.sayHello(name);
    }

    /**
     * NATS 서버에 메시지를 발행한다.
     * @param subject
     * @param message
     */

    @GetMapping("/publish")
    public String publishToNats(@RequestParam String subject, @RequestParam String message) throws Exception {
        natsService.publishMessage(subject, message);
        return "Published message: " + message;
    }

    /**
     * NATS 서버로부터 메시지를 구독한다.
     * @param subject
     */
    @GetMapping("/subscribe")
    public void subscribeFromNats(@RequestParam String subject) throws Exception {
         natsService.subscribeMessage(subject);
    }
}
