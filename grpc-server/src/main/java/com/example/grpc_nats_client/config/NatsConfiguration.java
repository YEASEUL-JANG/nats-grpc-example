<<<<<<<< HEAD:src/main/java/com/example/grpc_server/config/NatsConfiguration.java
package com.example.grpc_server.config;
========
package com.example.grpc_nats_client.config;
>>>>>>>> origin/master:src/main/java/com/example/grpc_nats_client/config/NatsConfiguration.java

import io.nats.client.Connection;
import io.nats.client.Nats;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
@Configuration
public class NatsConfiguration {
    @Value("${nats.url}")
    private String natsUrl;

    @Bean
    public Connection natsConnection() throws IOException, InterruptedException {
        return Nats.connect(natsUrl);
    }


}
