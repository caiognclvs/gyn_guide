package com.gynguide.dto;

import com.gynguide.model.PessoaJuridica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridicaResponse {
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

