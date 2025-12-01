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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/cadastro/pessoa-fisica")
    public ResponseEntity<UsuarioResponse> cadastrarPessoaFisica(@Valid @RequestBody CadastroPessoaFisicaRequest request) {
        UsuarioResponse response = authService.cadastrarPessoaFisica(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/cadastro/pessoa-juridica")
    public ResponseEntity<UsuarioResponse> cadastrarPessoaJuridica(@Valid @RequestBody CadastroPessoaJuridicaRequest request) {
        UsuarioResponse response = authService.cadastrarPessoaJuridica(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping(value = "/login", produces = "application/json", consumes = "application/json")
    public ResponseEntity<UsuarioResponse> login(@Valid @RequestBody LoginRequest request) {
        UsuarioResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}

