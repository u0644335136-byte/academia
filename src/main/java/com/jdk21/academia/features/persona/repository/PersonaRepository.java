package com.jdk21.academia.features.persona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jdk21.academia.domain.Persona;
@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByTelefono(Integer telefono);

}
