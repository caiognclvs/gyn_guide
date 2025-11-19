package com.gynguide.dto;

public class EstabelecimentoDetalhesResponse {
    private Long id;
    private String nome;
    private String endereco;
    private String descricao;
    private String imagemUrl;
    private String nomeFantasia;
    
    public EstabelecimentoDetalhesResponse() {}
    
    public EstabelecimentoDetalhesResponse(Long id, String nome, String endereco, 
                                          String descricao, String imagemUrl, String nomeFantasia) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.nomeFantasia = nomeFantasia;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }
}

