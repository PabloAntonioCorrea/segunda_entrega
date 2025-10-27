package com.restaurante.service;

import com.restaurante.dto.*;
import com.restaurante.model.*;
import com.restaurante.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PedidoService {
    
    private final PedidoRepository pedidoRepository;
    private final ClienteRepository clienteRepository;
    private final GarcomRepository garcomRepository;
    private final PratoRepository pratoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    
    public PedidoResponseDTO criarPedido(PedidoRequestDTO request) {
        Cliente cliente = clienteRepository.findById(request.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + request.getClienteId()));
        
        Garcom garcom = garcomRepository.findById(request.getGarcomId())
                .orElseThrow(() -> new RuntimeException("Garçom não encontrado com ID: " + request.getGarcomId()));
        
        Pedido pedido = new Pedido();
        pedido.setData(LocalDateTime.now());
        pedido.setStatus(StatusPedido.EM_ABERTO);
        pedido.setCliente(cliente);
        pedido.setGarcom(garcom);
        pedido.setValorTotal(BigDecimal.ZERO);
        
        Pedido pedidoSalvo = pedidoRepository.save(pedido);
        
        BigDecimal valorTotal = BigDecimal.ZERO;
        
        for (ItemPedidoRequestDTO itemRequest : request.getItens()) {
            Prato prato = pratoRepository.findById(itemRequest.getPratoId())
                    .orElseThrow(() -> new RuntimeException("Prato não encontrado com ID: " + itemRequest.getPratoId()));
            
            ItemPedido itemPedido = new ItemPedido();
            itemPedido.setPedido(pedidoSalvo);
            itemPedido.setPrato(prato);
            itemPedido.setQuantidade(itemRequest.getQuantidade());
            
            BigDecimal subTotal = prato.getPreco().multiply(BigDecimal.valueOf(itemRequest.getQuantidade()));
            itemPedido.setSubTotal(subTotal);
            
            itemPedidoRepository.save(itemPedido);
            valorTotal = valorTotal.add(subTotal);
        }
        
        pedidoSalvo.setValorTotal(valorTotal);
        pedidoRepository.save(pedidoSalvo);
        
        return buscarPedidoPorId(pedidoSalvo.getId());
    }
    
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> listarPedidos() {
        return pedidoRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PedidoResponseDTO buscarPedidoPorId(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        return converterParaResponseDTO(pedido);
    }
    
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorStatus(StatusPedido status) {
        return pedidoRepository.findByStatus(status)
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorCliente(Long clienteId) {
        return pedidoRepository.findByClienteId(clienteId)
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PedidoResponseDTO> buscarPedidosPorGarcom(Long garcomId) {
        return pedidoRepository.findByGarcomId(garcomId)
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    public PedidoResponseDTO atualizarStatusPedido(Long id, StatusPedido novoStatus) {
        System.out.println("DEBUG: Atualizando pedido " + id + " para status: " + novoStatus);
        
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        System.out.println("DEBUG: Status atual do pedido: " + pedido.getStatus());
        
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Não é possível alterar o status de um pedido cancelado");
        }
        
        pedido.setStatus(novoStatus);
        Pedido pedidoAtualizado = pedidoRepository.save(pedido);
        
        System.out.println("DEBUG: Status após atualização: " + pedidoAtualizado.getStatus());
        
        return converterParaResponseDTO(pedidoAtualizado);
    }
    
    public void cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado com ID: " + id));
        
        if (pedido.getStatus() == StatusPedido.CANCELADO) {
            throw new RuntimeException("Pedido já está cancelado");
        }
        
        pedido.setStatus(StatusPedido.CANCELADO);
        pedidoRepository.save(pedido);
    }
    
    private PedidoResponseDTO converterParaResponseDTO(Pedido pedido) {
        List<ItemPedidoResponseDTO> itens = new ArrayList<>();
        
        if (pedido.getItensPedido() != null) {
            itens = pedido.getItensPedido()
                    .stream()
                    .map(item -> {
                        PratoResponseDTO prato = new PratoResponseDTO(
                                item.getPrato().getId(),
                                item.getPrato().getNome(),
                                item.getPrato().getPreco()
                        );
                        return new ItemPedidoResponseDTO(
                                item.getId(),
                                item.getQuantidade(),
                                item.getSubTotal(),
                                prato
                        );
                    })
                    .collect(Collectors.toList());
        }
        
        ClienteResponseDTO cliente = new ClienteResponseDTO(
                pedido.getCliente().getId(),
                pedido.getCliente().getNome(),
                pedido.getCliente().getEmail(),
                pedido.getCliente().getTelefone()
        );
        
        GarcomResponseDTO garcom = new GarcomResponseDTO(
                pedido.getGarcom().getId(),
                pedido.getGarcom().getNome(),
                pedido.getGarcom().getMatricula()
        );
        
        return new PedidoResponseDTO(
                pedido.getId(),
                pedido.getData(),
                pedido.getStatus(),
                pedido.getValorTotal(),
                cliente,
                garcom,
                itens
        );
    }
}
