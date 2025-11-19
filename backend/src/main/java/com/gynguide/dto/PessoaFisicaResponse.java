package com.gynguide.dto;

import com.gynguide.model.PessoaFisica;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PessoaFisicaResponse {
    public PessoaFisicaResponse(Long id, String email, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }

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

