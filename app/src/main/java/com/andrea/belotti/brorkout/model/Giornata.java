package com.andrea.belotti.brorkout.model;


import java.io.Serializable;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Giornata implements Serializable {

    private int numberOfDays;
    private List<Esercizio> exercises;
    private String creationDate;
    private String updateDate;
    private boolean used;

    public CompleteState isDayComplete() {
        if (this.getExercises().stream().allMatch(exe -> exe.isExeComplete() == CompleteState.COMPLETE_OK)) {
            return CompleteState.COMPLETE_OK;
        } else if (this.getExercises().stream().allMatch(exe -> exe.isExeComplete() == CompleteState.INCOMPLETE_KO)) {
            return CompleteState.INCOMPLETE_KO;
        } else {
            return CompleteState.INCOMPLETE;
        }
    }

}
