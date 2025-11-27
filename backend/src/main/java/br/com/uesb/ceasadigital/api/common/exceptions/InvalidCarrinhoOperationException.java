package br.com.uesb.ceasadigital.api.common.exceptions;

public class InvalidCarrinhoOperationException extends RuntimeException {
    public InvalidCarrinhoOperationException(String message) {
        super(message);
    }
}
