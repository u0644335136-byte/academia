package com.jdk21.academia.features.convocatoria.service;

import org.springframework.stereotype.Service;

import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaRequestDTO;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;
import com.jdk21.academia.features.convocatoria.mapper.ConvocatoriaMapper;

@Service
public class ConvocatoriaService extends BaseService<Convocatoria, ConvocatoriaRequestDTO, ConvocatoriaResponseDTO, Long> {

    public ConvocatoriaService(BaseRepository<Convocatoria> repository, ConvocatoriaMapper mapper) {
        super(repository, mapper);
    }
}
