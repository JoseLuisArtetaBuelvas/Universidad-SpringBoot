package com.universidad;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class ControladorInicio {
    @GetMapping("/")
    public String inicio() {
        log.info("Ejecutando el controlador inicio");
        return "index.html";
    }
}
