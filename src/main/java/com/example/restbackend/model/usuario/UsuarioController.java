package com.example.restbackend.model.usuario;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.logging.Logger;

@RestController
@PreAuthorize("hasAnyRole('ADMIN','USER')")
@RequestMapping(path = "/user")
public class UsuarioController {
    static final Logger logger = Logger.getLogger(UsuarioController.class.getName());
    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @GetMapping()
    public List<Usuario> getUsuarios() {
        return usuarioService.getUsuarios();
    }

    @GetMapping(path = "/existByEmail/{email}")
    public Boolean getUsuarioByEmail(@PathVariable String email){
        var user = usuarioService.findOneByEmail(email);
        return user != null;
    }

    @GetMapping(path = "/existById/{googleId}")
    public Boolean getUsuarioByGoogleId(@PathVariable String googleId){
        var user = usuarioService.findOneByGoogleId(googleId);
        return user != null;
    }

    @GetMapping(path = "/roleById/{googleId}")
    public String getUsuarioRolByGoogleId(@PathVariable String googleId){
        var user = usuarioService.findOneByGoogleId(googleId);
        return user.getRole();
    }

    @GetMapping(path = "/roleByEmail/{email}")
    public String getUsuarioRolByEmail(@PathVariable String email){
        var user = usuarioService.findOneByEmail(email);
        return user.getRole();
    }

}

