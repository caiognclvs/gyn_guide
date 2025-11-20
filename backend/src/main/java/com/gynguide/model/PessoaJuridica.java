package com.gynguide.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pessoas_juridicas")
public class PessoaJuridica extends Usuario {
    
    @Column(nullable = false)
    private String nomeFantasia;
    
    @Column(nullable = false)
    private String razaoSocial;
    
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;
    
    @Column(nullable = false)
    private String endereco;

    public PessoaJuridica() {}

    public PessoaJuridica(String email, String senha, String nomeFantasia, String razaoSocial, String cnpj, String endereco) {
        super(email, senha);
        this.nomeFantasia = nomeFantasia;
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
        this.endereco = endereco;
    }
    
    public String getNomeFantasia() {
        return nomeFantasia;
    }
    
    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
    
    public String getRazaoSocial() {
        return razaoSocial;
    }
    
    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }
    
    public String getCnpj() {
        return cnpj;
    }
    
    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
}

