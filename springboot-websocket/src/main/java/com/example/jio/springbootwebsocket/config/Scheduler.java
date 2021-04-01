package com.example.jio.springbootwebsocket.config;


import com.example.jio.springbootwebsocket.Service.Greeting;
import com.example.jio.springbootwebsocket.Service.HelloMessage;
import org.apache.logging.log4j.message.SimpleMessage;
import org.apache.logging.log4j.message.SimpleMessageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@EnableScheduling
@Configuration
public class Scheduler {

    @Autowired
    private SimpMessagingTemplate messageSender;


    @Scheduled(fixedDelay = 3000)
    public void send(){
        messageSender.convertAndSend("/topic/greetings", new Greeting("you are connected"));
    }


}
