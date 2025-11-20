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
import org.springframework.web.multipart.MultipartFile;

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

    @Transactional(readOnly = true)
    public EstabelecimentoDetalhesResponse buscarEstabelecimentoPorId(Long estabelecimentoId) {
        Estabelecimento estabelecimento = estabelecimentoRepository.findById(estabelecimentoId)
            .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        // calcula media e numero de avaliacoes
        java.util.List<com.gynguide.model.Avaliacao> avaliacoes = estabelecimento.getAvaliacoes();
        Long numeroAvaliacoes = (long) avaliacoes.size();
        Double mediaNotas = null;
        if (!avaliacoes.isEmpty()) {
            double soma = avaliacoes.stream().mapToInt(com.gynguide.model.Avaliacao::getNota).sum();
            mediaNotas = soma / numeroAvaliacoes;
        }

        // converte avaliacoes para DTOs ordenadas por data desc
        java.util.List<com.gynguide.dto.AvaliacaoResponse> avaliacoesDto = avaliacoes.stream()
            .sorted((a,b) -> b.getDataAvaliacao().compareTo(a.getDataAvaliacao()))
            .map(a -> new com.gynguide.dto.AvaliacaoResponse(
                a.getId(),
                a.getAutor() != null ? a.getAutor().getNome() : null,
                a.getDataAvaliacao(),
                a.getTexto(),
                a.getNota()
            ))
            .collect(java.util.stream.Collectors.toList());

        String nomeFantasia = estabelecimento.getProprietario() != null
            ? estabelecimento.getProprietario().getNomeFantasia()
            : null;

        return new EstabelecimentoDetalhesResponse(
            estabelecimento.getId(),
            estabelecimento.getNome(),
            estabelecimento.getEndereco(),
            estabelecimento.getDescricao(),
            estabelecimento.getImagemUrl(),
            nomeFantasia,
            mediaNotas,
            numeroAvaliacoes,
            avaliacoesDto
        );
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

    @Transactional
    public EstabelecimentoDetalhesResponse criarOuAtualizarEstabelecimento(Long proprietarioId, EstabelecimentoRequest request, MultipartFile imagem) {
        // Se houver um arquivo enviado, salva no filesystem e define imagemUrl
        if (imagem != null && !imagem.isEmpty()) {
            try {
                // Usa um caminho absoluto baseado em user.dir para evitar trabalhar no diretório temporário do Tomcat
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

                // Copia o conteúdo do input stream para o arquivo destino. Evita problemas de rename entre volumes
                try (InputStream in = imagem.getInputStream()) {
                    Files.copy(in, target, StandardCopyOption.REPLACE_EXISTING);
                }

                // Define a URL pública para acessar o arquivo
                String fileUrl = "http://localhost:8080/uploads/" + filename;
                request.setImagemUrl(fileUrl);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao salvar imagem: " + e.getMessage(), e);
            }
        }

        // Reaproveita o fluxo existente
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
