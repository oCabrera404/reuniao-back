package com.reuniao.backend.entities;

import ch.qos.logback.core.status.Status;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.reuniao.backend.entities.enums.RoleUsers;
import com.reuniao.backend.entities.enums.StatusUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.management.relation.Role;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(schema = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    @Column(unique = true)
    private String email;

    @JsonIgnore
    @JsonIgnoreProperties({"reunioes"})
    private String senha;

    @Column
    @Enumerated(EnumType.STRING)
    private StatusUser status;

    @Column
    @Enumerated(EnumType.STRING)
    private RoleUsers roleUsers;

    @PrePersist
    public void prePersist() {
        if (roleUsers == null) {
            roleUsers = roleUsers.USUARIO;
        }

        if (status == null) {
            status = status.ATIVO;
        }
    }
}
