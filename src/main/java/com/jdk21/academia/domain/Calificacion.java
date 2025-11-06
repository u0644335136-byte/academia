package com.jdk21.academia.domain;

import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "calificacion")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Calificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "id_calificacion")
    private Long idCalificacion;

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "nota")
    private int nota;

    //Auto-incluidos
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo", insertable = false)
    private boolean activo;

    //Llaves foraneas 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_matricula", referencedColumnName = "id_matricula")
    private Matricula matricula;

}