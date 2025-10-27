package com.restaurante.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarcomResponseDTO {
    
    private Long id;
    private String nome;
    private String matricula;
}
