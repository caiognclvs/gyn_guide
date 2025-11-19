package com.gynguide.controller;

import com.gynguide.dto.AtualizarPerfilPessoaFisicaRequest;
import com.gynguide.dto.AtualizarPerfilPessoaJuridicaRequest;
import com.gynguide.dto.PerfilPessoaFisicaResponse;
import com.gynguide.dto.PerfilPessoaJuridicaResponse;
import com.gynguide.service.PerfilService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/perfil")
@CrossOrigin(origins = "http://localhost:3000")
public class PerfilController {
    
    private final PerfilService perfilService;
    
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }
    
    @GetMapping("/pessoa-fisica/{id}")
    public ResponseEntity<?> buscarPerfilPessoaFisica(@PathVariable Long id) {
        try {
            PerfilPessoaFisicaResponse response = perfilService.buscarPerfilPessoaFisica(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @GetMapping("/pessoa-juridica/{id}")
    public ResponseEntity<?> buscarPerfilPessoaJuridica(@PathVariable Long id) {
        try {
            PerfilPessoaJuridicaResponse response = perfilService.buscarPerfilPessoaJuridica(id);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PutMapping("/pessoa-fisica/{id}")
    public ResponseEntity<?> atualizarPerfilPessoaFisica(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarPerfilPessoaFisicaRequest request) {
        try {
            PerfilPessoaFisicaResponse response = perfilService.atualizarPerfilPessoaFisica(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PutMapping("/pessoa-juridica/{id}")
    public ResponseEntity<?> atualizarPerfilPessoaJuridica(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarPerfilPessoaJuridicaRequest request) {
        try {
            PerfilPessoaJuridicaResponse response = perfilService.atualizarPerfilPessoaJuridica(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}

