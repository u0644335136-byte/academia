package com.jdk21.academia.features.comunidad.controller;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

// **Asegúrate de cambiar estos DTOs por tus rutas reales**
import com.jdk21.academia.features.comunidad.dto.ComunidadDto;
import com.jdk21.academia.features.comunidad.dto.CreateComunidadDTO;
import com.jdk21.academia.features.comunidad.dto.UpdateComunidadDTO;
import com.jdk21.academia.features.comunidad.service.ComunidadService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ComunidadGraphQLController {

    private final ComunidadService comunidadService;

    // ===============
    //      QUERY
    // ===============

    @QueryMapping
    public List<ComunidadDto> allComunidades() {
        // Llama al método del servicio
        return comunidadService.getAllComunidades();
    }

    @QueryMapping
    public ComunidadDto comunidadById(@Argument Long id) {
        // Llama al método del servicio
        return comunidadService.getComunidadById(id);
    }

    @QueryMapping
    public ComunidadDto comunidadByCodigo(@Argument String codigo) {
        // Llama al método del servicio
        return comunidadService.getComunidadByCodigo(codigo);
    }


    // ===============
    //    MUTATIONS
    // ===============

    @MutationMapping
    public ComunidadDto createComunidad(@Argument CreateComunidadDTO input) {
        // Llama al método del servicio para crear
        return comunidadService.createComunidad(input);
    }

    @MutationMapping
    public ComunidadDto updateComunidad(@Argument Long id, @Argument UpdateComunidadDTO input) {
        // Llama al método del servicio para actualizar
        return comunidadService.updateComunidad(id, input);
    }

    @MutationMapping
    public Boolean deleteComunidad(@Argument Long id) {
        // Llama al método del servicio para eliminar (lógico o físico)
        comunidadService.deleteComunidad(id);
        return true; // Devuelve true si la operación fue exitosa
    }

    // ================================
    //  MAPEOS DE CAMPOS (snake_case a camelCase)
    // ================================

    /**
     * Mapea id_comunidad (en GraphQL) a idComunidad() (en el DTO/Record).
     */
    @SchemaMapping(typeName = "Comunidad", field = "id_comunidad")
    public Long idComunidad(ComunidadDto comunidad) {
        // Asumiendo que tu record/DTO tiene un getter como idComunidad()
        return comunidad.idComunidad(); 
    }
    
    /**
     * Mapea fechaCreacion (en GraphQL) al campo del DTO.
     */
    @SchemaMapping(typeName = "Comunidad", field = "fechaCreacion")
    public String fechaCreacion(ComunidadDto comunidad) {
        return comunidad.fechaCreacion() != null ? comunidad.fechaCreacion().toString() : null;
    }

    /**
     * Mapea fechaActualizacion (en GraphQL) al campo del DTO.
     */
    @SchemaMapping(typeName = "Comunidad", field = "fechaActualizacion")
    public String fechaActualizacion(ComunidadDto comunidad) {
        return comunidad.fechaActualizacion() != null ? comunidad.fechaActualizacion().toString() : null;
    }
}