package com.reuniao.backend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema = "reunioes")
public class Reuniao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String descricao;
    private LocalDate data;
    private LocalTime inicio;
    private LocalTime termino;

    @ManyToOne
    @JoinColumn(name = "criador_id")
    private Usuario criador;

    @ManyToMany
    @JoinTable(
            name = "reuniao_usuarios",
            joinColumns = @JoinColumn(name = "reuniao_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> participantes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "sala_id")
    private Sala sala;
}
