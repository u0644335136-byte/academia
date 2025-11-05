package com.jdk21.academia.domain.view;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;


@Entity
@Table(name = "vista_convocatoria") // 👈 matches your SQL view name
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ViewConvocatoria {

    @Id
    @Column(name = "id_convocatoria")
    private Long idConvocatoria;

    @Column(name = "profesor")
    private String profesor;

    @Column(name = "curso")
    private String curso;

    @Column(name = "duracion")
    private Integer duracion;

    @Column(name = "fecha_inicio")
    private java.time.LocalDate fechaInicio;

    @Column(name = "fecha_fin")
    private java.time.LocalDate fechaFin;

    @Column(name = "catalago")
    private String catalago;

    @Column(name = "centro")
    private String centro;

    @Column(name = "codigo_centro")
    private String codigoCentro;

    @Column(name = "codigo_convocatoria")
    private String codigoConvocatoria;
}
