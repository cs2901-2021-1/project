package com.example.restbackend.security.oauth2;

import com.example.restbackend.model.usuario.Usuario;
import com.example.restbackend.model.usuario.UsuarioService;
import com.example.restbackend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping("/profile")
    public Usuario currentUser(Authentication authentication){
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        return usuarioService.findOneByGoogleId(userPrincipal.getGoogleId());
    }
}
