package com.example.demo.requests;

import com.example.demo.models.User;

public class AddPersonRequest {

    private User user;
    private RequestWithString string;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public RequestWithString getString() {
        return string;
    }

    public void setString(RequestWithString string) {
        this.string = string;
    }
}
