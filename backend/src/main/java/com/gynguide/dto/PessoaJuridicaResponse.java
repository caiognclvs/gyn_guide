package com.gynguide.dto;

import com.gynguide.model.PessoaJuridica;
import lombok.Data;

@Data
public class PessoaJuridicaResponse {
    public PessoaJuridicaResponse(Long id, String email, String nomeFantasia, String razaoSocial, String cnpj, String endereco) {
        this.id = id;
        this.email = email;
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }
    
    private Long id;
    private String email;
    private String nomeFantasia;
    private String razaoSocial;
    private String cnpj;
    private String endereco;
    
    public static PessoaJuridicaResponse fromPessoaJuridica(PessoaJuridica pessoaJuridica) {
        return new PessoaJuridicaResponse(
            pessoaJuridica.getId(),
            pessoaJuridica.getEmail(),
            pessoaJuridica.getNomeFantasia(),
            pessoaJuridica.getRazaoSocial(),
            pessoaJuridica.getCnpj(),
            pessoaJuridica.getEndereco()
        );
    }
}

