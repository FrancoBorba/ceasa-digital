package br.com.uesb.ceasadigital.api.features.item_carrinho.dto.response;

import java.math.BigDecimal;

import io.swagger.v3.oas.annotations.media.Schema;



public class CarrinhoItemResponseDTO {

  @Schema(description = "ID único do item no carrinho.", example = "101")
  Long id;

  @Schema(description = "ID da oferta do produto.", example = "42")
  Long productID;

  @Schema(description = "Preço por unidade de medida no momento da adição.", example = "10.50")
  String nomeDoProduto;

  @Schema(description = "Subtotal para este item (preço * quantidade).", example = "26.25")
  BigDecimal precoUnitario;

  
   @Schema(
    description = " Preco que o item mais sua quantidade agrega no carrinho" ,
    example = "200" , 
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  BigDecimal subTotal; // Se o kilo custa 10 reais e tem 2.5 kg o sub total é 25

   @Schema(
    description = " Quantidade do item na sua unidade de medida" ,
    example = "10" , 
    requiredMode = Schema.RequiredMode.REQUIRED
  )
  BigDecimal quantidade;


   public Long getId() {
     return id;
   }

   public void setId(Long id) {
     this.id = id;
   }

   public Long getProductID() {
     return productID;
   }

   public void setProductID(Long productID) {
     this.productID = productID;
   }

   public String getNomeDoProduto() {
     return nomeDoProduto;
   }

   public void setNomeDoProduto(String nomeDoProduto) {
     this.nomeDoProduto = nomeDoProduto;
   }

   public BigDecimal getPrecoUnitario() {
     return precoUnitario;
   }

   public void setPrecoUnitario(BigDecimal precoUnitario) {
     this.precoUnitario = precoUnitario;
   }

   public BigDecimal getSubTotal() {
     return  precoUnitario.multiply(quantidade);
   }

   public void setSubTotal(BigDecimal subTotal) {
     this.subTotal = subTotal;
   }

   public BigDecimal getQuantidade() {
     return quantidade;
   }

   public void setQuantidade(BigDecimal quantidade) {
     this.quantidade = quantidade;
   }
}