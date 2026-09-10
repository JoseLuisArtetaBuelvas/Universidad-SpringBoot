package com.universidad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import com.universidad.modelo.Usuario;

@Controller
@Slf4j
public class ControladorInicio {
    @GetMapping("/")
    public String inicio(Model modelo) {
        String mensaje = "Saludos desde el controlador de Spring Boot";
        modelo.addAttribute("mensaje", mensaje);
        Usuario u = new Usuario();
        u.setId("1");
        u.setClave("123");
        u.setNombre("Zadu");
        u.setEmail("zadu@example.com");
        u.setRol("Administrador");
        modelo.addAttribute("usuario", u);
        log.info("Ejecutando el controlador inicio");
        return "index";
    }
}
