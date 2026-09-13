package com.universidad.modelo;

import java.io.Serializable;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "universidades")
@Data
public class Universidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @NotEmpty
    private String categoria;

    @NotEmpty
    private String web;

    @NotEmpty
    private String rector;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String acceso;

    @NotEmpty
    private String telefono;

    @NotEmpty
    private String ciudad;

    @NotNull
    @Min(0)
    private Integer numeroCarreras;

    @NotNull
    @Min(0)
    private Integer numSedes;
}
