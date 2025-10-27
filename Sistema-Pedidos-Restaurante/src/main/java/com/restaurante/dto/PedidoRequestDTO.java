package com.restaurante.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PedidoRequestDTO {
    
    @NotNull(message = "ID do cliente é obrigatório")
    private Long clienteId;
    
    @NotNull(message = "ID do garçom é obrigatório")
    private Long garcomId;
    
    @NotEmpty(message = "Pedido deve conter pelo menos um item")
    @Valid
    private List<ItemPedidoRequestDTO> itens;
}
