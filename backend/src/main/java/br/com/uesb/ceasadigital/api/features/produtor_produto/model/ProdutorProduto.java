package br.com.uesb.ceasadigital.api.features.produtor_produto.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.com.uesb.ceasadigital.api.features.product.model.Product;
import br.com.uesb.ceasadigital.api.features.produtor.model.Produtor;
import br.com.uesb.ceasadigital.api.features.user.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_produtor_produto")
public class ProdutorProduto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "produtor_id", nullable = false)
    private Produtor produtor;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Product produto;

    @ManyToOne
    @JoinColumn(name = "admin_autorizador_id")
    private User adminAutorizador;

    @Column(nullable = false, length = 50)
    private String status; 

    @CreationTimestamp
    @Column(name = "criado_em", updatable = false)
    private LocalDateTime criadoEm;

    @UpdateTimestamp
    @Column(name = "atualizado_em")
    private LocalDateTime atualizadoEm;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Produtor getProdutor() {
        return produtor;
    }

    public void setProdutor(Produtor produtor) {
        this.produtor = produtor;
    }

    public Product getProduto() {
        return produto;
    }

    public void setProduto(Product produto) {
        this.produto = produto;
    }

    public User getAdminAutorizador() {
        return adminAutorizador;
    }

    public void setAdminAutorizador(User adminAutorizador) {
        this.adminAutorizador = adminAutorizador;
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

    /* getters e setters */
    
}
