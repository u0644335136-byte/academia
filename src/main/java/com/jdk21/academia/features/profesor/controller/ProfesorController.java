package com.jdk21.academia.features.profesor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jdk21.academia.domain.Profesor;
import com.jdk21.academia.features.profesor.dto.ProfesorDto;
import com.jdk21.academia.features.profesor.service.ProfesorService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/profesor")
@RequiredArgsConstructor
public class ProfesorController {
    
    private final ProfesorService profesorService;

    @PostMapping
    public ResponseEntity<Profesor> crearProfesor(@RequestBody ProfesorDto dto){
        return ResponseEntity.ok(profesorService.crearProfesor(dto));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Profesor> actualizarProfesor(@PathVariable Long id, @RequestBody ProfesorDto dto){
        return profesorService.actualizarProfesor(id, dto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profesor> eliminarProfesor(@PathVariable Long id){
        return profesorService.eliminarProfesor(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
    }

}
