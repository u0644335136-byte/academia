package com.jdk21.academia.features.persona.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

public record PersonaDTO(
    Long idPersona,
    String nombre,
    String apellido,
    Boolean activo,
    LocalDate fechaNacimiento,
    Integer telefono,
    String email,
    String web,
    String direccion,
    Integer numero,
    Integer piso,
    String puerta,
    Integer codigoPostal,
    String localidad,
    String provincia,
    Long idDiscapacidad,
  
    Set<String> roles
) implements Serializable {
    
    private static final long serialVersionUID = 1L;
}

