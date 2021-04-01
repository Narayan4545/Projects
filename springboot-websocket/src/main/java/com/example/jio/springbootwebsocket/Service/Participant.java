package com.example.jio.springbootwebsocket.Service;

import org.springframework.stereotype.Component;

@Component
public class Participant {
    private String firstName;
    private String LastName;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }
}
