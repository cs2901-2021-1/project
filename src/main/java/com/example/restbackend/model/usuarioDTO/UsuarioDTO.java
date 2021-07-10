package com.example.restbackend.model.usuariodto;

public class UsuarioDTO {
    private Long idDTO;
    private String nameDTO;
    private String emailDTO;
    private String imageUrlDTO;
    private String roleDTO;
    private String googleIdDTO;

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

}
