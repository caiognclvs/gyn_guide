package com.gynguide.dto;

import java.time.LocalDate;

import com.gynguide.model.PessoaFisica;

public class PessoaFisicaResponse {
    private Long id;
    private String email;
    private String nome;
    private LocalDate dataNascimento;

    public PessoaFisicaResponse() {}

    public PessoaFisicaResponse(Long id, String email, String nome, LocalDate dataNascimento) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public static PessoaFisicaResponse fromPessoaFisica(PessoaFisica pessoaFisica) {
        return new PessoaFisicaResponse(
            pessoaFisica.getId(),
            pessoaFisica.getEmail(),
            pessoaFisica.getNome(),
            pessoaFisica.getDataNascimento()
        );
    }
}
