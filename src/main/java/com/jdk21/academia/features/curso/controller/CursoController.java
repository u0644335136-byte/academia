package com.jdk21.academia.features.curso.controller;

import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.curso.dto.CursoDto;
import com.jdk21.academia.features.curso.mapper.CursoMapper;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
public class CursoController {

    private final CursoMapper mapper = CursoMapper.INSTANCE;

    // Ejemplo de endpoint para convertir entidad a DTO
    @GetMapping("/demo")
    public CursoDto demo() {
        Curso curso = Curso.builder()
                .idCurso(1L)
                .nombre("Matemáticas")
                .descripcion("Curso de ejemplo")
                .activo(true)
                .duracionHoras(40)
                .build();

        return mapper.toDto(curso);
    }
}
