package com.gynguide.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "estabelecimentos")
public class Estabelecimento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String nome;
    
    @Column(nullable = false)
    private String endereco;
    
    @Column(length = 500)
    private String descricao;
    
    @Column(length = 1000)
    private String imagemUrl;
    
    @ManyToOne
    @JoinColumn(name = "pessoa_juridica_id", nullable = false)
    private PessoaJuridica proprietario;
    
    @OneToMany(mappedBy = "estabelecimento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes;

    public Estabelecimento() {}

    public Estabelecimento(String nome, String endereco, String descricao, String imagemUrl, PessoaJuridica proprietario) {
        this.nome = nome;
        this.endereco = endereco;
        this.descricao = descricao;
        this.imagemUrl = imagemUrl;
        this.proprietario = proprietario;
    }

    public String getImagemUrl() {
        return imagemUrl;
    }
    
    public void setImagemUrl(String imagemUrl) {
        this.imagemUrl = imagemUrl;
    }

    public PessoaJuridica getProprietario() {
        return proprietario;
    }
    
    public void setProprietario(PessoaJuridica proprietario) {
        this.proprietario = proprietario;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }
    
    public void setAvaliacoes(List<Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
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
}

