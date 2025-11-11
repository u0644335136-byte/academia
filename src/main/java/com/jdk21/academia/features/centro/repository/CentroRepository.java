package com.jdk21.academia.features.centro.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jdk21.academia.domain.Centro;

@Repository
public interface CentroRepository extends JpaRepository<Centro, Long>{

// Solo los métodos esenciales para empezar
    Optional<Centro> findByCodigo_centro(String codigo_centro);
    boolean existsByCodigo_centro(String codigo_centro);
    List<Centro> findByActivoTrue();
    
}
