package com.gynguide.controller;

import com.gynguide.dto.PessoaFisicaResponse;
import com.gynguide.dto.PessoaJuridicaResponse;
import com.gynguide.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class UsuarioController {
    
    private final UsuarioService usuarioService;
    
    @GetMapping("/pessoas-fisicas")
    public ResponseEntity<List<PessoaFisicaResponse>> listarPessoasFisicas() {
        List<PessoaFisicaResponse> pessoas = usuarioService.listarPessoasFisicas();
        return ResponseEntity.ok(pessoas);
    }
    
    @GetMapping("/pessoas-juridicas")
    public ResponseEntity<List<PessoaJuridicaResponse>> listarPessoasJuridicas() {
        List<PessoaJuridicaResponse> pessoas = usuarioService.listarPessoasJuridicas();
        return ResponseEntity.ok(pessoas);
    }
}

