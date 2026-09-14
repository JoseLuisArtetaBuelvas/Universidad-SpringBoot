package com.universidad.servicio;

public interface IEmailServicio {
    void enviarCorreo(String destinatario, String asunto, String contenidoHtml);
}
