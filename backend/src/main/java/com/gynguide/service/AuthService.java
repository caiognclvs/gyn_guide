package com.gynguide.service;

import com.gynguide.dto.CadastroPessoaFisicaRequest;
import com.gynguide.dto.CadastroPessoaJuridicaRequest;
import com.gynguide.dto.LoginRequest;
import com.gynguide.dto.UsuarioResponse;
import com.gynguide.exception.CnpjJaCadastradoException;
import com.gynguide.exception.CredenciaisInvalidasException;
import com.gynguide.exception.EmailJaCadastradoException;
import com.gynguide.model.PessoaFisica;
import com.gynguide.model.PessoaJuridica;
import com.gynguide.model.Usuario;
import com.gynguide.repository.PessoaFisicaRepository;
import com.gynguide.repository.PessoaJuridicaRepository;
import com.gynguide.repository.UsuarioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
    
    private final UsuarioRepository usuarioRepository;
    private final PessoaFisicaRepository pessoaFisicaRepository;
    private final PessoaJuridicaRepository pessoaJuridicaRepository;
    
    public AuthService(UsuarioRepository usuarioRepository, PessoaFisicaRepository pessoaFisicaRepository, PessoaJuridicaRepository pessoaJuridicaRepository) {
        this.usuarioRepository = usuarioRepository;
        this.pessoaFisicaRepository = pessoaFisicaRepository;
        this.pessoaJuridicaRepository = pessoaJuridicaRepository;
    }
    
    @Transactional
    public UsuarioResponse cadastrarPessoaFisica(CadastroPessoaFisicaRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailJaCadastradoException(request.getEmail());
        }
        
        PessoaFisica pessoaFisica = new PessoaFisica();
        pessoaFisica.setEmail(request.getEmail());
        pessoaFisica.setSenha(request.getSenha()); // Em produção, usar BCrypt
        pessoaFisica.setTipoUsuario(Usuario.TipoUsuario.PESSOA_FISICA);
        pessoaFisica.setNome(request.getNome());
        pessoaFisica.setDataNascimento(request.getDataNascimento());
        
        pessoaFisica = pessoaFisicaRepository.save(pessoaFisica);
        return UsuarioResponse.fromUsuario(pessoaFisica);
    }
    
    @Transactional
    public UsuarioResponse cadastrarPessoaJuridica(CadastroPessoaJuridicaRequest request) {
        if (usuarioRepository.existsByEmail(request.getEmail())) {
            throw new EmailJaCadastradoException(request.getEmail());
        }
        
        if (pessoaJuridicaRepository.existsByCnpj(request.getCnpj())) {
            throw new CnpjJaCadastradoException(request.getCnpj());
        }
        
        PessoaJuridica pessoaJuridica = new PessoaJuridica();
        pessoaJuridica.setEmail(request.getEmail());
        pessoaJuridica.setSenha(request.getSenha()); // Em produção, usar BCrypt
        pessoaJuridica.setTipoUsuario(Usuario.TipoUsuario.PESSOA_JURIDICA);
        pessoaJuridica.setNomeFantasia(request.getNomeFantasia());
        pessoaJuridica.setRazaoSocial(request.getRazaoSocial());
        pessoaJuridica.setCnpj(request.getCnpj());
        pessoaJuridica.setEndereco(request.getEndereco());
        
        pessoaJuridica = pessoaJuridicaRepository.save(pessoaJuridica);
        return UsuarioResponse.fromUsuario(pessoaJuridica);
    }
    
    public UsuarioResponse login(LoginRequest request) {
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new CredenciaisInvalidasException());
        
        if (!usuario.getSenha().equals(request.getSenha())) {
            throw new CredenciaisInvalidasException();
        }
        
        return UsuarioResponse.fromUsuario(usuario);
    }
}

