package com.jdk21.academia.features.curso.controller;

import com.jdk21.academia.features.curso.dto.CursoDto;
import com.jdk21.academia.features.curso.service.CursoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cursos")
@RequiredArgsConstructor
public class CursoController {

    private final CursoService cursoService;

    @PostMapping
    public ResponseEntity<CursoDto> crearCurso(@Valid @RequestBody CursoDto dto) {
        return ResponseEntity.ok(cursoService.crearCurso(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CursoDto> actualizarCurso(@PathVariable Long id, @RequestBody CursoDto dto) {
        return cursoService.actualizarCurso(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CursoDto> eliminarCurso(@PathVariable Long id) {
        return cursoService.eliminarCurso(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<CursoDto>> getAll() {
        return ResponseEntity.ok(cursoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CursoDto> getById(@PathVariable Long id) {
        return cursoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
