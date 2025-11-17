package com.jdk21.academia.features.centro.controller;


import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
import com.jdk21.academia.features.centro.service.CentroService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/centros")
@RequiredArgsConstructor
public class CentroController {
    
    private final CentroService centroService;
    
    @GetMapping
    public ResponseEntity<List<CentroDTO>> getAllCentros() {
        List<CentroDTO> centros = centroService.getAllCentros();
        return ResponseEntity.ok(centros);
    }

    @PostMapping
    public ResponseEntity<CentroDTO> createCentro(@Valid @RequestBody CreateCentroDTO createDTO) {
        CentroDTO centroDTO = centroService.createCentro(createDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(centroDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CentroDTO> getCentroById(@PathVariable Long id) {
        CentroDTO centro = centroService.getCentroById(id);
        return ResponseEntity.ok(centro);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CentroDTO> updateCentro(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCentroDTO updateDTO) {
        try {
            CentroDTO centroActualizado = centroService.updateCentro(id, updateDTO);
            return ResponseEntity.ok(centroActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/desactivar")
    public ResponseEntity<CentroDTO> desactivarCentro(@PathVariable Long id) {
        try {
            CentroDTO centro = centroService.desactivarCentro(id);
            return ResponseEntity.ok(centro);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

}