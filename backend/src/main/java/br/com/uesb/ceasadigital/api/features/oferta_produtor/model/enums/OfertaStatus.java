package br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums;

public enum OfertaStatus {
  PENDENTE, // Aguardando confirmação do produtor
  ATIVA,   // Confirmado pelo produtor, disponível para venda
  RECUSADO, // Recusado pelo produtor (ainda não implementado)
  ESGOTADO, // Quantidade disponível chegou a zero
  CANCELADO // Cancelado pelo Admin ou sistema
}