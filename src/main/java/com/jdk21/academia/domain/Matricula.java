package com.jdk21.academia.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matricula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "id_calificacion")
    private Long idCalificacion;

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "precio")
    private int precio;

    //Auto-incluidos
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDate fechaActualizacion;

    @Column(name = "activo", insertable = false)
    private boolean activo;

    //Llaves foraneas 
    @Column(name = "id_convocatoria")
    private Long idConvocatoria;

    //Llaves foraneas 
    @Column(name = "id_alumno")
    private Long idAlumno;
}