package com.jdk21.academia.persona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdk21.academia.persona.domain.Persona;

public interface PersonaRepository extends JpaRepository<Persona, Long> {
    Optional<Persona> findByTelefono(Integer telefono);

}
