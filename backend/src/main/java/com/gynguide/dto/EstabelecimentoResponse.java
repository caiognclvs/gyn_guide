package com.gynguide.dto;

import lombok.Data;

@Data
public class EstabelecimentoResponse {
    private Long id;
    private String nome;
    private String endereco;
    private String descricao;
    private String imagemUrl;
    private String nomeFantasia;
    private Double mediaNotas;
    private Long numeroAvaliacoes;
    
    public EstabelecimentoResponse(Long id, String nome, String endereco, String descricao, 
                                   String imagemUrl, String nomeFantasia, Double mediaNotas, 
                                   Long numeroAvaliacoes) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.nomeFantasia = nomeFantasia;
        this.mediaNotas = mediaNotas;
        this.numeroAvaliacoes = numeroAvaliacoes;
    }
}

