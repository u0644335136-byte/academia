package com.jdk21.academia.features.comunidad.controller;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.service.ComunidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunidades")
@RequiredArgsConstructor
public class ComunidadController {

    private final ComunidadService servicio;

    // ✅ GET /api/comunidades
    @GetMapping
    public ResponseEntity<List<ComunidadDto>> listarTodas() {
        return ResponseEntity.ok(servicio.listarTodas());
    }

    // ✅ GET /api/comunidades/{idComunidad}
    @GetMapping("/{idComunidad}")
    public ResponseEntity<ComunidadDto> obtenerPorId(@PathVariable Long idComunidad) {
        ComunidadDto dto = servicio.obtenerPorId(idComunidad);
        return dto != null ? ResponseEntity.ok(dto) : ResponseEntity.notFound().build();
    }

    // ✅ POST /api/comunidades
    @PostMapping
    public ResponseEntity<ComunidadDto> crear(@RequestBody ComunidadDto dto) {
        dto.setIdComunidad(null); // lo genera la BD
        ComunidadDto guardada = servicio.guardar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardada);
    }

    // ✅ PUT /api/comunidades/{idComunidad}
    @PutMapping("/{idComunidad}")
    public ResponseEntity<ComunidadDto> actualizar(@PathVariable Long idComunidad, @RequestBody ComunidadDto dto) {
        ComunidadDto actualizada = servicio.actualizar(idComunidad, dto);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    // ✅ DELETE /api/comunidades/{idComunidad}
    @DeleteMapping("/{idComunidad}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idComunidad) {
        servicio.eliminar(idComunidad);
        return ResponseEntity.noContent().build();
    }
}
