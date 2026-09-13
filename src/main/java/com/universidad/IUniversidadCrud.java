package com.universidad;

import org.springframework.data.repository.CrudRepository;
import com.universidad.modelo.Universidad;

public interface IUniversidadCrud extends CrudRepository<Universidad, Long> {
}
