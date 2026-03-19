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
    private String descricao;
    private LocalDate data;
    private LocalTime inicio;
    private LocalTime termino;

    private Long salaId;

    private List<String> participantesEmails;

}
