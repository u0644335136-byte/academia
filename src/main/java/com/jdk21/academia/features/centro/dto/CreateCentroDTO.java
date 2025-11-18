package com.jdk21.academia.features.centro.dto;

import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;

public record CreateCentroDTO(
    @NotBlank(message = "El código del centro es obligatorio")
    String codigo_centro,
    
    @NotBlank(message = "El nombre del centro es obligatorio")
    String nombre,
    
    @NotBlank(message = "El responsable es obligatorio")
    String responsable,
    Long id_empresa,
    Long id_comunidad,
    Integer capacidad_maxima,
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
