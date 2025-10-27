package com.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PratoResponseDTO {
    
    private Long id;
    private String nome;
    private BigDecimal preco;
}
