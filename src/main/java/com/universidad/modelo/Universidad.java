package com.universidad.modelo;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "universidades")
@Data
public class Universidad implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    private String categoria;

    private String web;

    private String rector;

    private String email;

    private String acceso;

    private String telefono;

    private String ciudad;

    private Integer numeroCarreras;

    private Integer numSedes;
}
