package com.restaurante.service;

import com.restaurante.dto.ClienteRequestDTO;
import com.restaurante.dto.ClienteResponseDTO;
import com.restaurante.model.Cliente;
import com.restaurante.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ClienteService {
    
    private final ClienteRepository clienteRepository;
    
    public ClienteResponseDTO criarCliente(ClienteRequestDTO request) {
        if (clienteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + request.getEmail());
        }
        
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        
        Cliente clienteSalvo = clienteRepository.save(cliente);
        return converterParaResponseDTO(clienteSalvo);
    }
    
    @Transactional(readOnly = true)
    public List<ClienteResponseDTO> listarClientes() {
        return clienteRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public ClienteResponseDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        return converterParaResponseDTO(cliente);
    }
    
    public ClienteResponseDTO atualizarCliente(Long id, ClienteRequestDTO request) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com ID: " + id));
        
        if (!cliente.getEmail().equals(request.getEmail()) && 
            clienteRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email já cadastrado: " + request.getEmail());
        }
        
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        cliente.setTelefone(request.getTelefone());
        
        Cliente clienteAtualizado = clienteRepository.save(cliente);
        return converterParaResponseDTO(clienteAtualizado);
    }
    
    public void excluirCliente(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("Cliente não encontrado com ID: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
    private ClienteResponseDTO converterParaResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getEmail(),
                cliente.getTelefone()
        );
    }
}
