package br.com.uesb.ceasadigital.api.exceptions;

public class ItemCarrinhoNotFoundException extends RuntimeException {
    public ItemCarrinhoNotFoundException(Long id) {
        super("Item com ID " + id + " não encontrado no carrinho.");
    }
}
