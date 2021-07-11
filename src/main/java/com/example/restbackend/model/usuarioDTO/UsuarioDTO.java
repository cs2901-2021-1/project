package com.example.restbackend.model.usuarioDTO;

//import com.example.restbackend.model.authProvider.AuthProvider;

public class UsuarioDTO {
    private Long idDTO;
    private String nameDTO;
    private String emailDTO;
    private String imageUrlDTO;
    private String roleDTO;
    private String googleIdDTO;
    //private AuthProvider provider;

    public UsuarioDTO() {}
    public UsuarioDTO(Long idDTO, String nameDTO, String emailDTO, String imageUrlDTO, String roleDTO, String googleIdDTO) {
        this.idDTO = idDTO;
        this.nameDTO = nameDTO;
        this.emailDTO = emailDTO;
        this.imageUrlDTO = imageUrlDTO;
        this.roleDTO = roleDTO;
        this.googleIdDTO = googleIdDTO;
    }

    public Long getIdDTO() {
        return idDTO;
    }

    public void setIdDTO(Long idDTO) {
        this.idDTO = idDTO;
    }

    public String getNameDTO() {
        return nameDTO;
    }

    public void setNameDTO(String nameDTO) {
        this.nameDTO = nameDTO;
    }

    public String getEmailDTO() {
        return emailDTO;
    }

    public void setEmailDTO(String emailDTO) {
        this.emailDTO = emailDTO;
    }

    public String getImageUrlDTO() {
        return imageUrlDTO;
    }

    public void setImageUrlDTO(String imageUrlDTO) {
        this.imageUrlDTO = imageUrlDTO;
    }

    public String getRoleDTO() {
        return roleDTO;
    }

    public void setRoleDTO(String roleDTO) {
        this.roleDTO = roleDTO;
    }

    public String getGoogleIdDTO() { return googleIdDTO; }

    public void setGoogleIdDTO(String googleIdDTO) { this.googleIdDTO = googleIdDTO; }
/*
    public AuthProvider getProvider() {
        return provider;
    }

    public void setProvider(AuthProvider provider) {
        this.provider = provider;
    }

 */
}
