package com.jdk21.academia.features.curso.controller;

import com.jdk21.academia.domain.Curso;
import com.jdk21.academia.features.curso.dto.CursoDto;
import com.jdk21.academia.features.curso.service.CursoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<Curso> crearCurso(@RequestBody CursoDto dto) {
        return ResponseEntity.ok(cursoService.crearCurso(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Curso> actualizarCurso(@PathVariable Long id, @RequestBody CursoDto dto) {
        return cursoService.actualizarCurso(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> eliminarCurso(@PathVariable Long id) {
        return cursoService.eliminarCurso(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
