package com.jdk21.academia.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "convocatoria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Convocatoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "id_calificacion")
    private Long idCalificacion;

    @Column(name = "codigo")
    private Long codigo;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private LocalDate fechaFin;


    //Auto-incluidos
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDate fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDate fechaActualizacion;

    @Column(name = "activo", insertable = false)
    private boolean activo;

    //Llaves foraneas 
    @Column(name = "id_curso")
    private Long idCurso;

    @Column(name = "id_catalogo")
    private Long idCatalogo;

    @Column(name = "id_profesor")
    private Long idProfesor;

    @Column(name = "id_centro")
    private Long idCentro;

}
