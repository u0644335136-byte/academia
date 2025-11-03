package com.jdk21.academia.domain;

import java.io.Serializable;

import lombok.*;

/**
 * Clase que representa la clave primaria compuesta para la entidad PersonaRol.
 * Debe implementar Serializable y tener equals() y hashCode() para funcionar correctamente con JPA.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonaRolId implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Long idPersona;
    private Long idRol;
}
