package com.gynguide.exception;

public class EstabelecimentoNaoEncontradoException extends RuntimeException {
    
    public EstabelecimentoNaoEncontradoException(Long id) {
        super("Estabelecimento não encontrado com ID: " + id);
    }
    
    public EstabelecimentoNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
