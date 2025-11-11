package com.jdk21.academia.features.empresa.service;

import com.jdk21.academia.domain.Empresa;
import com.jdk21.academia.features.baseFeature.repository.BaseRepository;
import com.jdk21.academia.features.baseFeature.service.BaseService;
import com.jdk21.academia.features.empresa.dto.EmpresaRequestDTO;
import com.jdk21.academia.features.empresa.dto.EmpresaResponseDTO;
import com.jdk21.academia.features.empresa.mapper.EmpresaMapper;
import org.springframework.stereotype.Service;

@Service
public class EmpresaService extends BaseService<Empresa, EmpresaRequestDTO, EmpresaResponseDTO, Long> {

    public EmpresaService(BaseRepository<Empresa> repository, EmpresaMapper mapper) {
        super(repository, mapper);
    }
}