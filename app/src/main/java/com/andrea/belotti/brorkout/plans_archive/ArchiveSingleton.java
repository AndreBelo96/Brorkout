package com.andrea.belotti.brorkout.plans_archive;

import com.andrea.belotti.brorkout.model.Giornata;
import com.andrea.belotti.brorkout.model.Scheda;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArchiveSingleton {

    // private static instance variable to hold the singleton instance
    private static volatile ArchiveSingleton INSTANCE = null;

    private String chosenUserId;
    private Giornata chosenDay;

    // Caching
    private List<Scheda> userSelectedPlans;
    private LocalDate selectedDate;

    // private constructor to prevent instantiation of the class
    private ArchiveSingleton() {
    }

    public static ArchiveSingleton getInstance() {
        // Check if the instance is already created
        if (INSTANCE == null) {
            // synchronize the block to ensure only one thread can execute at a time
            synchronized (ArchiveSingleton.class) {
                // check again if the instance is already created
                if (INSTANCE == null) {
                    // create the singleton instance
                    INSTANCE = new ArchiveSingleton();
                }
            }
        }
        // return the singleton instance
        return INSTANCE;
    }


}
