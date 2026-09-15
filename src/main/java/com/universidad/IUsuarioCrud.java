package com.universidad;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.universidad.modelo.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioCrud extends CrudRepository<Usuario, String> {
    Optional<Usuario> findByEmailIgnoreCase(String email);
    List<Usuario> findByRol(String rol);
    List<Usuario> findByNombreContainingIgnoreCase(String nombre);

    @Query("SELECT DISTINCT u.rol FROM Usuario u ORDER BY u.rol")
    List<String> listarRolesDistintos();
}
