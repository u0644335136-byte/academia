package com.jdk21.academia.features.curso.controller;

import com.jdk21.academia.features.curso.dto.CursoDto;
import com.jdk21.academia.features.curso.service.CursoService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class CursoGraphQLController {

    private final CursoService cursoService;

    public CursoGraphQLController(CursoService cursoService) {
        this.cursoService = cursoService;
    }

    // --- QUERIES ---
    @QueryMapping
    public List<CursoDto> allCursos() {
        return cursoService.obtenerTodos();
    }

    @QueryMapping
    public CursoDto cursoById(@Argument Long id) {
        return cursoService.obtenerPorId(id)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id " + id));
    }

    // --- MUTATIONS ---
    @MutationMapping
    public CursoDto createCurso(@Argument("input") CursoDto input) {
        return cursoService.crearCurso(input);
    }

    @MutationMapping
    public CursoDto updateCurso(@Argument Long id, @Argument("input") CursoDto input) {
        return cursoService.actualizarCurso(id, input)
                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id " + id));
    }

    @MutationMapping
    public Boolean deleteCurso(@Argument Long id) {
        cursoService.eliminarCurso(id);
        return true;
    }
}
