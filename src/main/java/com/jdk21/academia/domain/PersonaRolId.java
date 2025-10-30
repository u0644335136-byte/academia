package com.jdk21.academia.domain;
import java.io.Serializable;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PersonaRolId implements Serializable{
    private Long idPersona;
    private Long idRol;
}
