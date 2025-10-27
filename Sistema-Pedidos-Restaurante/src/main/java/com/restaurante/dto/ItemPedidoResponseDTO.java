package com.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemPedidoResponseDTO {
    
    private Long id;
    private Integer quantidade;
    private BigDecimal subTotal;
    private PratoResponseDTO prato;
}
