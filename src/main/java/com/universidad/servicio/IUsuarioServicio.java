package com.universidad.servicio;


import com.universidad.modelo.Usuario;

import java.util.List;

public interface IUsuarioServicio {
    public List<Usuario> listarUsuarios ();
    public void guardarUsuario(Usuario usuario);
    public void eliminarUsuario(Usuario usuario);
    public Usuario buscarUsuario(Usuario usuario);
}
