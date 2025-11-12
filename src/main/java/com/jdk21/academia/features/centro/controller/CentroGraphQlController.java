package com.jdk21.academia.features.centro.controller;

import com.jdk21.academia.features.centro.dto.CentroDTO;
import com.jdk21.academia.features.centro.dto.CreateCentroDTO;
import com.jdk21.academia.features.centro.dto.UpdateCentroDTO;
import com.jdk21.academia.features.centro.mapper.CentroMapper;
import com.jdk21.academia.features.centro.service.CentroService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class CentroGraphQlController {

    private final CentroService service;
    private final CentroMapper mapper;

    
    // ========== QUERIES ==========

    @QueryMapping
    public List<CentroDTO> getAllCentros() {
        log.info("GraphQL - Obteniendo todos los centros");
        return service.getAllCentros();
    }

    @QueryMapping
    public CentroDTO getCentroById(@Argument Long id) {
        log.info("GraphQL - Obteniendo centro por ID: {}", id);
        return service.getCentroById(id);
    }

    @QueryMapping
    public CentroDTO getCentroByCodigo(@Argument String codigoCentro) {
        log.info("GraphQL - Obteniendo centro por código: {}", codigoCentro);
        return service.getCentroByCodigo(codigoCentro);
    }

    @QueryMapping
    public List<CentroDTO> getCentrosActivos() {
        log.info("GraphQL - Obteniendo centros activos");
        return service.getCentrosActivos();
    }

    // ========== MUTATIONS ==========

    @MutationMapping
    public CentroDTO createCentro(@Argument CreateCentroDTO createDTO) {
        log.info("GraphQL - Creando centro con código: {}", createDTO.codigo_centro());
        return service.createCentro(createDTO);
    }

    @MutationMapping
    public CentroDTO updateCentro(@Argument Long id, @Argument UpdateCentroDTO updateDTO) {
        log.info("GraphQL - Actualizando centro con ID: {}", id);
        return service.updateCentro(id, updateDTO);
    }

    @MutationMapping
    public CentroDTO desactivarCentro(@Argument Long id) {
        log.info("GraphQL - Desactivando centro con ID: {}", id);
        return service.desactivarCentro(id);
    }
}
