package com.restaurante.repository;

import com.restaurante.model.Garcom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GarcomRepository extends JpaRepository<Garcom, Long> {
    
    Optional<Garcom> findByMatricula(String matricula);
    
    boolean existsByMatricula(String matricula);
}
