package br.com.uesb.ceasadigital.api.features.oferta_produtor.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;

@Entity
@Table(name = "tb_ofertas_produtor")
public class OfertaProdutor {
  
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "meta_estoque_id", nullable = false)
  private EstoqueVirtual metaEstoque;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "produtor_id") // Nome da coluna no V04
  private Produtor produtor;
  
  @Column(name = "quantidade_ofertada", precision = 10, scale = 3, nullable = false)
  private BigDecimal quantidadeOfertada;
  
  @Column(name = "quantidade_disponivel", precision = 10, scale = 3, nullable = false)
  private BigDecimal quantidadeDisponivel;
  
  @Column(name = "total_volume_vendido", precision = 10, scale = 3, nullable = false)
  private BigDecimal totalVolumeVendido = BigDecimal.ZERO;
  
  @Column(name = "data_ultima_venda")
  private LocalDateTime dataUltimaVenda;
  
  @Column(nullable = false)
  private String status = "ATIVA";
  
  @Column(name = "criado_em")
  private LocalDateTime criadoEm = LocalDateTime.now();
  
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm = LocalDateTime.now();
  
  // Getters e Setters comuns
  public Long getId() {
    return id;
  }
  
  public void setId(Long id) {
    this.id = id;
  }
  
  public EstoqueVirtual getMetaEstoque() {
    return metaEstoque;
  }
  
  public void setMetaEstoque(EstoqueVirtual metaEstoque) {
    this.metaEstoque = metaEstoque;
  }
  
  public Produtor getProdutor() {
    return this.produtor;
  }
  
  public void setProdutor(Produtor produtor) {
    this.produtor = produtor;
  }
  
  public BigDecimal getQuantidadeOfertada() {
    return quantidadeOfertada;
  }
  
  public void setQuantidadeOfertada(BigDecimal quantidadeOfertada) {
    this.quantidadeOfertada = quantidadeOfertada;
  }
  
  public BigDecimal getQuantidadeDisponivel() {
    return quantidadeDisponivel;
  }
  
  public void setQuantidadeDisponivel(BigDecimal quantidadeDisponivel) {
    this.quantidadeDisponivel = quantidadeDisponivel;
  }
  
  public BigDecimal getTotalVolumeVendido() {
    return totalVolumeVendido;
  }
  
  public void setTotalVolumeVendido(BigDecimal totalVolumeVendido) {
    this.totalVolumeVendido = totalVolumeVendido;
  }
  
  public LocalDateTime getDataUltimaVenda() {
    return dataUltimaVenda;
  }
  
  public void setDataUltimaVenda(LocalDateTime dataUltimaVenda) {
    this.dataUltimaVenda = dataUltimaVenda;
  }
  
  public String getStatus() {
    return status;
  }
  
  public void setStatus(String status) {
    this.status = status;
  }
  
  public LocalDateTime getCriadoEm() {
    return criadoEm;
  }
  
  public void setCriadoEm(LocalDateTime criadoEm) {
    this.criadoEm = criadoEm;
  }
  
  public LocalDateTime getAtualizadoEm() {
    return atualizadoEm;
  }
  
  public void setAtualizadoEm(LocalDateTime atualizadoEm) {
    this.atualizadoEm = atualizadoEm;
  }
}