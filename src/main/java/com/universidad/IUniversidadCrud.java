package com.universidad;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.universidad.modelo.Universidad;

import java.util.List;

public interface IUniversidadCrud extends CrudRepository<Universidad, Long> {
    List<Universidad> findByCiudadIgnoreCase(String ciudad);
    List<Universidad> findByCategoriaIgnoreCaseAndNumSedesGreaterThanEqual(String categoria, Integer numSedes);

    @Query("SELECT DISTINCT u.ciudad FROM Universidad u ORDER BY u.ciudad")
    List<String> listarCiudadesDistintas();

    @Query("SELECT DISTINCT u.categoria FROM Universidad u ORDER BY u.categoria")
    List<String> listarCategoriasDistintas();
}
