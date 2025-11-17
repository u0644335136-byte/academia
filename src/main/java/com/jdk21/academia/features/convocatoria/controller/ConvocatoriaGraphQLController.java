package com.jdk21.academia.features.convocatoria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaRequestDTO;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;
import com.jdk21.academia.features.convocatoria.service.ConvocatoriaService;

@Controller
public class ConvocatoriaGraphQLController {

    private final ConvocatoriaService convocatoriaService;

    public ConvocatoriaGraphQLController(ConvocatoriaService convocatoriaService){
        this.convocatoriaService = convocatoriaService;
    }

    //GET
    @QueryMapping
    public List<ConvocatoriaResponseDTO> retornarTodasConvocatorias(){
        return convocatoriaService.getAll();
    }

    @QueryMapping Optional<ConvocatoriaResponseDTO> convocatoriaPorId(@Argument Long id){
        return convocatoriaService.getById(id);
    }


    //POST
    @MutationMapping
    public ConvocatoriaResponseDTO crearConvocatoria(@Argument("input") ConvocatoriaRequestDTO dto) {
        return convocatoriaService.create(dto);
    }


    //UPDATE
    @MutationMapping
    public ConvocatoriaResponseDTO actualizarConvocatoria(@Argument Long id, @Argument("input") ConvocatoriaRequestDTO dto) {
        return convocatoriaService.update(id, dto);
    }

    //SOFT-DELETE
    @MutationMapping
    public Boolean eliminarConvocatoria(@Argument Long id) {
        convocatoriaService.delete(id);
        return true;
    }


}


