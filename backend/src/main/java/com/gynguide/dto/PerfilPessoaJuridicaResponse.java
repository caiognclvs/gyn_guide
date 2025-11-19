package com.gynguide.dto;

import lombok.Data;

@Data
public class PerfilPessoaJuridicaResponse {
    private Long id;
    private String email;
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String endereco;
    
    public PerfilPessoaJuridicaResponse() {}
    
    public PerfilPessoaJuridicaResponse(Long id, String email, String nomeFantasia, 
                                       String razaoSocial, String cnpj, String endereco) {
        this.id = id;
        this.email = email;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }
}

