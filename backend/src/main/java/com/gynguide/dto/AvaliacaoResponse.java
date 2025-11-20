package com.gynguide.dto;

import java.time.LocalDateTime;

public class AvaliacaoResponse {
    private Long id;
    private String autorNome;
    private LocalDateTime dataAvaliacao;
    private String texto;
    private Integer nota;

    public AvaliacaoResponse() {}

    public AvaliacaoResponse(Long id, String autorNome, LocalDateTime dataAvaliacao, String texto, Integer nota) {
        this.id = id;
        this.autorNome = autorNome;
        this.dataAvaliacao = dataAvaliacao;
        this.texto = texto;
        this.nota = nota;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAutorNome() {
        return autorNome;
    }

    public void setAutorNome(String autorNome) {
        this.autorNome = autorNome;
    }

    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }
    
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }
}
