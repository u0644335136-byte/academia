package com.jdk21.academia.features.convocatoria.dto;

import java.time.LocalDate;

import com.jdk21.academia.domain.Centro;
import com.jdk21.academia.domain.Curso;

// TODO: Las entidads se deben cambiar por su DTO
public record ConvocatoriaDTO(
        Long idConvocatoria,
        String codigo,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        Curso curso,
        Long idCatalogo,
        Long idProfesor,
        Centro centro
) {}