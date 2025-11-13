package com.jdk21.academia.features.profesor.controller;

import com.jdk21.academia.features.profesor.dto.ProfesorDto;
import com.jdk21.academia.features.profesor.service.ProfesorService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class ProfesorGraphQLController {

    private final ProfesorService profesorService;

    public ProfesorGraphQLController(ProfesorService profesorService) {
        this.profesorService = profesorService;
    }

    @QueryMapping
    public List<ProfesorDto> profesores() {
        try {
            return profesorService.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error al listar profesores: " + e.getMessage());
        }
    }

    @QueryMapping
    public ProfesorDto profesorById(@Argument Long id) {
        try {
            Optional<ProfesorDto> profesor = profesorService.findById(id);
            return profesor.orElse(null);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Profesor no encontrado: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al buscar profesor: " + e.getMessage());
        }
    }

    @MutationMapping
    public ProfesorDto crearProfesor(
            @Argument String nombre,
            @Argument String apellidos,
            @Argument String telefono,
            @Argument String email,
            @Argument String direccion,
            @Argument String localidad,
            @Argument String provincia) {
        try {
            ProfesorDto dto = ProfesorDto.builder()
                    .nombre(nombre)
                    .apellidos(apellidos)
                    .telefono(telefono)
                    .email(email)
                    .direccion(direccion)
                    .localidad(localidad)
                    .provincia(provincia)
                    .activo(true)
                    .build();
            return profesorService.crearProfesor(dto);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error de validación al crear profesor: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al crear profesor: " + e.getMessage());
        }
    }

    @MutationMapping
    public ProfesorDto actualizarProfesor(
            @Argument Long id,
            @Argument String nombre,
            @Argument String apellidos,
            @Argument String telefono,
            @Argument String email,
            @Argument String direccion,
            @Argument String localidad,
            @Argument String provincia,
            @Argument Boolean activo) {
        try {
            // Construye el DTO con los valores proporcionados
            ProfesorDto dto = ProfesorDto.builder()
                    .idProfesor(id)  // Establece el ID para identificar el profesor a actualizar
                    .nombre(nombre)
                    .apellidos(apellidos)
                    .telefono(telefono)
                    .email(email)
                    .direccion(direccion)
                    .localidad(localidad)
                    .provincia(provincia)
                    .activo(activo != null ? activo : true)  // Usa el valor proporcionado o true por defecto
                    .build();

            // Llama al servicio para actualizar el profesor
            Optional<ProfesorDto> updatedProfesor = profesorService.actualizarProfesor(id, dto);
            return updatedProfesor.orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado para actualización"));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Error de validación al actualizar profesor: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Error al actualizar profesor: " + e.getMessage());
        }
    }
}
