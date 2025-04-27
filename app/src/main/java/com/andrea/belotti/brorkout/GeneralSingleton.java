package com.andrea.belotti.brorkout;

import com.andrea.belotti.brorkout.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GeneralSingleton {

    // private static instance variable to hold the singleton instance
    private static volatile GeneralSingleton INSTANCE = null;

    private User loggedUser;

    // private constructor to prevent instantiation of the class
    private GeneralSingleton() {}

    public static GeneralSingleton getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (GeneralSingleton.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new GeneralSingleton();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }


}
