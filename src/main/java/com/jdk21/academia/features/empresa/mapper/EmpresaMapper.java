package com.jdk21.academia.features.empresa.mapper;

import com.jdk21.academia.domain.Empresa;
import com.jdk21.academia.features.baseFeature.mapper.BaseMapper;
import com.jdk21.academia.features.empresa.dto.EmpresaRequestDTO;
import com.jdk21.academia.features.empresa.dto.EmpresaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmpresaMapper extends BaseMapper<Empresa, EmpresaRequestDTO, EmpresaResponseDTO> {

    @Override
    Empresa toEntity(EmpresaRequestDTO dto);

    @Override
    EmpresaResponseDTO toDto(Empresa entity);

    @Override
    void updateEntityFromDto(EmpresaRequestDTO dto, @MappingTarget Empresa entity);
}


