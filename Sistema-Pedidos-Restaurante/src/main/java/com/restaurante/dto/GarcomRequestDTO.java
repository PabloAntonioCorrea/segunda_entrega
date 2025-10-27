package com.restaurante.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GarcomRequestDTO {
    
    @NotBlank(message = "Nome é obrigatório")
    private String nome;
    
    @NotBlank(message = "Matrícula é obrigatória")
    @Pattern(regexp = "GAR\\d{4}", message = "Matrícula deve estar no formato GARXXXX")
    private String matricula;
}
