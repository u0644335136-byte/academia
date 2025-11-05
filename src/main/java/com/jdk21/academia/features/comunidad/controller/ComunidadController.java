package com.jdk21.academia.features.comunidad.controller;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.service.ComunidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comunidades")
@RequiredArgsConstructor
public class ComunidadController {

    private final ComunidadService service;

    @GetMapping
    public List<ComunidadDto> listar() {
        return service.listarTodas();
    }

    @GetMapping("/{id}")
    public ComunidadDto obtener(@PathVariable Long idComunidad) {
        return service.obtenerPorId(idComunidad);
    }

    @PostMapping
    public ComunidadDto crear(@RequestBody ComunidadDto dto) {
        return service.guardar(dto);
    }

    @PutMapping("/{id}")
    public ComunidadDto actualizar(@PathVariable Long idComunidad, @RequestBody ComunidadDto dto) {
        dto.setIdComunidad(idComunidad);
        return service.guardar(dto);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long idComunidad) {
        service.eliminar(idComunidad);
    }
}