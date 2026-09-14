package com.universidad.modelo;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tokens_recuperacion")
@Data
public class TokenRecuperacion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String token;

    @Column(name = "usuario_id", nullable = false)
    private String usuarioId;

    @Column(name = "fecha_expiracion", nullable = false)
    private LocalDateTime fechaExpiracion;
}
