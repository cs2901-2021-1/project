package com.example.restbackend.config;

import com.example.restbackend.model.proyeccion.Proyeccion;
import com.example.restbackend.repositories.ProyeccionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ProyeccionConfig {
/*
    @Bean
    CommandLineRunner commandLineRunnerP(ProyeccionRepository proyeccionRepository){

        return args -> {
            var uno = new Proyeccion(
                    "Ciencia de la Computación",
                    "CS02-21",
                    "Ing. Software",
                    25
            );
            var dos = new Proyeccion(
                    "Ciencia de la Computación",
                    "CS02-22",
                    "ADA",
                    40
            );
            proyeccionRepository.saveAll(List.of(uno,dos));
        };
    }
 */
}
