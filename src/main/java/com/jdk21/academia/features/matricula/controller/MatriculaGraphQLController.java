package com.jdk21.academia.features.matricula.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.jdk21.academia.features.matricula.dto.MatriculaRequestDTO;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;
import com.jdk21.academia.features.matricula.service.MatriculaService;

@Controller
public class MatriculaGraphQLController {

    private final MatriculaService matriculaService;

    public MatriculaGraphQLController(MatriculaService matriculaService){
        this.matriculaService = matriculaService;
    }

    //GET
    @QueryMapping
    public List<MatriculaResponseDTO> retornarTodasMatriculas(){
        return matriculaService.getAll();
    }

    @QueryMapping Optional<MatriculaResponseDTO> matriculaPorId(@Argument Long id){
        return matriculaService.getById(id);
    }


    //POST
    @MutationMapping
    public MatriculaResponseDTO crearMatricula(@Argument("input") MatriculaRequestDTO dto) {
        return matriculaService.create(dto);
    }


    //UPDATE
    @MutationMapping
    public MatriculaResponseDTO actualizarMatricula(@Argument Long id, @Argument("input") MatriculaRequestDTO dto) {
        return matriculaService.update(id, dto);
    }

    //SOFT-DELETE
    @MutationMapping
    public Boolean eliminarMatricula(@Argument Long id) {
        matriculaService.delete(id);
        return true;
    }


    //Actualizar nota
    @MutationMapping
    public MatriculaResponseDTO actualizarCalificacion(@Argument Long id, @Argument int nota) {
        
        return matriculaService.actualizarCalificacion(id, nota);
    }


}
