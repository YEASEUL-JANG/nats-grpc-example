//package com.example.grpc_nats_client;
//
//import io.grpc.Server;
//import io.grpc.ManagedChannel;
//import io.grpc.inprocess.InProcessChannelBuilder;
//import io.grpc.inprocess.InProcessServerBuilder;
//import org.junit.jupiter.api.AfterAll;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//
//public class HelloServiceImplTest {
//    private static Server inProcessServer;
//    private static ManagedChannel inProcessChannel;
//
//    @BeforeAll
//    public static void setup() throws IOException{
//        //In-process gRPC 서버 실행
//        inProcessServer = InProcessServerBuilder
//                .forName("in-process-server")
//                .directExecutor()
//                .addService(new HelloServiceImpl())
//                .build()
//                .start();
//        //In-process gRPC 채널 생성
//        inProcessChannel = InProcessChannelBuilder
//                .forName("in-process-server")
//                .directExecutor()
//                .build();
//    }
//
//    @AfterAll
//    public static void tearDown(){
//        inProcessChannel.shutdown();
//        inProcessServer.shutdown();
//    }
//
//    @Test
//    public void testSayHello(){
//        HelloServiceGrpc.HelloServiceBlockingStub blockingStub = HelloServiceGrpc.newBlockingStub(inProcessChannel);
//
//        HelloRequest request = HelloRequest.newBuilder().setName("World").build();
//        HelloResponse response = blockingStub.sayHello(request);
//
//        assertEquals("Hello, World", response.getMessage());
//    }
//
//
//}
