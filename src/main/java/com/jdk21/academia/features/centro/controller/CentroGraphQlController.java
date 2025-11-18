package com.jdk21.academia.features.centro.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;
import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
import com.jdk21.academia.features.centro.service.CentroService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class CentroGraphQlController {

    private final CentroService centroService;

    // ===============
    //      QUERY
    // ===============

    @QueryMapping
    public List<CentroDTO> getAllCentros() {
        return centroService.getAllCentros();
    }

    @QueryMapping
    public CentroDTO getCentroById(@Argument Long id) {
        return centroService.getCentroById(id);
    }

    @QueryMapping
    public CentroDTO getCentroByCodigo(@Argument("codigo_centro") String codigoCentro) {
        return centroService.getCentroByCodigo(codigoCentro);
    }

    @QueryMapping
    public List<CentroDTO> getCentrosActivos() {
        return centroService.getCentrosActivos();
    }

    // ===============
    //    MUTATIONS
    // ===============

    @MutationMapping
    public CentroDTO createCentro(@Argument("input") CreateCentroDTO input) {
        return centroService.createCentro(input);
    }

    @MutationMapping
    public CentroDTO updateCentro(@Argument Long id, @Argument("updateDTO") UpdateCentroDTO updateDTO) {
        return centroService.updateCentro(id, updateDTO);
    }

    @MutationMapping
    public CentroDTO desactivarCentro(@Argument Long id) {
        return centroService.desactivarCentro(id);
    }

    // ================================
    //  MAPEOS DE CAMPOS (snake_case)
    // ================================

    @SchemaMapping(typeName = "Centro", field = "id_centro")
    public Long id_centro(CentroDTO centro) {
        return centro.id_Centro();
    }

    @SchemaMapping(typeName = "Centro", field = "capacidad_maxima")
    public Integer capacidad_maxima(CentroDTO centro) {
        return centro.capacidad_maxima();
    }

    @SchemaMapping(typeName = "Centro", field = "codigo_postal")
    public Integer codigo_postal(CentroDTO centro) {
        return centro.codigo_postal();
    }

    @SchemaMapping(typeName = "Centro", field = "id_empresa")
    public Long id_empresa(CentroDTO centro) {
        return centro.id_empresa();
    }

    @SchemaMapping(typeName = "Centro", field = "fechaCreacion")
    public String fechaCreacion(CentroDTO centro) {
        return centro.fechaCreacion() != null ? centro.fechaCreacion().toString() : null;
    }

    @SchemaMapping(typeName = "Centro", field = "fechaActualizacion")
    public String fechaActualizacion(CentroDTO centro) {
        return centro.fechaActualizacion() != null ? centro.fechaActualizacion().toString() : null;
    }

}