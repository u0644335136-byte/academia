package com.jdk21.academia.domain;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "calificacion")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@AttributeOverride(name = "id", column = @Column(name = "id_calificacion"))
public class Calificacion extends BaseEntity implements Serializable {

    @Column(name = "fecha")
    private Date fecha;

    @Column(name = "nota")
    private int nota;

    //Llaves foraneas 
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_matricula", referencedColumnName = "id_matricula")
    private Matricula matricula;

}