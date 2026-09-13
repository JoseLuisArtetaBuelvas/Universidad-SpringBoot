package com.universidad.servicio;

import com.universidad.modelo.Universidad;

import java.util.List;

public interface IUniversidadServicio {
    public List<Universidad> listarUniversidades();
    public void guardarUniversidad(Universidad universidad);
    public void eliminarUniversidad(Universidad universidad);
    public Universidad buscarUniversidad(Universidad universidad);
}
