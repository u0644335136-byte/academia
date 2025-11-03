package com.jdk21.academia.features.persona.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jdk21.academia.domain.PersonaRol;
import com.jdk21.academia.domain.PersonaRolId;

public interface PersonaRolRepository extends JpaRepository<PersonaRol, PersonaRolId> {

}
