package com.universidad.controladores;

import com.universidad.servicio.IRecuperacionServicio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
public class ControladorRecuperacion {

    @Autowired
    IRecuperacionServicio recuperacionServicio;

    @GetMapping("/recuperar")
    public String recuperar() {
        return "recuperar";
    }

    @PostMapping("/recuperar")
    public String solicitar(@RequestParam String idOEmail, Model modelo) {
        recuperacionServicio.solicitarRecuperacion(idOEmail);
        log.info("Solicitud de recuperación de clave procesada");
        modelo.addAttribute("solicitudEnviada", true);
        return "recuperar";
    }

    @GetMapping("/restablecer")
    public String restablecer(@RequestParam String token, Model modelo) {
        boolean tokenValido = recuperacionServicio.validarToken(token).isPresent();
        modelo.addAttribute("tokenValido", tokenValido);
        modelo.addAttribute("token", token);
        return "restablecer";
    }

    @PostMapping("/restablecer")
    public String guardarNuevaClave(
            @RequestParam String token,
            @RequestParam String nuevaClave,
            @RequestParam String confirmarClave,
            Model modelo) {

        if (!nuevaClave.equals(confirmarClave)) {
            modelo.addAttribute("tokenValido", true);
            modelo.addAttribute("token", token);
            modelo.addAttribute("error", "clavesNoCoinciden");
            return "restablecer";
        }

        boolean actualizado = recuperacionServicio.restablecerClave(token, nuevaClave);
        if (!actualizado) {
            modelo.addAttribute("tokenValido", false);
            return "restablecer";
        }

        return "redirect:/login?claveRestablecida=true";
    }
}
