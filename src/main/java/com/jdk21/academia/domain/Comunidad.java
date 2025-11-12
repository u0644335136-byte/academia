package com.jdk21.academia.domain;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Entidad JPA mapeada a la tabla 'comunidad' de la BD nativa.
 * 💡 Refleja la estructura existente sin modificarla.
 */
@Entity
@Table(name = "comunidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Comunidad implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include

    // ⚠️ Si "id_comunidad" ya es la PK en la BD, no uses @GeneratedValue arriba.
    // En ese caso, cámbialo a este campo y elimina el anterior.

    @Column(name = "id_comunidad", nullable = false)
    private Long idComunidad;

    @Column(name = "codigo", length = 10)
    private String codigo;

    @Column(name = "nombre", length = 100)
    private String nombre;

    @Column(name = "capital", length = 100)
    private String capital;

    //Auto-incluidos
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo", insertable = false)
    private boolean activo;
    }
