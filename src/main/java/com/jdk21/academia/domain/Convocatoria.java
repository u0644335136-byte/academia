package com.jdk21.academia.domain;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.sql.Date;


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
@AttributeOverride(name = "id", column = @Column(name = "id_convocatoria"))
public class Convocatoria extends BaseEntity implements Serializable{

    @Column(name = "codigo")
    private String codigo;

    @Column(name = "fecha_inicio")
    private Date fechaInicio;

    @Column(name = "fecha_fin")
    private Date fechaFin;

    //Llaves foraneas 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_curso", referencedColumnName = "id_curso")
    private Curso curso;

    @Column(name = "id_catalogo")
    private Long idCatalogo;

    @Column(name = "id_profesor")
    private Long idProfesor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_centro", referencedColumnName = "id_centro")
    private Centro centro;

}
