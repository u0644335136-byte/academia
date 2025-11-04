package com.jdk21.academia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona_rol")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@IdClass(PersonaRolId.class)
public class PersonaRol implements Serializable{
    // === CLAVE COMPUESTA ===
    @Id
    @Column(name = "id_persona")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long idPersona;

    @Id
    @Column(name = "id_rol")
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long idRol;

    // === RELACIONES ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_persona", insertable = false, updatable = false)
    private Persona persona;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rol", insertable = false, updatable = false)
    private Rol rol;
}
