package com.jdk21.academia.features.centro.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CentroDTO(
    Long idCentro,
    String codigo_centro,
    String nombre,
    String responsable,
    Integer capacidadMax,
    Boolean activo,
    String telefono,
    String email,
    String web,
    String direccion,
    String numero,
    String piso,
    String puerta,
    String codigoPostal,
    String localidad,
    String provincia,
    Long idempresa,
    Long idcomunidad,
    String empresaNombre,
    LocalDateTime fechaCreacion,
    LocalDateTime fechaActualiza
) implements Serializable {
    private static final long serialVersionUID = 1L;
}
