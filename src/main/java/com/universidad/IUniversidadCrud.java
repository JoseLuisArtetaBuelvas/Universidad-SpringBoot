package com.universidad;

import org.springframework.data.repository.CrudRepository;
import com.universidad.modelo.Universidad;

import java.util.List;

public interface IUniversidadCrud extends CrudRepository<Universidad, Long> {
    List<Universidad> findByCiudadIgnoreCase(String ciudad);
    List<Universidad> findByCategoriaIgnoreCaseAndNumSedesGreaterThanEqual(String categoria, Integer numSedes);
}
