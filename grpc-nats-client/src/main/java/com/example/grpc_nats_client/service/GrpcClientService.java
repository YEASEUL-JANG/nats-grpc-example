package com.example.grpc_nats_client.service;


import com.example.grpc_nats_client.HelloRequest;
import com.example.grpc_nats_client.HelloResponse;
import com.example.grpc_nats_client.HelloServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;


@Service
public class GrpcClientService {
private static final int PORT = 9090;
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientService.class);
    public static final String HOST = "localhost";
    private final ManagedChannel channel;
    private final HelloServiceGrpc.HelloServiceStub asyncStub;

    public GrpcClientService() {
        this.channel = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext()
                .build();
        //HelloServiceGrpc.HelloServiceStub는 비동기 호출에 사용됨.
        this.asyncStub = HelloServiceGrpc.newStub(channel);
    }
    public String sayHello(String name) {
        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
        //비동기 호출의 결과를 기다리고 반환할 수 있는 비동기 제어 메커니즘
        CountDownLatch latch = new CountDownLatch(1);
        AtomicReference<String> responseMessage = new AtomicReference<>("No response");

        asyncStub.sayHello(request, new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse response) {
                responseMessage.set(response.getMessage());
            }

            @Override
            public void onError(Throwable t) {
                logger.error("Error calling gRPC server: {}", t.getMessage());
                responseMessage.set("Error: " + t.getMessage());
                latch.countDown();
            }

            @Override
            public void onCompleted() {
                latch.countDown();
            }
        });

        try {
            latch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "Interrupted while waiting for response";
        }

        return responseMessage.get();
    }

    public void shutdown() {
        channel.shutdown();
    }
}
