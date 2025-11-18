package com.jdk21.academia.features.alumno.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para la respuesta/salida de alumnos.
 * No incluye validaciones ya que es solo para representar datos de salida.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDto {

    @JsonProperty("id_alumno")
    private Long id_alumno;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellidos")
    private String apellidos;

    @JsonProperty("telefono")
    private String telefono;

    @JsonProperty("email")
    private String email;
    
    @JsonProperty("contrasenia")
    private String contrasenia;

    @JsonProperty("fecha_nacimiento")
    private LocalDate fecha_nacimiento;

    @JsonProperty("localidad")
    private String localidad;

    @JsonProperty("provincia")
    private String provincia;

    @JsonProperty("activo")
    private Boolean activo;
}