package com.jdk21.academia.features.alumno.controller;

import com.jdk21.academia.features.alumno.dto.AlumnoCreateInputDto;
import com.jdk21.academia.features.alumno.dto.AlumnoDto;
import com.jdk21.academia.features.alumno.dto.AlumnoPageDto;
import com.jdk21.academia.features.alumno.dto.AlumnoUpdateInputDto;
import com.jdk21.academia.features.alumno.service.AlumnoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AlumnoGraphQLController {

    private final AlumnoService alumnoService;

    public AlumnoGraphQLController(AlumnoService alumnoService) {
        this.alumnoService = alumnoService;
    }

    // --- QUERIES ---
    @QueryMapping
    public List<AlumnoDto> allAlumnos() {
        return alumnoService.getAllAlumnos();
    }

    @QueryMapping
    public AlumnoDto alumnoById(@Argument Long id) {
        return alumnoService.getAlumnoById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id " + id));
    }

    @QueryMapping
    public AlumnoPageDto searchAlumnos(@Argument String search, 
                                        @Argument Boolean activo, 
                                        @Argument Integer page, 
                                        @Argument Integer size) {
        return alumnoService.searchAlumnos(search, activo, page, size);
    }

    // --- MUTATIONS ---
    @MutationMapping
    public AlumnoDto createAlumno(@Argument("input") AlumnoCreateInputDto input) {
        return alumnoService.createAlumno(input);
    }

    @MutationMapping
    public AlumnoDto updateAlumno(@Argument Long id, @Argument("input") AlumnoUpdateInputDto input) {
        return alumnoService.updateAlumno(id, input);
    }

    @MutationMapping
    public Boolean deleteAlumno(@Argument Long id) {
        alumnoService.deleteAlumno(id);
        return true;
    }
}

