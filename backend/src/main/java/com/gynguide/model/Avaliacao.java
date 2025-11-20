package com.gynguide.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "avaliacoes")
public class Avaliacao {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 2000)
    private String texto;
    
    @Column(nullable = false)
    @Min(1)
    @Max(5)
    @NotNull
    private Integer nota;
    
    @Column(nullable = false)
    private LocalDateTime dataAvaliacao;
    
    @ManyToOne
    @JoinColumn(name = "pessoa_fisica_id", nullable = false)
    private PessoaFisica autor;
    
    @ManyToOne
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimento estabelecimento;
    
    @PrePersist
    protected void onCreate() {
        dataAvaliacao = LocalDateTime.now();
    }

    public Avaliacao() {}

    public Avaliacao(String texto, Integer nota, PessoaFisica autor, Estabelecimento estabelecimento) {
        this.texto = texto;
        this.nota = nota;
        this.autor = autor;
        this.estabelecimento = estabelecimento;
    }

    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public LocalDateTime getDataAvaliacao() {
        return dataAvaliacao;
    }
    
    public void setDataAvaliacao(LocalDateTime dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
    
    public PessoaFisica getAutor() {
        return autor;
    }
    
    public void setAutor(PessoaFisica autor) {
        this.autor = autor;
    }
    
    public Estabelecimento getEstabelecimento() {
        return estabelecimento;
    }
    
    public void setEstabelecimento(Estabelecimento estabelecimento) {
        this.estabelecimento = estabelecimento;
    }
}
