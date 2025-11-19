package com.gynguide.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CadastroPessoaJuridicaRequest {
    
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;
    
    @NotBlank(message = "Senha é obrigatória")
    private String senha;
    
    @NotBlank(message = "Nome fantasia é obrigatório")
    private String nomeFantasia;
    
    @NotBlank(message = "Razão social é obrigatória")
    private String razaoSocial;
    
    @NotBlank(message = "CNPJ é obrigatório")
    private String cnpj;
    
    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;
}

