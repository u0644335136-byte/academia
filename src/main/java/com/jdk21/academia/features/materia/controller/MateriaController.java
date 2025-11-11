package com.jdk21.academia.features.materia.controller;

import com.jdk21.academia.features.materia.dto.MateriaDto;
import com.jdk21.academia.features.materia.service.MateriaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/materias")
@RequiredArgsConstructor
public class MateriaController {

    private final MateriaService materiaService;

    @PostMapping
    public ResponseEntity<MateriaDto> crearMateria(@RequestBody MateriaDto dto) {
        MateriaDto created = materiaService.crearMateria(dto);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MateriaDto> actualizarMateria(@PathVariable Long id, @RequestBody MateriaDto dto) {
        return materiaService.actualizarMateria(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MateriaDto> eliminarMateria(@PathVariable Long id) {
        return materiaService.eliminarMateria(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MateriaDto>> getAll() {
        List<MateriaDto> lista = materiaService.obtenerTodas();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MateriaDto> getById(@PathVariable Long id) {
        return materiaService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
