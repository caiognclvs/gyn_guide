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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class PerfilController {
    
    private final PerfilService perfilService;
    
    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }
    
    @GetMapping("/pessoa-fisica/{id}")
    public ResponseEntity<PerfilPessoaFisicaResponse> buscarPerfilPessoaFisica(@PathVariable Long id) {
        PerfilPessoaFisicaResponse response = perfilService.buscarPerfilPessoaFisica(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/pessoa-juridica/{id}")
    public ResponseEntity<PerfilPessoaJuridicaResponse> buscarPerfilPessoaJuridica(@PathVariable Long id) {
        PerfilPessoaJuridicaResponse response = perfilService.buscarPerfilPessoaJuridica(id);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/pessoa-fisica/{id}")
    public ResponseEntity<PerfilPessoaFisicaResponse> atualizarPerfilPessoaFisica(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarPerfilPessoaFisicaRequest request) {
        PerfilPessoaFisicaResponse response = perfilService.atualizarPerfilPessoaFisica(id, request);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/pessoa-juridica/{id}")
    public ResponseEntity<PerfilPessoaJuridicaResponse> atualizarPerfilPessoaJuridica(
            @PathVariable Long id,
            @Valid @RequestBody AtualizarPerfilPessoaJuridicaRequest request) {
        PerfilPessoaJuridicaResponse response = perfilService.atualizarPerfilPessoaJuridica(id, request);
        return ResponseEntity.ok(response);
    }
}

