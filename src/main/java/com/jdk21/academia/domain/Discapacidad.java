package com.jdk21.academia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "discapacidad")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Discapacidad implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Integer idDiscapacidad;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "porcentaje")
    private Integer porcentaje;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "reconocida", nullable = false)
    private Boolean reconocida;

    @Column(name = "adaptabilidad")
    private String adptabilidad;

    @Column(name = "comentarios")
    private String comentarios;

    // Campos de timestamps
    @Column(name = "fecha_reconocimiento")
    private java.time.LocalDate fecha_reconocimiento;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaActualizacion;
}
