package com.universidad.controladores;

import java.util.List;

import com.universidad.servicio.IUniversidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import lombok.extern.slf4j.Slf4j;
import com.universidad.modelo.Universidad;

@Controller
@Slf4j
public class ControladorUniversidad {

    @Autowired
    IUniversidadServicio universidadServicio;

    @GetMapping("/universidades")
    public String listar(Model modelo) {
        List<Universidad> listaUniversidades = universidadServicio.listarUniversidades();
        modelo.addAttribute("universidades", listaUniversidades);
        log.info("Listando universidades registradas");
        return "universidades";
    }
}
