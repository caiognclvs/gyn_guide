package com.gynguide.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "pessoas_fisicas")
public class PessoaFisica extends Usuario {
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private LocalDate dataNascimento;

    public PessoaFisica() {}
    
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

