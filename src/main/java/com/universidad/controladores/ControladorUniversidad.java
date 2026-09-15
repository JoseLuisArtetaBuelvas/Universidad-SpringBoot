package com.universidad.controladores;

import java.util.List;

import com.universidad.servicio.IUniversidadServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping({"/universidades/eliminar/{id}", "/universidades/eliminar"})
    public String eliminar(Universidad universidad) {
        log.info("Eliminando universidad: " + universidad);
        universidadServicio.eliminarUniversidad(universidad);
        return "redirect:/universidades";
    }

    @GetMapping("/universidades/reportes")
    public String reportes(
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) String categoria,
            @RequestParam(required = false) Integer numSedes,
            Model modelo) {

        modelo.addAttribute("ciudades", universidadServicio.listarCiudades());
        modelo.addAttribute("categorias", universidadServicio.listarCategorias());

        if (ciudad != null && !ciudad.isBlank()) {
            modelo.addAttribute("resultadoCiudad", universidadServicio.buscarPorCiudad(ciudad));
            modelo.addAttribute("ciudad", ciudad);
        }

        if (categoria != null && !categoria.isBlank() && numSedes != null) {
            modelo.addAttribute("resultadoCategoria", universidadServicio.buscarPorCategoriaYSedesMinimas(categoria, numSedes));
            modelo.addAttribute("categoria", categoria);
            modelo.addAttribute("numSedes", numSedes);
        }

        return "universidad-reportes";
    }
}
