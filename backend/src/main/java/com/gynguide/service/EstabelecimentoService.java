package com.gynguide.service;

import com.gynguide.dto.EstabelecimentoResponse;
import com.gynguide.model.Estabelecimento;
import com.gynguide.repository.EstabelecimentoRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoService {
    
    private final EstabelecimentoRepository estabelecimentoRepository;
    
    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
    }
    
    public List<EstabelecimentoResponse> buscarEstabelecimentosAleatorios(int quantidade) {
        List<Estabelecimento> todosEstabelecimentos = estabelecimentoRepository.findAllWithAvaliacoes();
        
        if (todosEstabelecimentos.isEmpty()) {
            return Collections.emptyList();
        }
        
        // Embaralha a lista para garantir aleatoriedade
        Collections.shuffle(todosEstabelecimentos);
        
        // Pega os primeiros N estabelecimentos (ou todos se houver menos que N)
        int quantidadeParaRetornar = Math.min(quantidade, todosEstabelecimentos.size());
        List<Estabelecimento> estabelecimentosSelecionados = 
            todosEstabelecimentos.subList(0, quantidadeParaRetornar);
        
        return estabelecimentosSelecionados.stream()
            .map(this::converterParaResponse)
            .collect(Collectors.toList());
    }
    
    private EstabelecimentoResponse converterParaResponse(Estabelecimento estabelecimento) {
        List<com.gynguide.model.Avaliacao> avaliacoes = estabelecimento.getAvaliacoes();
        
        Double mediaNotas = null;
        Long numeroAvaliacoes = (long) avaliacoes.size();
        
        if (!avaliacoes.isEmpty()) {
            double soma = avaliacoes.stream()
                .mapToInt(com.gynguide.model.Avaliacao::getNota)
                .sum();
            mediaNotas = soma / numeroAvaliacoes;
        }
        
        String nomeFantasia = estabelecimento.getProprietario() != null 
            ? estabelecimento.getProprietario().getNomeFantasia() 
            : null;
        
        return new EstabelecimentoResponse(
            estabelecimento.getId(),
            estabelecimento.getNome(),
            estabelecimento.getEndereco(),
            estabelecimento.getDescricao(),
            estabelecimento.getImagemUrl(),
            nomeFantasia,
            mediaNotas,
            numeroAvaliacoes
        );
    }
}

