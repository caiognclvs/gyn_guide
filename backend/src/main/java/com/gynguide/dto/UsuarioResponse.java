package com.gynguide.dto;

import com.gynguide.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponse {
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

