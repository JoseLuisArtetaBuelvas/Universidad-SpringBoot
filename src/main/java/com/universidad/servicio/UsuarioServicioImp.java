package com.universidad.servicio;

import com.universidad.IUsuarioCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.universidad.modelo.Usuario;
import java.util.List;

@Service
public class UsuarioServicioImp implements IUsuarioServicio {

    @Autowired
    private IUsuarioCrud usuarioCrud;

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> listarUsuarios() {
        return (List<Usuario>) usuarioCrud.findAll();
    }

    @Transactional
    @Override
    public void guardarUsuario(Usuario usuario) {
        usuarioCrud.save(usuario);
    }

    @Transactional
    @Override
    public void eliminarUsuario(Usuario usuario) {
        if (usuario != null && usuario.getId() != null) {
            usuarioCrud.deleteById(usuario.getId());
        } else if (usuario != null) {
            usuarioCrud.delete(usuario);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Usuario buscarUsuario(Usuario usuario) {
        return usuarioCrud.findById(usuario.getId()).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> buscarPorRol(String rol) {
        return usuarioCrud.findByRol(rol);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Usuario> buscarPorNombre(String nombre) {
        return usuarioCrud.findByNombreContainingIgnoreCase(nombre);
    }
}
