package com.example.grpc_nats_client.service;


import com.example.grpc_nats_client.HelloRequest;
import com.example.grpc_nats_client.HelloResponse;
import com.example.grpc_nats_client.HelloServiceGrpc;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import net.devh.boot.grpc.client.inject.GrpcClient;


@Service
public class GrpcClientService {

//    @GrpcClient("my-grpc-client")
//    private  HelloServiceGrpc.HelloServiceBlockingStub stub;
//
//    public String sayHello(String name){
//        HelloRequest request = HelloRequest.newBuilder().setName(name).build();
//        HelloResponse response = stub.sayHello(request);
//        return response.getMessage();
//    }
private static final int PORT = 9090;
    private static final Logger logger = LoggerFactory.getLogger(GrpcClientService.class);
    public static final String HOST = "localhost";
    private final HelloServiceGrpc.HelloServiceStub asyncStub = HelloServiceGrpc.newStub(
            ManagedChannelBuilder.forAddress(HOST, PORT)
                    .usePlaintext()
                    .build()
    );
    public String sayHello(String name) {
        final HelloRequest sampleRequest = HelloRequest.newBuilder()
                .setName(name)
                .build();

        asyncStub.sayHello(sampleRequest, new StreamObserver<HelloResponse>() {
            @Override
            public void onNext(HelloResponse value) {
                logger.info("GrpcClient#sampleCall - {}", value);
            }

            @Override
            public void onError(Throwable t) {
                logger.error("GrpcClient#sampleCall - onError");
            }

            @Override
            public void onCompleted() {
                logger.info("GrpcClient#sampleCall - onCompleted");
            }
        });
        return "string";
    }

}
