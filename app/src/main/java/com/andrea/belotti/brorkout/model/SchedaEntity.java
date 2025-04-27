package com.andrea.belotti.brorkout.model;

import static com.andrea.belotti.brorkout.utils.JsonGeneratorUtil.generateJsonFromObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SchedaEntity implements Serializable {

    private String id;
    private List<String> giornate;
    private int numeroGiornate;
    private String nome;
    private String creationDate;
    private String updateDate;
    private String idCreator;
    private String idUser;

    public SchedaEntity(Scheda plan) {

        List<String> giornate = new ArrayList<>();

        for (Giornata day : plan.getGiornate()) {
            String dayDTO = new String(generateJsonFromObject(day));
            giornate.add(dayDTO);
        }

        this.id = plan.getId();
        this.giornate = giornate;
        this.numeroGiornate = plan.getNumeroGiornate();
        this.nome = plan.getNome();
        this.creationDate = plan.getCreationDate();
        this.updateDate = plan.getUpdateDate();
        this.idCreator = plan.getIdCreator();
        this.idUser = plan.getIdUser();

    }}
