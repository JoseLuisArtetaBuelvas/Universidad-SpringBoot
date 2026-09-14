package com.universidad.servicio;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BrevoEmailServicio implements IEmailServicio {

    private static final String BREVO_API_URL = "https://api.brevo.com/v3/smtp/email";

    @Value("${brevo.api.key}")
    private String apiKey;

    @Value("${brevo.sender.email}")
    private String remitenteEmail;

    @Value("${brevo.sender.nombre}")
    private String remitenteNombre;

    private final RestClient restClient = RestClient.create();

    @Override
    public void enviarCorreo(String destinatario, String asunto, String contenidoHtml) {
        Map<String, Object> cuerpo = Map.of(
                "sender", Map.of("name", remitenteNombre, "email", remitenteEmail),
                "to", List.of(Map.of("email", destinatario)),
                "subject", asunto,
                "htmlContent", contenidoHtml
        );

        try {
            restClient.post()
                    .uri(BREVO_API_URL)
                    .header("api-key", apiKey)
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .body(cuerpo)
                    .retrieve()
                    .toBodilessEntity();
            log.info("Correo enviado a través de Brevo a: " + destinatario);
        } catch (Exception e) {
            log.error("Error al enviar el correo mediante Brevo: " + e.getMessage(), e);
        }
    }
}
