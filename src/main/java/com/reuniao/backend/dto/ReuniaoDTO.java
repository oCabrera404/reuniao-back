package com.reuniao.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
public class ReuniaoDTO {

    private String titulo;
    private LocalDate data;
    private LocalTime horario;

    private Long salaId;

    private List<Long> participantesIds;

}
