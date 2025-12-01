package com.gynguide.exception;

public class CnpjJaCadastradoException extends RuntimeException {
    
    public CnpjJaCadastradoException(String cnpj) {
        super("CNPJ já cadastrado: " + cnpj);
    }
}
