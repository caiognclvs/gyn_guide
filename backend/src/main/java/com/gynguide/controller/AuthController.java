package com.gynguide.controller;

import com.gynguide.dto.CadastroPessoaFisicaRequest;
import com.gynguide.dto.CadastroPessoaJuridicaRequest;
import com.gynguide.dto.LoginRequest;
import com.gynguide.dto.UsuarioResponse;
import com.gynguide.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro/pessoa-fisica")
    public ResponseEntity<?> cadastrarPessoaFisica(@Valid @RequestBody CadastroPessoaFisicaRequest request) {
        try {
            UsuarioResponse response = authService.cadastrarPessoaFisica(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/cadastro/pessoa-juridica")
    public ResponseEntity<?> cadastrarPessoaJuridica(@Valid @RequestBody CadastroPessoaJuridicaRequest request) {
        try {
            UsuarioResponse response = authService.cadastrarPessoaJuridica(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            UsuarioResponse response = authService.login(request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}

