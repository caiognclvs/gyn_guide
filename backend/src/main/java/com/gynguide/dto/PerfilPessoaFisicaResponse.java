package com.gynguide.dto;

import java.time.LocalDate;

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
}

