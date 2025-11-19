package com.gynguide.dto;

import jakarta.validation.constraints.NotBlank;

public class EstabelecimentoRequest {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Endereço é obrigatório")
    private String endereco;
    
    private String descricao;
    
    private String imagemUrl;

    public EstabelecimentoRequest() {}

    public EstabelecimentoRequest(String nome, String endereco, String descricao, String imagemUrl) {
        this.nome = nome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }
}

