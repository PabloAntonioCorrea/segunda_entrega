package com.restaurante.repository;

import com.restaurante.model.Pedido;
import com.restaurante.model.StatusPedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    List<Pedido> findByStatus(StatusPedido status);
    
    List<Pedido> findByDataBetween(LocalDateTime dataInicio, LocalDateTime dataFim);
    
    List<Pedido> findByClienteId(Long clienteId);
    
    List<Pedido> findByGarcomId(Long garcomId);
}
