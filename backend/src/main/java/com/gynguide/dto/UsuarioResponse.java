package com.gynguide.dto;

import com.gynguide.model.Usuario;
import lombok.Data;

@Data
public class UsuarioResponse {
    public UsuarioResponse(Long id, String email, Usuario.TipoUsuario tipoUsuario) {
        this.id = id;
        this.email = email;
        this.tipoUsuario = tipoUsuario;
    }

    private Long id;
    private String email;
    private Usuario.TipoUsuario tipoUsuario;
    
    public static UsuarioResponse fromUsuario(Usuario usuario) {
        return new UsuarioResponse(
            usuario.getId(),
            usuario.getEmail(),
            usuario.getTipoUsuario()
        );
    }
}

