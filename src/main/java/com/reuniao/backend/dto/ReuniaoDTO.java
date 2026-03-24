package com.reuniao.backend.dto;

import com.reuniao.backend.entities.enums.StatusReuniao;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private StatusReuniao status;
    private Long salaId;

    private List<String> participantesEmails;

}
