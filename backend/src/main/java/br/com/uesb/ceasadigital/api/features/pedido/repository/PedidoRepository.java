package br.com.uesb.ceasadigital.api.features.pedido.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.uesb.ceasadigital.api.features.pedido.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    Optional<Pedido> findByIdAndUsuarioId(Long id, Long usuarioId);
    
    List<Pedido> findByUsuarioId(Long usuarioId);

}
