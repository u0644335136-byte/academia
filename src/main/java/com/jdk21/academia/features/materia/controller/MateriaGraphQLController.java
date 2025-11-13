package com.jdk21.academia.features.materia.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.jdk21.academia.features.materia.dto.MateriaDto;
import com.jdk21.academia.features.materia.service.MateriaService;

@Controller
public class MateriaGraphQLController {

    private final MateriaService materiaService;

    public MateriaGraphQLController(MateriaService materiaService){
        this.materiaService = materiaService;
    }

    //GET
    @QueryMapping
    public List<MateriaDto> retornarTodasMaterias(){
        return materiaService.obtenerTodas();
    }

    @QueryMapping Optional<MateriaDto> materiaPorId(@Argument Long id){
        return materiaService.obtenerPorId(id);
    }


    //POST
    @MutationMapping
    public MateriaDto crearMateria(@Argument("input") MateriaDto dto) {
        return materiaService.crearMateria(dto);
    }


    //UPDATE
    public Optional<MateriaDto> crearMateria(@Argument Long id, @Argument("input") MateriaDto dto){
        return materiaService.actualizarMateria(id, dto);
    }

    //SOFT-DELETE
    public Optional<MateriaDto> eliminarMateria(@Argument Long id) {
        return materiaService.eliminarMateria(id);
    }



}






