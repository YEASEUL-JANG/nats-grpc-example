package com.example.nats_grpc_example.service;

import com.example.nats_grpc_example.HelloRequest;
import com.example.nats_grpc_example.HelloResponse;
import com.example.nats_grpc_example.HelloServiceGrpc;
import io.grpc.stub.StreamObserver;

public class HelloServiceImpl extends HelloServiceGrpc.HelloServiceImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloResponse> responseObserver){
        String greeting = "Hello, " + request.getName();
        HelloResponse response = HelloResponse.newBuilder().setMessage(greeting).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

}
