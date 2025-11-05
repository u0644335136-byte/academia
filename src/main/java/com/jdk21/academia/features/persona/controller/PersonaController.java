package com.jdk21.academia.features.persona.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.jdk21.academia.features.persona.dto.AsignarRolDTO;
import com.jdk21.academia.features.persona.dto.CreatePersonaDTO;
import com.jdk21.academia.features.persona.dto.PersonaDTO;
import com.jdk21.academia.features.persona.dto.UpdatePersonaDTO;
import com.jdk21.academia.features.persona.service.PersonaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
public class PersonaController {
    
    private final PersonaService personaService;
    
    @PostMapping
    public ResponseEntity<PersonaDTO> createPersona(@Valid @RequestBody CreatePersonaDTO createDTO) {
        PersonaDTO personaCreada = personaService.createPersona(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(personaCreada);
    }
    
    @GetMapping
    public ResponseEntity<List<PersonaDTO>> getAllPersonas() {
        List<PersonaDTO> personas = personaService.getAllPersonas();
        return ResponseEntity.ok(personas);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<PersonaDTO> getPersonaById(@PathVariable Long id) {
        try {
            PersonaDTO persona = personaService.getPersonaById(id);
            return ResponseEntity.ok(persona);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PersonaDTO> updatePersona(
            @PathVariable Long id,
            @Valid @RequestBody UpdatePersonaDTO updateDTO) {
        try {
            PersonaDTO personaActualizada = personaService.updatePersona(id, updateDTO);
            return ResponseEntity.ok(personaActualizada);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePersona(@PathVariable Long id) {
        try {
            personaService.deletePersona(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/roles/asignar")
    public ResponseEntity<Void> asignarRol(@Valid @RequestBody AsignarRolDTO asignarRolDTO) {
        try {
            personaService.asignarRol(asignarRolDTO);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @DeleteMapping("/{idPersona}/roles/{codigoRol}")
    public ResponseEntity<Void> removerRol(
            @PathVariable Long idPersona,
            @PathVariable String codigoRol) {
        try {
            personaService.removerRol(idPersona, codigoRol);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}

