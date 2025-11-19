package com.gynguide.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoas_juridicas")
@Data
@EqualsAndHashCode(callSuper = true)
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
}

