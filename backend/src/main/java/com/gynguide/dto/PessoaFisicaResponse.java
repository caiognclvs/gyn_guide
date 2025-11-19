package com.gynguide.dto;

import com.gynguide.model.PessoaFisica;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PessoaFisicaResponse {
    private Long id;
    private String email;
    private String nome;
    private LocalDate dataNascimento;
    
    public static PessoaFisicaResponse fromPessoaFisica(PessoaFisica pessoaFisica) {
        return new PessoaFisicaResponse(
            pessoaFisica.getId(),
            pessoaFisica.getEmail(),
            pessoaFisica.getNome(),
            pessoaFisica.getDataNascimento()
        );
    }
}

