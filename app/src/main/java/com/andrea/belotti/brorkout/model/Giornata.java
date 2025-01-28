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
    private String createDate;
    private String updateDate;



}
