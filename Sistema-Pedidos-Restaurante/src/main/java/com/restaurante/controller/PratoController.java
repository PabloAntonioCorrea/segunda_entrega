package com.restaurante.controller;

import com.restaurante.dto.PratoRequestDTO;
import com.restaurante.dto.PratoResponseDTO;
import com.restaurante.service.PratoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pratos")
@RequiredArgsConstructor
@Tag(name = "Pratos", description = "Operações relacionadas aos pratos")
public class PratoController {
    
    private final PratoService pratoService;
    
    @PostMapping
    @Operation(summary = "Criar novo prato", description = "Cadastra um novo prato no cardápio")
    public ResponseEntity<PratoResponseDTO> criarPrato(@Valid @RequestBody PratoRequestDTO request) {
        PratoResponseDTO prato = pratoService.criarPrato(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(prato);
    }
    
    @GetMapping
    @Operation(summary = "Listar pratos", description = "Retorna todos os pratos do cardápio")
    public ResponseEntity<List<PratoResponseDTO>> listarPratos() {
        List<PratoResponseDTO> pratos = pratoService.listarPratos();
        return ResponseEntity.ok(pratos);
    }
    
    @GetMapping("/buscar")
    @Operation(summary = "Buscar pratos por nome", description = "Busca pratos que contenham o nome especificado")
    public ResponseEntity<List<PratoResponseDTO>> buscarPratosPorNome(@RequestParam String nome) {
        List<PratoResponseDTO> pratos = pratoService.buscarPratosPorNome(nome);
        return ResponseEntity.ok(pratos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar prato por ID", description = "Retorna um prato específico pelo ID")
    public ResponseEntity<PratoResponseDTO> buscarPratoPorId(@PathVariable Long id) {
        try {
            PratoResponseDTO prato = pratoService.buscarPratoPorId(id);
            return ResponseEntity.ok(prato);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar prato", description = "Atualiza os dados de um prato existente")
    public ResponseEntity<PratoResponseDTO> atualizarPrato(@PathVariable Long id, @Valid @RequestBody PratoRequestDTO request) {
        try {
            PratoResponseDTO prato = pratoService.atualizarPrato(id, request);
            return ResponseEntity.ok(prato);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir prato", description = "Remove um prato do cardápio")
    public ResponseEntity<Void> excluirPrato(@PathVariable Long id) {
        try {
            pratoService.excluirPrato(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
