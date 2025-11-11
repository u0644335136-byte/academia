package com.jdk21.academia.features.convocatoria.service;

import org.springframework.stereotype.Service;

import com.jdk21.academia.features.convocatoria.repository.ConvocatoriaRepository;
import com.jdk21.academia.features.convocatoria.repository.ViewConvocatoriaRepository;

@Service
public class ConvocatoriaService {

    private final ConvocatoriaRepository repository;
    private final ViewConvocatoriaRepository viewRepository;


    public ConvocatoriaService(ConvocatoriaRepository repository, ViewConvocatoriaRepository viewRepository){
        this.repository=repository;
        this.viewRepository = viewRepository;
    }

    // @Transactional
    // public MatriculaDTO crearMatricula(CrearMatriculaDTO crearMatriculaDTO){

    // }


}
