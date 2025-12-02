package com.gynguide.service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gynguide.dto.EstabelecimentoDetalhesResponse;
import com.gynguide.dto.EstabelecimentoRequest;
import com.gynguide.dto.EstabelecimentoResponse;
import com.gynguide.exception.ArquivoException;
import com.gynguide.exception.UsuarioNaoEncontradoException;
import com.gynguide.model.Estabelecimento;
import com.gynguide.model.PessoaJuridica;
import com.gynguide.repository.EstabelecimentoRepository;
import com.gynguide.repository.PessoaJuridicaRepository;

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
        
        Collections.shuffle(todosEstabelecimentos);
        
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
            return null;
        }
        
        Estabelecimento estabelecimento = estabelecimentos.get(0);
        return converterParaDetalhesResponse(estabelecimento);
    }

    @Transactional(readOnly = true)
    public EstabelecimentoDetalhesResponse buscarEstabelecimentoPorId(Long id) {
        return estabelecimentoRepository.findById(id)
                .map(this::converterParaDetalhesResponse)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<EstabelecimentoResponse> buscarTodosOrdenadosPorNome() {
        List<Estabelecimento> todos = estabelecimentoRepository.findAllWithAvaliacoesAndProprietario();
        return todos.stream()
                .sorted((a, b) -> {
                    if (a.getNome() == null) return -1;
                    if (b.getNome() == null) return 1;
                    return a.getNome().compareToIgnoreCase(b.getNome());
                })
                .map(this::converterParaResponse)
                .collect(Collectors.toList());
    }
    
    @Transactional
    public EstabelecimentoDetalhesResponse criarOuAtualizarEstabelecimento(Long proprietarioId, EstabelecimentoRequest request) {
        PessoaJuridica proprietario = pessoaJuridicaRepository.findById(proprietarioId)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(proprietarioId));
        
        List<Estabelecimento> estabelecimentosExistentes = estabelecimentoRepository.findByProprietarioId(proprietarioId);
        
        Estabelecimento estabelecimento;
        
        if (estabelecimentosExistentes.isEmpty()) {
            estabelecimento = new Estabelecimento();
            estabelecimento.setProprietario(proprietario);
        } else {
            estabelecimento = estabelecimentosExistentes.get(0);
        }
        
        estabelecimento.setNome(request.getNome());
        estabelecimento.setEndereco(request.getEndereco());
        estabelecimento.setDescricao(request.getDescricao());
        estabelecimento.setImagemUrl(request.getImagemUrl());
        
        estabelecimento = estabelecimentoRepository.save(estabelecimento);
        
        return converterParaDetalhesResponse(estabelecimento);
    }

    @Transactional
    public EstabelecimentoDetalhesResponse criarOuAtualizarEstabelecimento(Long proprietarioId, EstabelecimentoRequest request, MultipartFile imagem) {
        if (imagem != null && !imagem.isEmpty()) {
            try {
                Path uploadsDir = Paths.get(System.getProperty("user.dir"), "data", "uploads").toAbsolutePath().normalize();
                if (!Files.exists(uploadsDir)) {
                    Files.createDirectories(uploadsDir);
                }

                String original = imagem.getOriginalFilename();
                String extension = "";
                if (original != null && original.contains(".")) {
                    extension = original.substring(original.lastIndexOf('.'));
                }
                String filename = UUID.randomUUID().toString() + extension;
                Path target = uploadsDir.resolve(filename);

                try (InputStream in = imagem.getInputStream()) {
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                }

                String fileUrl = "http://localhost:8080/uploads/" + filename;
                request.setImagemUrl(fileUrl);
            } catch (IOException e) {
                throw new ArquivoException("Erro ao salvar imagem: " + e.getMessage(), e);
            }
        }

        return criarOuAtualizarEstabelecimento(proprietarioId, request);
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