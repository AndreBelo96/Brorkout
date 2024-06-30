package com.andrea.belotti.brorkout.model;

import java.time.LocalDate;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MetaData {

    private final LocalDate creationDate;
    private final LocalDate updateDate;

    public MetaData(LocalDate creationDate,
                    LocalDate updateDate) {
        this.creationDate = creationDate;
        this.updateDate = updateDate;
    }

}
