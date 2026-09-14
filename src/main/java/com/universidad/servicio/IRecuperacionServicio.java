package com.universidad.servicio;

import java.util.Optional;

public interface IRecuperacionServicio {
    void solicitarRecuperacion(String idOEmail);
    Optional<String> validarToken(String token);
    boolean restablecerClave(String token, String nuevaClave);
}
