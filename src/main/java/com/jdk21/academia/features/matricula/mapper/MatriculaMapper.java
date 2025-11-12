package com.jdk21.academia.features.matricula.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.jdk21.academia.domain.Alumno;
import com.jdk21.academia.domain.Convocatoria;
import com.jdk21.academia.domain.Matricula;
import com.jdk21.academia.features.baseFeature.mapper.BaseMapper;
import com.jdk21.academia.features.matricula.dto.MatriculaRequestDTO;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;

@Mapper(componentModel = "spring")
public interface MatriculaMapper extends BaseMapper<Matricula, MatriculaRequestDTO, MatriculaResponseDTO> {

    @Override
    @Mapping(target = "convocatoria", source = "idConvocatoria")
    @Mapping(target = "alumno", source = "idAlumno")
    Matricula toEntity(MatriculaRequestDTO dto);

    @Override
    @Mapping(target = "idConvocatoria", source = "convocatoria.id")
    @Mapping(target = "convocatoriaCodigo", source = "convocatoria.codigo")
    @Mapping(target = "idAlumno", source = "alumno.id_alumno")
    @Mapping(target = "alumnoEmail", source = "alumno.email")
    MatriculaResponseDTO toDto(Matricula entity);


    @Override
    void updateEntityFromDto(MatriculaRequestDTO dto, @MappingTarget Matricula entity);


    // Mapeo entidades/llaves foraneas
    default Convocatoria mapConvocatoria(Long id) {
        if (id == null) return null;
        Convocatoria convocatoria = new Convocatoria();
        convocatoria.setId(id);
        return convocatoria;
    }

    default Alumno mapAlumno(Long id) {
        if (id == null) return null;
        Alumno a = new Alumno();
        a.setId_alumno(id);
        return a;
    }

}
