package com.restaurante.controller;

import com.restaurante.dto.GarcomRequestDTO;
import com.restaurante.dto.GarcomResponseDTO;
import com.restaurante.service.GarcomService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/garcons")
@RequiredArgsConstructor
@Tag(name = "Garçons", description = "Operações relacionadas aos garçons")
public class GarcomController {
    
    private final GarcomService garcomService;
    
    @PostMapping
    @Operation(summary = "Criar novo garçom", description = "Cadastra um novo garçom no sistema")
    public ResponseEntity<GarcomResponseDTO> criarGarcom(@Valid @RequestBody GarcomRequestDTO request) {
        try {
            GarcomResponseDTO garcom = garcomService.criarGarcom(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(garcom);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Listar garçons", description = "Retorna todos os garçons cadastrados")
    public ResponseEntity<List<GarcomResponseDTO>> listarGarcons() {
        List<GarcomResponseDTO> garcons = garcomService.listarGarcons();
        return ResponseEntity.ok(garcons);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar garçom por ID", description = "Retorna um garçom específico pelo ID")
    public ResponseEntity<GarcomResponseDTO> buscarGarcomPorId(@PathVariable Long id) {
        try {
            GarcomResponseDTO garcom = garcomService.buscarGarcomPorId(id);
            return ResponseEntity.ok(garcom);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar garçom", description = "Atualiza os dados de um garçom existente")
    public ResponseEntity<GarcomResponseDTO> atualizarGarcom(@PathVariable Long id, @Valid @RequestBody GarcomRequestDTO request) {
        try {
            GarcomResponseDTO garcom = garcomService.atualizarGarcom(id, request);
            return ResponseEntity.ok(garcom);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir garçom", description = "Remove um garçom do sistema")
    public ResponseEntity<Void> excluirGarcom(@PathVariable Long id) {
        try {
            garcomService.excluirGarcom(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
