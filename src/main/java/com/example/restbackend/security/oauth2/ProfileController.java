package com.example.restbackend.security.oauth2;

import com.example.restbackend.model.usuario.Usuario;
import com.example.restbackend.model.usuario.UsuarioService;
import com.example.restbackend.security.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProfileController {
    @Autowired
    private UsuarioService usuarioService;

    @RequestMapping("/profile")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    //@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Usuario currentUser(@CurrentUser UserPrincipal userPrincipal){
        return usuarioService.findOneByGoogleId(userPrincipal.getGoogleId());
    }
/*
    @RequestMapping("/login")
    public Jwt login(@RequestBody LoginBody login) {
        String accessToken = login.getAccessToken();
        String email - login.getEmail();
        request "https://oauth2.googleapis.com/tokeninfo?access_token=" + accessToken

    }
    */

}
