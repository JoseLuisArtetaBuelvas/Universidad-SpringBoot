package com.universidad.controladores;

import java.util.List;

import com.universidad.servicio.IUniversidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import lombok.extern.slf4j.Slf4j;
import com.universidad.modelo.Universidad;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;

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

    @GetMapping("/universidades/agregar")
    public String agregar(Universidad universidad) {
        return "universidad-formulario";
    }

    @PostMapping("/universidades/guardar")
    public String guardar(@Valid Universidad universidad, Errors errores) {
        if (errores.hasErrors()) {
            return "universidad-formulario";
        }
        universidadServicio.guardarUniversidad(universidad);
        return "redirect:/universidades";
    }

    @GetMapping({"/universidades/modificar/{id}", "/universidades/modificar"})
    public String modificar(Universidad universidad, Model modelo) {
        log.info("Modificando universidad: " + universidad);
        universidad = universidadServicio.buscarUniversidad(universidad);
        modelo.addAttribute("universidad", universidad);
        return "universidad-formulario";
    }
}
