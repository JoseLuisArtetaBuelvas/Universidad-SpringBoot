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
import org.springframework.web.bind.annotation.PostMapping;


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
    public String guardar(Usuario usuario) {
        usuarioServicio.guardarUsuario(usuario);
        return "redirect:/";
    }

    @GetMapping("/modificar/{id}")
    public String modificar(Usuario usuario, Model modelo) {
        log.info("Modificando usuario: " + usuario);
        usuario = usuarioServicio.buscarUsuario(usuario);
        modelo.addAttribute("usuario", usuario);
        return "modificar";
    }
}
