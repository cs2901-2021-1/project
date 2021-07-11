package com.example.restbackend.model.usuario;

//import com.example.restbackend.model.authProvider.AuthProvider;
import com.example.restbackend.model.usuarioDTO.UsuarioDTO;

import javax.persistence.*;

@Entity
@Table
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = true)
    private String imageUrl;
    @Column(nullable = true)
    private String role;
    @Column
    private String googleId;

    //private AuthProvider provider;

    public Usuario(){}

    public Usuario( String name, String email, String imageUrl, String role, String googleId) {
        this.name = name;
        this.email = email;
        this.imageUrl = imageUrl;
        this.role = role;
        this.googleId = googleId;
    }

    public Usuario(UsuarioDTO userDTO){
        this.id = userDTO.getIdDTO();
        this.name = userDTO.getEmailDTO();
        this.email = userDTO.getEmailDTO();
        this.imageUrl = userDTO.getImageUrlDTO();
        this.role = userDTO.getRoleDTO();
        this.googleId = userDTO.getGoogleIdDTO();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }
/*
    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }
*/
    @Override
    public String toString() {
        return "Persona : " + "id = " + id + ", nombre =  " + name + ", email = " + email + " role: " + role;
    }


}
