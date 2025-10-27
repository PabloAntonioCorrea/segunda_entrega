package com.restaurante.service;

import com.restaurante.dto.PratoRequestDTO;
import com.restaurante.dto.PratoResponseDTO;
import com.restaurante.model.Prato;
import com.restaurante.repository.PratoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PratoService {
    
    private final PratoRepository pratoRepository;
    
    public PratoResponseDTO criarPrato(PratoRequestDTO request) {
        Prato prato = new Prato();
        prato.setNome(request.getNome());
        prato.setPreco(request.getPreco());
        
        Prato pratoSalvo = pratoRepository.save(prato);
        return converterParaResponseDTO(pratoSalvo);
    }
    
    @Transactional(readOnly = true)
    public List<PratoResponseDTO> listarPratos() {
        return pratoRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<PratoResponseDTO> buscarPratosPorNome(String nome) {
        return pratoRepository.findByNomeContainingIgnoreCase(nome)
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PratoResponseDTO buscarPratoPorId(Long id) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado com ID: " + id));
        return converterParaResponseDTO(prato);
    }
    
    public PratoResponseDTO atualizarPrato(Long id, PratoRequestDTO request) {
        Prato prato = pratoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prato não encontrado com ID: " + id));
        
        prato.setNome(request.getNome());
        prato.setPreco(request.getPreco());
        
        Prato pratoAtualizado = pratoRepository.save(prato);
        return converterParaResponseDTO(pratoAtualizado);
    }
    
    public void excluirPrato(Long id) {
        if (!pratoRepository.existsById(id)) {
            throw new RuntimeException("Prato não encontrado com ID: " + id);
        }
        pratoRepository.deleteById(id);
    }
    
    private PratoResponseDTO converterParaResponseDTO(Prato prato) {
        return new PratoResponseDTO(
                prato.getId(),
                prato.getNome(),
                prato.getPreco()
        );
    }
}
