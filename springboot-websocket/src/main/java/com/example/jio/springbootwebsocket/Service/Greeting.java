package com.example.jio.springbootwebsocket.Service;

import org.springframework.stereotype.Component;

@Component
public class Greeting {

    private String content;

    public Greeting() {
    }

    public Greeting(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

}