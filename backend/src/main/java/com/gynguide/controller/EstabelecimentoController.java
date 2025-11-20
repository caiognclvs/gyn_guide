package com.gynguide.controller;

import com.gynguide.dto.EstabelecimentoDetalhesResponse;
import com.gynguide.dto.EstabelecimentoRequest;
import com.gynguide.dto.EstabelecimentoResponse;
import com.gynguide.service.EstabelecimentoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/estabelecimentos")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
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

    @GetMapping("/todos")
    public ResponseEntity<List<EstabelecimentoResponse>> listarTodosOrdenados() {
        List<EstabelecimentoResponse> estabelecimentos = estabelecimentoService.buscarTodosOrdenadosPorNome();
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

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarEstabelecimentoPorId(@PathVariable Long id) {
        try {
            EstabelecimentoDetalhesResponse response = estabelecimentoService.buscarEstabelecimentoPorId(id);
            if (response == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Estabelecimento não encontrado");
            }
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    
    @PostMapping(value = "/meu-estabelecimento/{proprietarioId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> criarOuAtualizarEstabelecimento(
            @PathVariable Long proprietarioId,
            @RequestParam("nome") String nome,
            @RequestParam("endereco") String endereco,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "imagemUrl", required = false) String imagemUrl,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem) {
        try {
            EstabelecimentoRequest request = new EstabelecimentoRequest(nome, endereco, descricao, imagemUrl);
            EstabelecimentoDetalhesResponse response = estabelecimentoService.criarOuAtualizarEstabelecimento(proprietarioId, request, imagem);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping(value = "/meu-estabelecimento/{proprietarioId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> atualizarEstabelecimento(
            @PathVariable Long proprietarioId,
            @RequestParam("nome") String nome,
            @RequestParam("endereco") String endereco,
            @RequestParam(value = "descricao", required = false) String descricao,
            @RequestParam(value = "imagemUrl", required = false) String imagemUrl,
            @RequestParam(value = "imagem", required = false) MultipartFile imagem) {
        try {
            EstabelecimentoRequest request = new EstabelecimentoRequest(nome, endereco, descricao, imagemUrl);
            EstabelecimentoDetalhesResponse response = estabelecimentoService.criarOuAtualizarEstabelecimento(proprietarioId, request, imagem);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
