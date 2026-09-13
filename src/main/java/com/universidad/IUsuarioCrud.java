package com.universidad;

import org.springframework.data.repository.CrudRepository;
import com.universidad.modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioCrud extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByEmail(String email);
    List<Usuario> findByRol(String rol);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);
}
