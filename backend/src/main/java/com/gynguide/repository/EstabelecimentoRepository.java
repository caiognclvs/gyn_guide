package com.gynguide.repository;

import com.gynguide.model.Estabelecimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstabelecimentoRepository extends JpaRepository<Estabelecimento, Long> {
    List<Estabelecimento> findByProprietarioId(Long proprietarioId);
    
    @Query("SELECT DISTINCT e FROM Estabelecimento e LEFT JOIN FETCH e.avaliacoes LEFT JOIN FETCH e.proprietario")
    List<Estabelecimento> findAllWithAvaliacoes();
}

