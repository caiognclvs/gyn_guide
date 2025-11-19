package com.gynguide.controller;

import com.gynguide.dto.EstabelecimentoDetalhesResponse;
import com.gynguide.dto.EstabelecimentoRequest;
import com.gynguide.dto.EstabelecimentoResponse;
import com.gynguide.service.EstabelecimentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/estabelecimentos")
@CrossOrigin(origins = "http://localhost:3000")
public class EstabelecimentoController {
    
    private final EstabelecimentoService estabelecimentoService;
    
    public EstabelecimentoController(EstabelecimentoService estabelecimentoService) {
        this.estabelecimentoService = estabelecimentoService;
    }
    
    @GetMapping("/aleatorios")
    public ResponseEntity<List<EstabelecimentoResponse>> buscarEstabelecimentosAleatorios(
            @RequestParam(defaultValue = "3") int quantidade) {
        List<EstabelecimentoResponse> estabelecimentos = 
            estabelecimentoService.buscarEstabelecimentosAleatorios(quantidade);
        return ResponseEntity.ok(estabelecimentos);
    }
    
    @GetMapping("/meu-estabelecimento/{proprietarioId}")
    public ResponseEntity<?> buscarMeuEstabelecimento(@PathVariable Long proprietarioId) {
        try {
            EstabelecimentoDetalhesResponse response = estabelecimentoService.buscarEstabelecimentoPorProprietario(proprietarioId);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estabelecimento não encontrado");
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping("/meu-estabelecimento/{proprietarioId}")
    public ResponseEntity<?> criarOuAtualizarEstabelecimento(
            @PathVariable Long proprietarioId,
            @Valid @RequestBody EstabelecimentoRequest request) {
        try {
            EstabelecimentoDetalhesResponse response = estabelecimentoService.criarOuAtualizarEstabelecimento(proprietarioId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/meu-estabelecimento/{proprietarioId}")
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long proprietarioId,
            @Valid @RequestBody EstabelecimentoRequest request) {
        try {
            EstabelecimentoDetalhesResponse response = estabelecimentoService.criarOuAtualizarEstabelecimento(proprietarioId, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

