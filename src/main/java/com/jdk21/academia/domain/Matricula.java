package com.jdk21.academia.domain;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "matricula")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@AttributeOverride(name = "id", column = @Column(name = "id_matricula"))
public class Matricula extends BaseEntity implements Serializable {

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "precio")
    private int precio;

    //Llaves foraneas 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_convocatoria", referencedColumnName = "id_convocatoria")
    private Convocatoria convocatoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_alumno", referencedColumnName = "id_alumno   ")
    private Alumno alumno;
}