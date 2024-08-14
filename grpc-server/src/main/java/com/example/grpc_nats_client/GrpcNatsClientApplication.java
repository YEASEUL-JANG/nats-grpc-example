<<<<<<<< HEAD:src/main/java/com/example/grpc_server/GrpcServerApplication.java
package com.example.grpc_server;
========
package com.example.grpc_nats_client;
>>>>>>>> origin/master:src/main/java/com/example/grpc_nats_client/GrpcNatsClientApplication.java

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
<<<<<<<< HEAD:src/main/java/com/example/grpc_server/GrpcServerApplication.java
public class GrpcServerApplication {

	public static void main(String[] args) {

		SpringApplication.run(GrpcServerApplication.class, args);
========
public class GrpcNatsClientApplication {

	public static void main(String[] args) {

		SpringApplication.run(GrpcNatsClientApplication.class, args);
>>>>>>>> origin/master:src/main/java/com/example/grpc_nats_client/GrpcNatsClientApplication.java
	}

}
