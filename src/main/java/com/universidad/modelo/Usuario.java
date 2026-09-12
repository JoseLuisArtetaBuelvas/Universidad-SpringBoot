package com.universidad.modelo;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String clave;
    private String nombre;
    private String email;
    private String rol;
}
