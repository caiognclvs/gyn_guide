package com.gynguide.controller;

import com.gynguide.dto.AvaliacaoResponse;
import com.gynguide.model.Avaliacao;
import com.gynguide.repository.AvaliacaoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AvaliacaoController {

    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoController(AvaliacaoRepository avaliacaoRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
    }

    @GetMapping("/minhas/{autorId}")
    public ResponseEntity<List<AvaliacaoResponse>> minhasAvaliacoes(@PathVariable Long autorId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByAutorId(autorId);

        List<AvaliacaoResponse> responses = avaliacoes.stream()
                .sorted((a, b) -> b.getDataAvaliacao().compareTo(a.getDataAvaliacao()))
                .map(a -> new AvaliacaoResponse(
                        a.getId(),
                        a.getAutor() != null ? a.getAutor().getNome() : null,
                        a.getDataAvaliacao(),
                        a.getTexto(),
                        a.getNota()
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }
}
