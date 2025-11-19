package com.gynguide.controller;

import com.gynguide.dto.EstabelecimentoResponse;
import com.gynguide.service.EstabelecimentoService;
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
}

