package com.example.nats_grpc_example.server;

import com.example.nats_grpc_example.service.HelloServiceImpl;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class GrpcServer implements CommandLineRunner {
    private Server server;

    @Override
    public void run(String... args) throws Exception {
        server = ServerBuilder.forPort(9090)
                .addService(new HelloServiceImpl())
                .build()
                .start();
        System.out.println("gRPC 서버 시작 포트 9090");

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("gRPC 서버 종료");
            if (server != null) {
                server.shutdown();
            }
        }));

        server.awaitTermination();
    }
}
