package com.example.restbackend.service;

import com.example.restbackend.model.proyeccion.Proyeccion;
import com.example.restbackend.repositories.ProyeccionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
public class ProyeccionService {
    private final ProyeccionRepository proyeccionRepository;

    @Autowired
    public ProyeccionService(ProyeccionRepository proyeccionRepository) {
        this.proyeccionRepository = proyeccionRepository;
    }

    public List<Proyeccion> getProyecciones() {
        getCursos();
        return proyeccionRepository.findAll();
    }

    public Proyeccion findOneByCursoCod(String cursoCod) {
        Optional<Proyeccion> proyeccionOptional = proyeccionRepository.findProyeccionByCursoCod(cursoCod);
        return proyeccionOptional.orElse(null);
    }

    private void getCursos(){
        WebClient webClient = WebClient.create("https://serene-lowlands-43370.herokuapp.com");
        List<Proyeccion> proyecciones = webClient.get()
                .uri("/courses?period=402")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Proyeccion>>() {
                })
                .block();
        assert proyecciones != null;
        for(var p : proyecciones){
            var proyeccionOptional = proyeccionRepository.findProyeccionByCursoId(p.getCursoId());
            if(proyeccionOptional.isPresent()){
                var pro = proyeccionOptional.get();
                pro.setCursoCod(p.getCursoCod());
                pro.setAreaFunDesc(p.getAreaFunDesc());
                pro.setCursoDesc(p.getCursoDesc());
                pro.setAreaFunId(p.getAreaFunId());
                proyeccionRepository.save(pro);
            }else {
                proyeccionRepository.save(p);
            }
        }
    }

}
