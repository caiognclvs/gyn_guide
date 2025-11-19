package com.gynguide.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoas_juridicas")
@NoArgsConstructor
@AllArgsConstructor
public class PessoaJuridica extends Usuario {
    
    @Column(nullable = false)
    private String nomeFantasia;
    
    @Column(nullable = false)
    private String razaoSocial;
    
    @Column(nullable = false, unique = true, length = 18)
    private String cnpj;
    
    @Column(nullable = false)
    private String endereco;
    
    // Getters e Setters
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

