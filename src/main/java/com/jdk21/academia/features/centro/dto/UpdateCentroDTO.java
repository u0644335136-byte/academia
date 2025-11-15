package com.jdk21.academia.features.centro.dto;

import java.io.Serializable;

public record UpdateCentroDTO(
    String codigo_centro,
    String nombre,
    String responsable,
    Integer capacidadMax,
    Boolean activo,
    Integer telefono,
    String email,
    String web,
    String direccion,
    Integer numero,
    Integer piso,
    String puerta,
    Integer codigo_postal,
    String localidad,
    String provincia
) implements Serializable {
private static final long serialVersionUID = 1L;
}