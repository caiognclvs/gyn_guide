package com.gynguide.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "avaliacoes")
@Data
@NoArgsConstructor
@AllArgsConstructor
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
}

