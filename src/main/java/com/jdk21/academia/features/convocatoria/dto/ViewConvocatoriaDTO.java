package com.jdk21.academia.features.convocatoria.dto;

import java.time.LocalDate;

public record ViewConvocatoriaDTO(
        Long idConvocatoria,
        String profesor,
        String curso,
        Integer duracion,
        LocalDate fechaInicio,
        LocalDate fechaFin,
        String catalago,
        String centro,
        String codigoCentro,
        String codigoConvocatoria
) {}