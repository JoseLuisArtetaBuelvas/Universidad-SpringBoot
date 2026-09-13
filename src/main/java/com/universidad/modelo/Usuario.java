package com.universidad.modelo;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @NotEmpty
    private String id;

    @NotEmpty
    private String clave;

    @NotEmpty
    private String nombre;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String rol;
}
