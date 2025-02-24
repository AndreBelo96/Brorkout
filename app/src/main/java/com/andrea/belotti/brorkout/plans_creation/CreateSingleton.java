package com.andrea.belotti.brorkout.plans_creation;

import com.andrea.belotti.brorkout.entity.Giornata;
import com.andrea.belotti.brorkout.entity.Scheda;
import com.andrea.belotti.brorkout.entity.User;
import com.andrea.belotti.brorkout.model.Esercizio;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

// TODO fare un metodo per eliminarlo -> da chiamare poco prima del cambio activity
@Getter
@Setter
public class CreateSingleton {

    // private static instance variable to hold the singleton instance
    private static volatile CreateSingleton INSTANCE = null;

    private List<User> usersToShare;

    private Scheda planToCreate;

    private Esercizio addExeInCreation;

    private Giornata dayToCopy;

    private int selectedExe = -1;


    // private constructor to prevent instantiation of the class
    private CreateSingleton() {}

    public static CreateSingleton getInstance() {
        // Check if the instance is already created
        if(INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (CreateSingleton.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new CreateSingleton();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }


}
