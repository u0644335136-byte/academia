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

}


    /* 
    // ===============
    //    MUTATIONS
    // ===============

    @MutationMapping
    public CentroDTO createCentro(@Argument CreateCentroDTO input) {
        return centroService.createCentro(input);
    }

    @MutationMapping
    public CentroDTO updateCentro(@Argument Long id, @Argument UpdateCentroDTO updateDTO) {
        return centroService.updateCentro(id, updateDTO);
    }

    @MutationMapping
    public CentroDTO desactivarCentro(@Argument Long id) {
        return centroService.desactivarCentro(id);
    }

    // ================================
    //  MAPEOS DE CAMPOS "RAROS" (snake_case)
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
        return centro.idempresa();
    }

    @SchemaMapping(typeName = "Centro", field = "id_comunidad")
    public Long id_comunidad(CentroDTO centro) {
        return centro.idcomunidad();
    }

    
}


*/


/*
package com.jdk21.academia.features.centro.controller;

import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
import com.jdk21.academia.features.centro.mapper.CentroMapper;
import com.jdk21.academia.features.centro.service.CentroService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Controller
public class CentroGraphQlController {

    private final CentroService service;
    private final CentroMapper mapper;
    
    public CentroGraphQlController (CentroService service, CentroMapper mapper){
        this.service = service;
        this.mapper = mapper;
    }
    // ========== QUERIES ==========

    @QueryMapping
    public List<CentroDTO> getAllCentros() {
        return service.getAllCentros();
    }

    @QueryMapping
    public CentroDTO getCentroById(@Argument Long id) {

        return service.getCentroById(id);
    }

    @QueryMapping
    public CentroDTO getCentroByCodigo(@Argument String codigoCentro) {

        return service.getCentroByCodigo(codigoCentro);
    }

    @QueryMapping
    public List<CentroDTO> getCentrosActivos() {

        return service.getCentrosActivos();
    }

    // ========== MUTATIONS ==========
/*
    @MutationMapping
    public CentroDTO createCentro(@Argument CreateCentroDTO createDTO) {

        return service.createCentro(createDTO);
    }
        //
    @MutationMapping
    public CentroDTO createCentro(@Argument ("input") CreateCentroDTO centroDTO) {
        try {
            return service.createCentro(centroDTO);
        } catch (Exception e) {
            throw new RuntimeException("Error creating centro: " + e.getMessage());
        }
    }

    @MutationMapping
    public CentroDTO updateCentro(@Argument ("input") Long id, @Argument UpdateCentroDTO updateDTO) {

        return service.updateCentro(id, updateDTO);
    }

    @MutationMapping
    public CentroDTO desactivarCentro(@Argument Long id) {

        return service.desactivarCentro(id);
    }
}
*/