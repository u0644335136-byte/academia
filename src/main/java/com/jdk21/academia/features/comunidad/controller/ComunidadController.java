package com.jdk21.academia.features.comunidad.controller;

import com.jdk21.academia.domain.Comunidad;
import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.repository.ComunidadRepository;
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
    private final ComunidadRepository ComunidadRepository;

    @GetMapping
    @Operation(summary = "Listar todas las comunidades")
    public ResponseEntity<List<ComunidadDto>> listarTodas() {
        return ResponseEntity.ok(service.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una comunidad por su ID")
    public ResponseEntity<ComunidadDto> obtenerPorId(@PathVariable Long id) {
        ComunidadDto dto = service.obtenerPorId(id);
        return (dto != null) ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    @PostMapping
    @Operation(summary = "Crear una nueva comunidad")
    public ResponseEntity<ComunidadDto> crear(@RequestBody ComunidadDto dto) {
        dto.setIdComunidad(null); // Evita error 500 por ID duplicado
        ComunidadDto guardada = service.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una comunidad existente")
    public ResponseEntity<ComunidadDto> actualizar(@PathVariable Long id, @RequestBody ComunidadDto dto) {
        ComunidadDto actualizada = service.actualizar(id, dto);
        return (actualizada != null) ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

@DeleteMapping("/{id}")
@Operation(summary = "Eliminar una comunidad por ID")
public ResponseEntity<String> deleteComunidad(@PathVariable Long idComunidad) {
    service.deleteComunidad(idComunidad);
    return ResponseEntity.ok("✅ Comunidad desactivada correctamente");
}
}


