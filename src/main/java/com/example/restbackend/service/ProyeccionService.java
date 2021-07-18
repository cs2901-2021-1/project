package com.example.restbackend.service;

import com.example.restbackend.model.proyeccion.Proyeccion;
import com.example.restbackend.model.usuario.Usuario;
import com.example.restbackend.repositories.ProyeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProyeccionService {
    private final ProyeccionRepository proyeccionRepository;

    @Autowired
    public ProyeccionService(ProyeccionRepository proyeccionRepository) {
        this.proyeccionRepository = proyeccionRepository;
    }

    public List<Proyeccion> getProyecciones() {return proyeccionRepository.findAll();}

    public Proyeccion findOneByCurCod(String curCod) {
        Optional<Proyeccion> proyeccionOptional = proyeccionRepository.findProyeccionByCodCur(curCod);
        if (proyeccionOptional.isPresent())
            return proyeccionOptional.get();
        else
            return null;
    }

    public Collection<Proyeccion> findProyeccionByNomCarr(String nomCarr) {
        Collection<Proyeccion> proyeccionOptional = proyeccionRepository.findProyeccionByNomCarr(nomCarr);
        return proyeccionOptional;
    }


}
