package com.jdk21.academia.features.comunidad.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ComunidadDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long idComunidad;

    @NotBlank(message = "El código es obligatorio")
    @Size(max = 10, message = "El código no puede superar 10 caracteres")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "La capital no puede superar 100 caracteres")
    private String capital;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaCreacion;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime fechaActualizacion;

    private Boolean activo;
}

