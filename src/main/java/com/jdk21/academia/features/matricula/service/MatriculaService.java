package com.jdk21.academia.features.matricula.service;

import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.matricula.dto.MatriculaRequestDTO;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;
import com.jdk21.academia.features.matricula.mapper.MatriculaMapper;

import org.springframework.stereotype.Service;

@Service
public class MatriculaService extends BaseService<Matricula, MatriculaRequestDTO, MatriculaResponseDTO, Long> {

    public MatriculaService(BaseRepository<Matricula> repository, MatriculaMapper mapper) {
        super(repository, mapper);
    }
}