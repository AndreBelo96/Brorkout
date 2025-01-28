package com.andrea.belotti.brorkout.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

    private String username;
    private String email;
    private String base64Image;

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }


}
