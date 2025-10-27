package com.restaurante.dto;

import com.restaurante.model.StatusPedido;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoResponseDTO {
    
    private Long id;
    private LocalDateTime data;
    private StatusPedido status;
    private BigDecimal valorTotal;
    private ClienteResponseDTO cliente;
    private GarcomResponseDTO garcom;
    private List<ItemPedidoResponseDTO> itens;
}
