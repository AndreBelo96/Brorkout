package com.andrea.belotti.brorkout.entity;

import static com.andrea.belotti.brorkout.utils.JsonGeneratorUtil.generateExercisesFromJson;
import static com.andrea.belotti.brorkout.utils.JsonGeneratorUtil.generateJsonFromObject;

import com.andrea.belotti.brorkout.model.Giornata;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.android.material.timepicker.TimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Scheda implements Serializable {

    private List<Giornata> giornate;
    private int numeroGiornate;
    private String nome;
    // TODO prova a usare jackson e non Gson e usare @JsonFormat per localDateTime
    private String creationDate;
    private String updateDate;
    private String idCreator;
    private String idUser;

    public Scheda(SchedaDTO planDTOs) throws JsonProcessingException {

        List<Giornata> giornate = new ArrayList<>();

        for (String day : planDTOs.getGiornate()) {
            giornate.add(generateExercisesFromJson(day));
        }

        this.setGiornate(giornate);
        this.numeroGiornate = planDTOs.getNumeroGiornate();
        this.nome = planDTOs.getNome();
        this.creationDate = planDTOs.getCreationDate();
        this.updateDate = planDTOs.getUpdateDate();
        this.idCreator = planDTOs.getIdCreator();
        this.idUser = planDTOs.getIdUser();

    }

    public Scheda(List<Giornata> giornate) {
        this.giornate = giornate;
        this.numeroGiornate = giornate.size();
        this.creationDate = LocalDateTime.now().toString();
    }

}
