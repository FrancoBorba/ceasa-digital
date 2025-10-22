package br.com.uesb.ceasadigital.api.features.carrinho.model.enums;

public enum CarrinhoStatus {
    ATIVO,       // Carrinho em uso, pode adicionar/remover itens
    FINALIZADO   // Carrinho já foi convertido em pedido, não pode ser modificado
}

