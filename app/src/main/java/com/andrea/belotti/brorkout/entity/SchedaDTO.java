package com.andrea.belotti.brorkout.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class SchedaDTO  implements Serializable {

    private List<String> giornate;
    private int numeroGiornate;
    private String nome;
    private String creationDate;
    private String updateDate;
    private String idCreator;
    private String idUser;
}
