package com.jdk21.academia.features.materia.controller;

import com.jdk21.academia.domain.Materia;
import com.jdk21.academia.features.materia.dto.MateriaDto;
import com.jdk21.academia.features.materia.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @PostMapping
    public ResponseEntity<Materia> crearMateria(@RequestBody MateriaDto dto) {
        return ResponseEntity.ok(materiaService.crearMateria(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Materia> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDto dto) {
        return materiaService.actualizarMateria(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Materia> eliminarMateria(@PathVariable Long id) {
        return materiaService.eliminarMateria(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
