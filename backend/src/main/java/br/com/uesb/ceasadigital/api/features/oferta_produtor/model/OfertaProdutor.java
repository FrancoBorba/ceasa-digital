package br.com.uesb.ceasadigital.api.features.oferta_produtor.model;

import br.com.uesb.ceasadigital.api.features.estoque.model.EstoqueVirtual;
import br.com.uesb.ceasadigital.api.features.oferta_produtor.model.enums.OfertaStatus;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
  @JoinColumn(name = "produtor_id", nullable = false)
  private Produtor produtor;
  
  @Column(name = "quantidade_ofertada", nullable = false, precision = 10, scale = 3)
  private BigDecimal quantidadeOfertada;
  
  @Column(name = "quantidade_disponivel", nullable = false, precision = 10, scale = 3)
  private BigDecimal quantidadeDisponivel;

  @Column(name = "quantidade_reservada", nullable = false, precision = 10, scale = 3)
  private BigDecimal quantidadeReservada = BigDecimal.ZERO;
  
  @Column(name = "total_volume_vendido", nullable = false, precision = 10, scale = 3)
  private BigDecimal totalVolumeVendido = BigDecimal.ZERO;
  
  @Column(name = "data_ultima_venda")
  private LocalDateTime dataUltimaVenda;
  
  @Enumerated(EnumType.STRING)
  @Column(length = 50, nullable = false)
  private OfertaStatus status;
  
  @CreationTimestamp
  @Column(name = "criado_em", updatable = false)
  private LocalDateTime criadoEm;
  
  @UpdateTimestamp
  @Column(name = "atualizado_em")
  private LocalDateTime atualizadoEm;
  
  // Getters e Setters
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
    return produtor;
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

  public BigDecimal getQuantidadeReservada() {
    return quantidadeReservada;
  }

  public void setQuantidadeReservada(BigDecimal quantidadeReservada) {
    this.quantidadeReservada = quantidadeReservada;
  }

  
  public LocalDateTime getDataUltimaVenda() {
    return dataUltimaVenda;
  }
  
  public void setDataUltimaVenda(LocalDateTime dataUltimaVenda) {
    this.dataUltimaVenda = dataUltimaVenda;
  }
  
  public OfertaStatus getStatus() {
    return status;
  }
  
  public void setStatus(OfertaStatus status) {
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