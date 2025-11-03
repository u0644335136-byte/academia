package com.jdk21.academia.features.persona.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdk21.academia.domain.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
    /**
     * Busca un rol por su código único.
     * 
     * @param codigo Código del rol (ej: "ALUMNO", "PROFESOR")
     * @return Optional con el rol encontrado, o vacío si no existe
     */
    Optional<Rol> findByCodigo(String codigo);
}
