package com.jdk21.academia.features.profesor.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdk21.academia.domain.Profesor;
import com.jdk21.academia.features.profesor.dto.ProfesorDto;
import com.jdk21.academia.features.profesor.service.ProfesorService;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profesor")
@RequiredArgsConstructor
public class ProfesorController {
    
    private final ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<ProfesorDto> crearProfesor(@Valid @RequestBody ProfesorDto dto) {
        try {
            ProfesorDto created = profesorService.crearProfesor(dto);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null); // Handle validation errors (e.g., duplicate email).
        }
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<ProfesorDto> actualizarProfesor(@PathVariable Long id, @Valid @RequestBody ProfesorDto dto) {
        try {
            return profesorService.actualizarProfesor(id, dto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profesor> eliminarProfesor(@PathVariable Long id){
        return profesorService.eliminarProfesor(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProfesorDto> findById(@PathVariable Long id) {
        return profesorService.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<ProfesorDto>> findAll() {
        List<ProfesorDto> profesores = profesorService.findAll();
        return ResponseEntity.ok(profesores);
    }

}