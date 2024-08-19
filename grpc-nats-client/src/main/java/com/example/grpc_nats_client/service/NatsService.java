package com.example.grpc_nats_client.service;



import io.nats.client.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class NatsService {
    private final Connection natsConnection;
    private static final Logger logger = LoggerFactory.getLogger(NatsService.class);

    @Autowired
    public NatsService(Connection natsConnection) {
        this.natsConnection = natsConnection;
    }

    /**
     * 주어진 주제에 대해 메시지를 발행한다.
     *
     * @param subject
     * @param message
     */
    public void publishMessage(String subject, String message) throws Exception {
        natsConnection.publish(subject, message.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 주어진 주제에 대해 메시지를 구독하고 해당 메시지를 반환한다.
     *
     * @param subject
     * @return
     */

    public void subscribeMessage(String subject) throws Exception {
        //Dispatcher 를 통해 MessageHandler 클래스의 메서드 구현
        Dispatcher dispatcher = natsConnection.createDispatcher(new MessageHandler() {
            @Override
            public void onMessage(Message msg) {
                byte[] data = msg.getData(); // getData()를 사용하여 메시지 데이터를 가져옴
                String message = new String(data, StandardCharsets.UTF_8);
                logger.info("Received message on subject {}: {}", subject, message);

                // 로그 메시지를 파일로 기록
                logMessageToFile(subject, message);
            }
        });
        dispatcher.subscribe(subject);
    }

    private void logMessageToFile(String subject, String message) {
        // 현재 날짜를 가져와 파일 이름에 사용
        String currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String fileName = "logs/logs_" + currentDate + ".log";

        // 로그 파일이 저장될 폴더 경로를 설정
        File logDir = new File("logs");

        // logs 디렉토리가 없으면 생성
        if (!logDir.exists()) {
            logDir.mkdirs(); // 여러 폴더를 포함해 경로를 생성
        }

        // 로그 메시지 작성
        String logMessage = String.format("[%s] Subject: %s, Message: %s%n", currentTime, subject, message);

        // 파일에 로그 메시지 기록
        try (FileWriter writer = new FileWriter(fileName, true)) { // true를 사용하여 파일에 내용을 추가
            writer.write(logMessage);
        } catch (IOException e) {
            logger.error("Failed to write log to file: {}", e.getMessage());
        }
    }

}
