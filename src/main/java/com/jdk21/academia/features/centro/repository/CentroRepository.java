package com.jdk21.academia.features.centro.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jdk21.academia.domain.Centro;

@Repository
public interface CentroRepository extends JpaRepository<Centro, Long>{

// Solo los métodos esenciales para empezar
    Optional<Centro> findByCodigoCentro(String codigoCentro);
    boolean existsByCodigoCentro(String codigoCentro);
    Optional<Centro> findByActivoTrue();
    
}
