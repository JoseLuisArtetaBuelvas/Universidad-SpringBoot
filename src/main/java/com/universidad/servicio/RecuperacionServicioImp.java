package com.universidad.servicio;

import com.universidad.ITokenRecuperacionCrud;
import com.universidad.IUsuarioCrud;
import com.universidad.modelo.TokenRecuperacion;
import com.universidad.modelo.Usuario;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class RecuperacionServicioImp implements IRecuperacionServicio {

    private static final long MINUTOS_VALIDEZ_TOKEN = 30;

    @Autowired
    private IUsuarioCrud usuarioCrud;

    @Autowired
    private ITokenRecuperacionCrud tokenCrud;

    @Autowired
    private IEmailServicio emailServicio;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${app.base-url}")
    private String baseUrl;

    @Override
    @Transactional
    public void solicitarRecuperacion(String idOEmail) {
        Optional<Usuario> usuario = usuarioCrud.findById(idOEmail)
                .or(() -> usuarioCrud.findByEmail(idOEmail));

        if (usuario.isEmpty() || usuario.get().getEmail() == null) {
            log.info("Solicitud de recuperación para un identificador no encontrado o sin correo asociado");
            return;
        }

        String token = UUID.randomUUID().toString();
        TokenRecuperacion tokenRecuperacion = new TokenRecuperacion();
        tokenRecuperacion.setToken(token);
        tokenRecuperacion.setUsuarioId(usuario.get().getId());
        tokenRecuperacion.setFechaExpiracion(LocalDateTime.now().plusMinutes(MINUTOS_VALIDEZ_TOKEN));
        tokenCrud.save(tokenRecuperacion);

        String enlace = baseUrl + "/restablecer?token=" + token;
        String contenidoHtml = "<p>Hola " + usuario.get().getNombre() + ",</p>"
                + "<p>Recibimos una solicitud para restablecer tu contraseña en el portal de Universidad.</p>"
                + "<p><a href=\"" + enlace + "\">Haz clic aquí para crear una nueva contraseña</a></p>"
                + "<p>Este enlace vence en " + MINUTOS_VALIDEZ_TOKEN + " minutos. Si no solicitaste este cambio, ignora este correo.</p>";

        emailServicio.enviarCorreo(usuario.get().getEmail(), "Recuperación de contraseña", contenidoHtml);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> validarToken(String token) {
        return tokenCrud.findById(token)
                .filter(t -> t.getFechaExpiracion().isAfter(LocalDateTime.now()))
                .map(TokenRecuperacion::getUsuarioId);
    }

    @Override
    @Transactional
    public boolean restablecerClave(String token, String nuevaClave) {
        Optional<String> usuarioId = validarToken(token);
        if (usuarioId.isEmpty()) {
            return false;
        }

        Optional<Usuario> usuario = usuarioCrud.findById(usuarioId.get());
        if (usuario.isEmpty()) {
            return false;
        }

        usuario.get().setClave(passwordEncoder.encode(nuevaClave));
        usuarioCrud.save(usuario.get());
        tokenCrud.deleteById(token);
        return true;
    }
}
