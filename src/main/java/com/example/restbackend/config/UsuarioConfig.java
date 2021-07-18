package com.example.restbackend.config;


import com.example.restbackend.model.usuario.Usuario;
import com.example.restbackend.repositories.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class UsuarioConfig {
/*
    @Bean
    CommandLineRunner commandLineRunner(UsuarioRepository repository){
        return args -> {
            var uno = new Usuario(
                    "Alex",
                    "alex.loja@utec.edu.pe",
                    "",
                    "ADMIN",
                    "114643212274933077576"
            );
            var dos = new Usuario(
                    "Anthony",
                    "anthony.guimarey@utec.edu.pe",
                    "",
                    "ADMIN",
                    ""
            );
            var tres = new Usuario(
                    "Mateo",
                    "mateo.noel@utec.edu.pe",
                    "",
                    "ADMIN",
                    ""
            );
            repository.saveAll(List.of(uno, dos));
        };
    }
     */

}
