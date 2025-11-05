package com.jdk21.academia.features.comunidad.repository;

import com.jdk21.academia.domain.Comunidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ComunidadRepository extends JpaRepository<Comunidad, Long> {
}