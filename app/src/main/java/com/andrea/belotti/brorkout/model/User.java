package com.andrea.belotti.brorkout.model;

import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private String email;
    private String base64Image;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
