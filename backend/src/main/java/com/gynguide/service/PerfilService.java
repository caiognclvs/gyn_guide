package com.gynguide.service;

import com.gynguide.dto.AtualizarPerfilPessoaFisicaRequest;
import com.gynguide.dto.AtualizarPerfilPessoaJuridicaRequest;
import com.gynguide.dto.PerfilPessoaFisicaResponse;
import com.gynguide.dto.PerfilPessoaJuridicaResponse;
import com.gynguide.exception.CnpjJaCadastradoException;
import com.gynguide.exception.EmailJaCadastradoException;
import com.gynguide.exception.UsuarioNaoEncontradoException;
import com.gynguide.model.PessoaFisica;
import com.gynguide.model.PessoaJuridica;
import com.gynguide.repository.PessoaFisicaRepository;
import com.gynguide.repository.PessoaJuridicaRepository;
import com.gynguide.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PerfilService {
    
    private final UsuarioRepository usuarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    
    public PerfilService(UsuarioRepository usuarioRepository, 
                        PessoaFisicaRepository pessoaFisicaRepository,
                        PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }
    
    public PerfilPessoaFisicaResponse buscarPerfilPessoaFisica(Long id) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        
        return new PerfilPessoaFisicaResponse(
            pessoaFisica.getId(),
            pessoaFisica.getEmail(),
            pessoaFisica.getNome(),
            pessoaFisica.getDataNascimento()
        );
    }
    
    public PerfilPessoaJuridicaResponse buscarPerfilPessoaJuridica(Long id) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        
        return new PerfilPessoaJuridicaResponse(
            pessoaJuridica.getId(),
            pessoaJuridica.getEmail(),
            pessoaJuridica.getNomeFantasia(),
            pessoaJuridica.getRazaoSocial(),
            pessoaJuridica.getCnpj(),
            pessoaJuridica.getEndereco()
        );
    }
    
    @Transactional
    public PerfilPessoaFisicaResponse atualizarPerfilPessoaFisica(Long id, AtualizarPerfilPessoaFisicaRequest request) {
        PessoaFisica pessoaFisica = pessoaFisicaRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        
        if (!pessoaFisica.getEmail().equals(request.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new EmailJaCadastradoException(request.getEmail());
            }
        }
        
        pessoaFisica.setEmail(request.getEmail());
        if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
            pessoaFisica.setSenha(request.getSenha());
        }
        pessoaFisica.setNome(request.getNome());
        pessoaFisica.setDataNascimento(request.getDataNascimento());
        
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        
        return new PerfilPessoaFisicaResponse(
            pessoaFisica.getId(),
            pessoaFisica.getEmail(),
            pessoaFisica.getNome(),
            pessoaFisica.getDataNascimento()
        );
    }
    
    @Transactional
    public PerfilPessoaJuridicaResponse atualizarPerfilPessoaJuridica(Long id, AtualizarPerfilPessoaJuridicaRequest request) {
        PessoaJuridica pessoaJuridica = pessoaJuridicaRepository.findById(id)
            .orElseThrow(() -> new UsuarioNaoEncontradoException(id));
        
        if (!pessoaJuridica.getEmail().equals(request.getEmail())) {
            if (usuarioRepository.existsByEmail(request.getEmail())) {
                throw new EmailJaCadastradoException(request.getEmail());
            }
        }
        
        if (!pessoaJuridica.getCnpj().equals(request.getCnpj())) {
            if (pessoaJuridicaRepository.existsByCnpj(request.getCnpj())) {
                throw new CnpjJaCadastradoException(request.getCnpj());
            }
        }
        
        pessoaJuridica.setEmail(request.getEmail());
        if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
            pessoaJuridica.setSenha(request.getSenha());
        }
        pessoaJuridica.setNomeFantasia(request.getNomeFantasia());
        pessoaJuridica.setRazaoSocial(request.getRazaoSocial());
        pessoaJuridica.setCnpj(request.getCnpj());
        pessoaJuridica.setEndereco(request.getEndereco());
        
        pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);
        
        return new PerfilPessoaJuridicaResponse(
            pessoaJuridica.getId(),
            pessoaJuridica.getEmail(),
            pessoaJuridica.getNomeFantasia(),
            pessoaJuridica.getRazaoSocial(),
            pessoaJuridica.getCnpj(),
            pessoaJuridica.getEndereco()
        );
    }
}

