package com.jdk21.academia.features.alumno.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty; // Import oficial Jackson (clase Annotation para renaming, doc: marca field para mapper).

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor; // Lombok: Genera constructor all-args (AlumnoDto(Long id, String nombre, ...)).
import lombok.Data; // Lombok: Genera getters/setters/equals/hashCode/toString (e.g., setNombre(String) usado por Jackson post-instancia).
import lombok.NoArgsConstructor; // Lombok: Genera constructor vacío (necesario para Jackson: new AlumnoDto(), then set fields).

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AlumnoDto {

    @JsonProperty("id_alumno") // Mapea JSON "id_alumno" a field (si JSON snake); cambia a "idAlumno" si camel.
    private Long id_alumno;

    @JsonProperty("nombre")
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 50, message = "El nombre no puede exceder 50 caracteres")
    private String nombre;

    @JsonProperty("apellidos")
    @NotBlank(message = "Los apellidos son obligatorios")
    @Size(max = 100, message = "Los apellidos no pueden exceder 100 caracteres")
    private String apellidos;

    @JsonProperty("telefono")
    @NotNull(message = "El teléfono es obligatorio")
    @Pattern(regexp = "^[6-9]\\d{8}$", message = "El teléfono debe ser un número válido de 9 dígitos")
    private String telefono;

    @JsonProperty("email")
    @NotBlank(message = "El email es obligatorio")
    @Email(message = "El email debe ser válido")
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @JsonProperty("contrasenia")
    @NotBlank(message = "La contraseña es obligatoria")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    private String contrasenia;

    @JsonProperty("fecha_nacimiento")
    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fecha_nacimiento;

    @JsonProperty("localidad")
    @NotBlank(message = "La localidad es obligatoria")
    @Size(max = 50, message = "La localidad no puede exceder 50 caracteres")
    private String localidad;

    @JsonProperty("provincia")
    @NotBlank(message = "La provincia es obligatoria")
    @Size(max = 50, message = "La provincia no puede exceder 50 caracteres")
    private String provincia;

    @JsonProperty("activo")
    @NotNull(message = "El estado activo es obligatorio")
    private Boolean activo;

}