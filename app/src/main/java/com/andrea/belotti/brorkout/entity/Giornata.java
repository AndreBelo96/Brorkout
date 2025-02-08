package com.andrea.belotti.brorkout.entity;


import com.andrea.belotti.brorkout.model.Esercizio;

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

}
