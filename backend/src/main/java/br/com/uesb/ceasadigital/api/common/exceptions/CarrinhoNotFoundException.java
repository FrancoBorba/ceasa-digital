package br.com.uesb.ceasadigital.api.common.exceptions;

public class CarrinhoNotFoundException extends RuntimeException {
    public CarrinhoNotFoundException(Long id) {
        super("Carrinho com ID " + id + " não encontrado.");
    }

    public CarrinhoNotFoundException(String message) {
        super(message);
    }
}
