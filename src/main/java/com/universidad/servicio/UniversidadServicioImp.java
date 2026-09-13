package com.universidad.servicio;

import com.universidad.IUniversidadCrud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.universidad.modelo.Universidad;
import java.util.List;

@Service
public class UniversidadServicioImp implements IUniversidadServicio {

    @Autowired
    private IUniversidadCrud universidadCrud;

    @Transactional(readOnly = true)
    @Override
    public List<Universidad> listarUniversidades() {
        return (List<Universidad>) universidadCrud.findAll();
    }

    @Transactional
    @Override
    public void guardarUniversidad(Universidad universidad) {
        universidadCrud.save(universidad);
    }

    @Transactional
    @Override
    public void eliminarUniversidad(Universidad universidad) {
        if (universidad != null && universidad.getId() != null) {
            universidadCrud.deleteById(universidad.getId());
        } else if (universidad != null) {
            universidadCrud.delete(universidad);
        }
    }

    @Transactional(readOnly = true)
    @Override
    public Universidad buscarUniversidad(Universidad universidad) {
        return universidadCrud.findById(universidad.getId()).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Universidad> buscarPorCiudad(String ciudad) {
        return universidadCrud.findByCiudadIgnoreCase(ciudad);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Universidad> buscarPorCategoriaYSedesMinimas(String categoria, Integer numSedes) {
        return universidadCrud.findByCategoriaIgnoreCaseAndNumSedesGreaterThanEqual(categoria, numSedes);
    }
}
