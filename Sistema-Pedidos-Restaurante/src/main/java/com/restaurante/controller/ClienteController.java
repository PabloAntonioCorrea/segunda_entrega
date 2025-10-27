package com.restaurante.controller;

import com.restaurante.dto.ClienteRequestDTO;
import com.restaurante.dto.ClienteResponseDTO;
import com.restaurante.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operações relacionadas aos clientes")
public class ClienteController {
    
    private final ClienteService clienteService;
    
    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cadastra um novo cliente no sistema")
    public ResponseEntity<ClienteResponseDTO> criarCliente(@Valid @RequestBody ClienteRequestDTO request) {
        try {
            ClienteResponseDTO cliente = clienteService.criarCliente(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping
    @Operation(summary = "Listar clientes", description = "Retorna todos os clientes cadastrados")
    public ResponseEntity<List<ClienteResponseDTO>> listarClientes() {
        List<ClienteResponseDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo ID")
    public ResponseEntity<ClienteResponseDTO> buscarClientePorId(@PathVariable Long id) {
        try {
            ClienteResponseDTO cliente = clienteService.buscarClientePorId(id);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    public ResponseEntity<ClienteResponseDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO request) {
        try {
            ClienteResponseDTO cliente = clienteService.atualizarCliente(id, request);
            return ResponseEntity.ok(cliente);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir cliente", description = "Remove um cliente do sistema")
    public ResponseEntity<Void> excluirCliente(@PathVariable Long id) {
        try {
            clienteService.excluirCliente(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
