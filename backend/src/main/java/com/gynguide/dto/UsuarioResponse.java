package com.gynguide.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gynguide.model.Usuario;

public class UsuarioResponse {
    private Long id;
    private String email;
    private Usuario.TipoUsuario tipoUsuario;
    
    public UsuarioResponse() {}
    
    public UsuarioResponse(Long id, String email, Usuario.TipoUsuario tipoUsuario) {
        this.id = id;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @JsonProperty("tipoUsuario")
    public Usuario.TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }
    
    public void setTipoUsuario(Usuario.TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
    
    public static UsuarioResponse fromUsuario(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getTipoUsuario()
        );
    }
}

