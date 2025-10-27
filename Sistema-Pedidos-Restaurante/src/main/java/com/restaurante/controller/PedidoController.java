package com.restaurante.controller;

import com.restaurante.dto.PedidoRequestDTO;
import com.restaurante.dto.PedidoResponseDTO;
import com.restaurante.model.StatusPedido;
import com.restaurante.service.PedidoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
@RequiredArgsConstructor
@Tag(name = "Pedidos", description = "Operações relacionadas aos pedidos")
public class PedidoController {
    
    private final PedidoService pedidoService;
    
    @PostMapping
    @Operation(summary = "Criar novo pedido", description = "Cria um novo pedido no sistema")
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO request) {
        PedidoResponseDTO pedido = pedidoService.criarPedido(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(pedido);
    }
    
    @GetMapping
    @Operation(summary = "Listar pedidos", description = "Retorna todos os pedidos do sistema")
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<PedidoResponseDTO> pedidos = pedidoService.listarPedidos();
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido por ID", description = "Retorna um pedido específico pelo ID")
    public ResponseEntity<PedidoResponseDTO> buscarPedidoPorId(@PathVariable Long id) {
        try {
            PedidoResponseDTO pedido = pedidoService.buscarPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/status/{status}")
    @Operation(summary = "Buscar pedidos por status", description = "Retorna pedidos filtrados por status")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosPorStatus(@PathVariable StatusPedido status) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorStatus(status);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/cliente/{clienteId}")
    @Operation(summary = "Buscar pedidos por cliente", description = "Retorna pedidos de um cliente específico")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosPorCliente(@PathVariable Long clienteId) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorCliente(clienteId);
        return ResponseEntity.ok(pedidos);
    }
    
    @GetMapping("/garcom/{garcomId}")
    @Operation(summary = "Buscar pedidos por garçom", description = "Retorna pedidos de um garçom específico")
    public ResponseEntity<List<PedidoResponseDTO>> buscarPedidosPorGarcom(@PathVariable Long garcomId) {
        List<PedidoResponseDTO> pedidos = pedidoService.buscarPedidosPorGarcom(garcomId);
        return ResponseEntity.ok(pedidos);
    }
    
    @PatchMapping("/{id}/status")
    @Operation(summary = "Atualizar status do pedido", description = "Atualiza o status de um pedido")
    public ResponseEntity<PedidoResponseDTO> atualizarStatusPedido(@PathVariable Long id, @RequestParam StatusPedido status) {
        PedidoResponseDTO pedido = pedidoService.atualizarStatusPedido(id, status);
        return ResponseEntity.ok(pedido);
    }
    
    @PatchMapping("/{id}/cancelar")
    @Operation(summary = "Cancelar pedido", description = "Cancela um pedido existente")
    public ResponseEntity<Void> cancelarPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
