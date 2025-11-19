package com.gynguide.repository;

import com.gynguide.model.Avaliacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Long> {
    List<Avaliacao> findByEstabelecimentoId(Long estabelecimentoId);
    List<Avaliacao> findByAutorId(Long autorId);
}

