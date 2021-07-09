package com.example.restbackend.model.usuario;

import com.example.restbackend.model.usuarioDTO.UsuarioDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/admin")
public class AdminController {
    static final Logger logger = Logger.getLogger(AdminController.class.getName());
    private final UsuarioService usuarioService;

    @Autowired
    public AdminController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public void registerNewUsuario(@RequestBody UsuarioDTO userDTO) {
        var user = new Usuario(userDTO);
        usuarioService.addNewUsuario(user);
    }

    @DeleteMapping(path = "/deleteByEmail/{userEmail}")
    public void deleteUsuarioByEmail(@PathVariable("userEmail") String userEmail ){
        usuarioService.deleteUsuario(userEmail);
    }
}
