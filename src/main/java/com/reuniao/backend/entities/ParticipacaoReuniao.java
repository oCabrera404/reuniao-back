package com.reuniao.backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.reuniao.backend.entities.enums.StatusParticipacao;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParticipacaoReuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "reuniao_id")
    @JsonIgnore
    private Reuniao reuniao;

    @Enumerated(EnumType.STRING)
    private StatusParticipacao status;
}
