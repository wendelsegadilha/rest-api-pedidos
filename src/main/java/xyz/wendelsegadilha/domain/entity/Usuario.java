package xyz.wendelsegadilha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "{campo.login.obrigatorio}")
    @Column
    private String login;
    @NotEmpty(message = "{campo.senha.obrigatorio}")
    @Column
    private String senha;
    @NotNull
    @Column
    private boolean admin;
}
