package com.restaurante.service;

import com.restaurante.dto.GarcomRequestDTO;
import com.restaurante.dto.GarcomResponseDTO;
import com.restaurante.model.Garcom;
import com.restaurante.repository.GarcomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class GarcomService {
    
    private final GarcomRepository garcomRepository;
    
    public GarcomResponseDTO criarGarcom(GarcomRequestDTO request) {
        if (garcomRepository.existsByMatricula(request.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada: " + request.getMatricula());
        }
        
        Garcom garcom = new Garcom();
        garcom.setNome(request.getNome());
        garcom.setMatricula(request.getMatricula());
        
        Garcom garcomSalvo = garcomRepository.save(garcom);
        return converterParaResponseDTO(garcomSalvo);
    }
    
    @Transactional(readOnly = true)
    public List<GarcomResponseDTO> listarGarcons() {
        return garcomRepository.findAll()
                .stream()
                .map(this::converterParaResponseDTO)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public GarcomResponseDTO buscarGarcomPorId(Long id) {
        Garcom garcom = garcomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garçom não encontrado com ID: " + id));
        return converterParaResponseDTO(garcom);
    }
    
    public GarcomResponseDTO atualizarGarcom(Long id, GarcomRequestDTO request) {
        Garcom garcom = garcomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Garçom não encontrado com ID: " + id));
        
        if (!garcom.getMatricula().equals(request.getMatricula()) && 
            garcomRepository.existsByMatricula(request.getMatricula())) {
            throw new RuntimeException("Matrícula já cadastrada: " + request.getMatricula());
        }
        
        garcom.setNome(request.getNome());
        garcom.setMatricula(request.getMatricula());
        
        Garcom garcomAtualizado = garcomRepository.save(garcom);
        return converterParaResponseDTO(garcomAtualizado);
    }
    
    public void excluirGarcom(Long id) {
        if (!garcomRepository.existsById(id)) {
            throw new RuntimeException("Garçom não encontrado com ID: " + id);
        }
        garcomRepository.deleteById(id);
    }
    
    private GarcomResponseDTO converterParaResponseDTO(Garcom garcom) {
        return new GarcomResponseDTO(
                garcom.getId(),
                garcom.getNome(),
                garcom.getMatricula()
        );
    }
}
