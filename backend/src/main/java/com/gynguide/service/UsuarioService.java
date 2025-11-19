package com.gynguide.service;

import com.gynguide.dto.PessoaFisicaResponse;
import com.gynguide.dto.PessoaJuridicaResponse;
import com.gynguide.model.PessoaFisica;
import com.gynguide.model.PessoaJuridica;
import com.gynguide.repository.PessoaFisicaRepository;
import com.gynguide.repository.PessoaJuridicaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    
    public List<PessoaFisicaResponse> listarPessoasFisicas() {
        return pessoaFisicaRepository.findAll().stream()
            .map(PessoaFisicaResponse::fromPessoaFisica)
            .collect(Collectors.toList());
    }
    
    public List<PessoaJuridicaResponse> listarPessoasJuridicas() {
        return pessoaJuridicaRepository.findAll().stream()
            .map(PessoaJuridicaResponse::fromPessoaJuridica)
            .collect(Collectors.toList());
    }
}

