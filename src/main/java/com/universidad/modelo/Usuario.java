package com.universidad.modelo;
import lombok.Data;
@Data
public class Usuario {
    private String id;
    private String clave;
    private String nombre;
    private String email;
    private String rol;
}
