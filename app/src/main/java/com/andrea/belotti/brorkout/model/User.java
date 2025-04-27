package com.andrea.belotti.brorkout.model;

import com.andrea.belotti.brorkout.utils.AppMethodsUtils;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private String id;
    private String friendCode;
    private String username;
    private String email;
    private String base64Image;

    private User(String username, String email, String friendId) {
        this.username = username;
        this.email = email;
        this.friendCode = friendId;
    }

    public static User createUser(String username, String email, long friendId) {
        return new User(username, email, AppMethodsUtils.generate8CharString(friendId + ""));
    }


}
