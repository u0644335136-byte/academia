package com.jdk21.academia.features.comunidad.controller;

import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.service.ComunidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ComunidadGraphQLController {

    private final ComunidadService servicio;

    // --- QUERIES ---
    @QueryMapping
    public List<ComunidadDto> allComunidades() {
        return servicio.listarTodas();
    }

    @QueryMapping
    public ComunidadDto comunidadById(@Argument Long id) {
        ComunidadDto dto = servicio.obtenerPorId(id);
        if (dto == null) {
            throw new RuntimeException("Comunidad no encontrada con id " + id);
        }
        return dto;
    }

    // --- MUTATIONS ---
    @MutationMapping
    public ComunidadDto createComunidad(@Argument("input") ComunidadDto input) {
        return servicio.guardar(input);
    }

    @MutationMapping
    public ComunidadDto updateComunidad(@Argument Long id, @Argument("input") ComunidadDto input) {
        ComunidadDto updated = servicio.actualizar(id, input);
        if (updated == null) {
            throw new RuntimeException("Comunidad no encontrada con id " + id);
        }
        return updated;
    }

    @MutationMapping
    public Boolean deleteComunidad(@Argument Long id) {
        servicio.deleteComunidad(id);
        return true;
    }
}
