package com.gynguide.controller;

import com.gynguide.dto.AvaliacaoResponse;
import com.gynguide.model.Avaliacao;
import com.gynguide.model.Estabelecimento;
import com.gynguide.model.PessoaFisica;
import com.gynguide.repository.AvaliacaoRepository;
import com.gynguide.repository.EstabelecimentoRepository;
import com.gynguide.repository.PessoaFisicaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/avaliacoes")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173"})
public class AvaliacaoController {

    private final AvaliacaoRepository avaliacaoRepository;
    private final EstabelecimentoRepository estabelecimentoRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;

    public AvaliacaoController(AvaliacaoRepository avaliacaoRepository,
                               EstabelecimentoRepository estabelecimentoRepository,
                               PessoaFisicaRepository pessoaFisicaRepository) {
        this.avaliacaoRepository = avaliacaoRepository;
        this.estabelecimentoRepository = estabelecimentoRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
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

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<AvaliacaoResponse>> avaliacoesPorEstabelecimento(@PathVariable Long estabelecimentoId) {
        List<Avaliacao> avaliacoes = avaliacaoRepository.findByEstabelecimentoId(estabelecimentoId);

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

    public static class CreateAvaliacaoRequest {
        private Long autorId;
        private Long estabelecimentoId;
        private Integer nota;
        private String comentario;

        public CreateAvaliacaoRequest() {}

        public CreateAvaliacaoRequest(Long autorId, Long estabelecimentoId, Integer nota, String comentario) {
            this.autorId = autorId;
            this.estabelecimentoId = estabelecimentoId;
            this.nota = nota;
            this.comentario = comentario;
        }

        public Long getAutorId() {
            return autorId;
        }

        public void setAutorId(Long autorId) {
            this.autorId = autorId;
        }

        public Long getEstabelecimentoId() {
            return estabelecimentoId;
        }

        public void setEstabelecimentoId(Long estabelecimentoId) {
            this.estabelecimentoId = estabelecimentoId;
        }

        public Integer getNota() {
            return nota;
        }

        public void setNota(Integer nota) {
            this.nota = nota;
        }

        public String getComentario() {
            return comentario;
        }

        public void setComentario(String comentario) {
            this.comentario = comentario;
        }
    }

    @PostMapping
    public ResponseEntity<AvaliacaoResponse> criarAvaliacao(@RequestBody CreateAvaliacaoRequest request) {
        PessoaFisica autor = pessoaFisicaRepository.findById(request.getAutorId())
                .orElseThrow(() -> new RuntimeException("Autor (pessoa física) não encontrado"));

        Estabelecimento estabelecimento = estabelecimentoRepository.findById(request.getEstabelecimentoId())
                .orElseThrow(() -> new RuntimeException("Estabelecimento não encontrado"));

        Avaliacao avaliacao = new Avaliacao();
        avaliacao.setAutor(autor);
        avaliacao.setEstabelecimento(estabelecimento);
        avaliacao.setNota(request.getNota());
        avaliacao.setTexto(request.getComentario());

        Avaliacao salvo = avaliacaoRepository.save(avaliacao);

        AvaliacaoResponse resp = new AvaliacaoResponse(
                salvo.getId(),
                salvo.getAutor() != null ? salvo.getAutor().getNome() : null,
                salvo.getDataAvaliacao(),
                salvo.getTexto(),
                salvo.getNota()
        );

        return ResponseEntity.ok(resp);
    }
}
