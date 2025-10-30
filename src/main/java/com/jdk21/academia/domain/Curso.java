package com.jdk21.academia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "curso", schema = "public")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_curso")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // Jackson ignora en POST
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)     // Swagger lo muestra solo en respuestas
    private Long idCurso;

    @Column(name = "id_materia")
    private Long idMateria;

    @Column(name = "id_formato")
    private Long idFormato;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;

    @Column(name = "duracion_horas")
    private Integer duracionHoras;
}
