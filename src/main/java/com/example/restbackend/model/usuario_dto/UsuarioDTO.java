package com.example.restbackend.model.usuario_dto;

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

    public String getNameDTO() {
        return nameDTO;
    }

    public String getEmailDTO() {
        return emailDTO;
    }

    public String getImageUrlDTO() {
        return imageUrlDTO;
    }

    public String getRoleDTO() {
        return roleDTO;
    }

    public String getGoogleIdDTO() { return googleIdDTO; }

}
