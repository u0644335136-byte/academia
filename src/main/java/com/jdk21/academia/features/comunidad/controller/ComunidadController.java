package com.jdk21.academia.features.comunidad.controller;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto; // DTO de SALIDA
import com.jdk21.academia.features.comunidad.dto.CreateComunidadDTO; // DTO de ENTRADA (Creación)
import com.jdk21.academia.features.comunidad.dto.UpdateComunidadDTO; // DTO de ENTRADA (Actualización)
import com.jdk21.academia.features.comunidad.service.ComunidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunidades")
@RequiredArgsConstructor
@Tag(name = "Comunidad", description = "Gestión de Comunidades Autónomas")
public class ComunidadController {

    private final ComunidadService service;
    // Nota: El repositorio no es necesario inyectarlo en el controlador si solo usas el servicio.
    // private final ComunidadRepository ComunidadRepository; 

    // 1. GET ALL: Usa el nuevo nombre del método: getAllComunidades()
    @GetMapping
    @Operation(summary = "Listar todas las comunidades")
    public ResponseEntity<List<ComunidadDto>> listarTodas() {
        return ResponseEntity.ok(service.getAllComunidades()); // <-- CAMBIO
    }

    // 2. GET BY ID: Usa el nuevo nombre del método: getComunidadById()
    @GetMapping("/{id}")
    @Operation(summary = "Obtener una comunidad por su ID")
    public ResponseEntity<ComunidadDto> obtenerPorId(@PathVariable Long id) {
        ComunidadDto dto = service.getComunidadById(id); // <-- CAMBIO
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // 3. POST: Usa el DTO de entrada (CreateComunidadDTO) y el nuevo nombre del método: createComunidad()
    @PostMapping
    @Operation(summary = "Crear una nueva comunidad")
    public ResponseEntity<ComunidadDto> crear(@RequestBody CreateComunidadDTO dto) { // <-- CAMBIO DTO DE ENTRADA
        // Ya no es necesario setear el ID a null si usas el DTO de creación, 
        // ya que este DTO no contiene el campo ID.
        ComunidadDto guardada = service.createComunidad(dto); // <-- CAMBIO
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // 4. PUT: Usa el DTO de entrada (UpdateComunidadDTO) y el nuevo nombre del método: updateComunidad()
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una comunidad existente")
    public ResponseEntity<ComunidadDto> actualizar(@PathVariable Long id, @RequestBody UpdateComunidadDTO dto) { // <-- CAMBIO DTO DE ENTRADA
        ComunidadDto actualizada = service.updateComunidad(id, dto); // <-- CAMBIO
        return (actualizada != null) ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    // 5. DELETE: Usa el nuevo nombre del método: deleteComunidad()
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una comunidad por ID")
    public ResponseEntity<String> deleteComunidad(@PathVariable Long id) { // <-- Usar 'id' por consistencia
        service.deleteComunidad(id); 
        return ResponseEntity.ok("✅ Comunidad desactivada correctamente");
    }
}