package com.gynguide.service;

import com.gynguide.dto.EstabelecimentoDetalhesResponse;
import com.gynguide.dto.EstabelecimentoRequest;
import com.gynguide.dto.EstabelecimentoResponse;
import com.gynguide.model.Estabelecimento;
import com.gynguide.model.PessoaJuridica;
import com.gynguide.repository.EstabelecimentoRepository;
import com.gynguide.repository.PessoaJuridicaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstabelecimentoService {
    
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    
    public EstabelecimentoService(EstabelecimentoRepository estabelecimentoRepository,
                                 PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }
    
    @Transactional(readOnly = true)
    public List<EstabelecimentoResponse> buscarEstabelecimentosAleatorios(int quantidade) {
        List<Estabelecimento> todosEstabelecimentos = estabelecimentoRepository.findAllWithAvaliacoesAndProprietario();
        
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
    
    public EstabelecimentoDetalhesResponse buscarEstabelecimentoPorProprietario(Long proprietarioId) {
        List<Estabelecimento> estabelecimentos = estabelecimentoRepository.findByProprietarioId(proprietarioId);
        
        if (estabelecimentos.isEmpty()) {
            return null; // Não tem estabelecimento cadastrado ainda
        }
        
        // Retorna o primeiro estabelecimento (assumindo que cada pessoa jurídica tem apenas um)
        Estabelecimento estabelecimento = estabelecimentos.get(0);
        return converterParaDetalhesResponse(estabelecimento);
    }
    
    @Transactional
    public EstabelecimentoDetalhesResponse criarOuAtualizarEstabelecimento(Long proprietarioId, EstabelecimentoRequest request) {
        PessoaJuridica proprietario = pessoaJuridicaRepository.findById(proprietarioId)
            .orElseThrow(() -> new RuntimeException("Pessoa jurídica não encontrada"));
        
        List<Estabelecimento> estabelecimentosExistentes = estabelecimentoRepository.findByProprietarioId(proprietarioId);
        
        Estabelecimento estabelecimento;
        
        if (estabelecimentosExistentes.isEmpty()) {
            // Criar novo estabelecimento
            estabelecimento = new Estabelecimento();
            estabelecimento.setProprietario(proprietario);
        } else {
            // Atualizar estabelecimento existente
            estabelecimento = estabelecimentosExistentes.get(0);
        }
        
        estabelecimento.setNome(request.getNome());
        estabelecimento.setEndereco(request.getEndereco());
        estabelecimento.setDescricao(request.getDescricao());
        estabelecimento.setImagemUrl(request.getImagemUrl());
        
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
        
        return converterParaDetalhesResponse(estabelecimento);
    }
    
    private EstabelecimentoDetalhesResponse converterParaDetalhesResponse(Estabelecimento estabelecimento) {
        String nomeFantasia = estabelecimento.getProprietario() != null 
            ? estabelecimento.getProprietario().getNomeFantasia() 
            : null;
        
        return new EstabelecimentoDetalhesResponse(
            estabelecimento.getId(),
            estabelecimento.getNome(),
            estabelecimento.getEndereco(),
            estabelecimento.getDescricao(),
            estabelecimento.getImagemUrl(),
            nomeFantasia
        );
    }
}
