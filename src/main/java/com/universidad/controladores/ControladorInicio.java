package com.universidad.controladores;

import java.util.Arrays;
import java.util.List;

import com.universidad.servicio.IUsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import com.universidad.modelo.Usuario;
import jakarta.validation.Valid;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@Slf4j
public class ControladorInicio {
    @Autowired
    IUsuarioServicio usuarioServicio;
    @GetMapping("/")
    public String inicio(Model modelo) {
        List<Usuario> listaUsuarios = usuarioServicio.listarUsuarios();
        modelo.addAttribute("usuarios", listaUsuarios);
        log.info("Ejecutando el controlador Spring MVC");
        return "index";
    }

    @GetMapping("/agregar")
    public String agregar(Usuario usuario) {
        return "modificar";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Usuario usuario, Errors errores) {
        if (errores.hasErrors()) {
            return "modificar";
        }
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/";
    }

    @GetMapping({"/modificar/{id}", "/modificar"})
    public String modificar(Usuario usuario, Model modelo) {
        log.info("Modificando usuario: " + usuario);
        usuario = usuarioServicio.buscarUsuario(usuario);
        modelo.addAttribute("usuario", usuario);
        return "modificar";
    }

    @GetMapping({"/eliminar/{id}", "/eliminar"})
    public String eliminar(Usuario usuario) {
        log.info("Eliminando usuario: " + usuario);
        usuarioServicio.eliminarUsuario(usuario);
        return "redirect:/";
    }

    @GetMapping("/reportes")
    public String reportes(
            @RequestParam(required = false) String rol,
            @RequestParam(required = false) String nombre,
            Model modelo) {

        if (rol != null && !rol.isBlank()) {
            modelo.addAttribute("resultadoRol", usuarioServicio.buscarPorRol(rol));
            modelo.addAttribute("rol", rol);
        }

        if (nombre != null && !nombre.isBlank()) {
            modelo.addAttribute("resultadoNombre", usuarioServicio.buscarPorNombre(nombre));
            modelo.addAttribute("nombre", nombre);
        }

        return "usuario-reportes";
    }
}
