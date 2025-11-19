package com.gynguide.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PerfilPessoaFisicaResponse {
    private Long id;
    private String email;
    private String nome;
    private LocalDate dataNascimento;
    
    public PerfilPessoaFisicaResponse() {}
    
    public PerfilPessoaFisicaResponse(Long id, String email, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
    }
}

