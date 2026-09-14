package com.universidad;

import org.springframework.data.repository.CrudRepository;
import com.universidad.modelo.TokenRecuperacion;

public interface ITokenRecuperacionCrud extends CrudRepository<TokenRecuperacion, String> {
}
