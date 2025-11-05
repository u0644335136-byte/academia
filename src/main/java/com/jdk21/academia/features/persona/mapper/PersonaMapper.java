package com.jdk21.academia.features.persona.mapper;

import java.util.Set;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import com.jdk21.academia.domain.Persona;
import com.jdk21.academia.domain.Rol;
import com.jdk21.academia.features.persona.dto.CreatePersonaDTO;
import com.jdk21.academia.features.persona.dto.PersonaDTO;
import com.jdk21.academia.features.persona.dto.UpdatePersonaDTO;

/**
 * Mapper para convertir entre entidades Persona y DTOs.
 * MapStruct genera automáticamente la implementación de esta interfaz en tiempo
 * de compilación.
 */
@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface PersonaMapper {

    /**
     * Convierte una entidad Persona a PersonaDTO.
     * Mapea campos básicos y convierte la colección de roles a nombres de roles.
     */
    @Mapping(target = "idPersona", source = "idPersona")
    @Mapping(target = "idDiscapacidad", source = "idDiscapacidad.idDiscapacidad")
    @Mapping(target = "roles", expression = "java(extractRoleNames(persona))")
    PersonaDTO toDTO(Persona persona);

    /**
     * Convierte CreatePersonaDTO a entidad Persona.
     * Se usa al crear una nueva persona.
     */
    @Mapping(target = "idPersona", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "idDiscapacidad", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    Persona toEntity(CreatePersonaDTO dto);

    /**
     * Actualiza una entidad Persona existente con datos de UpdatePersonaDTO.
     * Solo actualiza los campos que no son null en el DTO.
     */
    @Mapping(target = "idPersona", ignore = true)
    @Mapping(target = "roles", ignore = true)
    @Mapping(target = "idDiscapacidad", ignore = true)
    @Mapping(target = "fechaCreacion", ignore = true)
    @Mapping(target = "fechaActualizacion", ignore = true)
    void updateEntityFromDTO(UpdatePersonaDTO dto, @MappingTarget Persona persona);

    /**
     * Método helper para extraer los nombres de los roles de una Persona.
     * Este método se usa en la expresión de MapStruct.
     */
    default Set<String> extractRoleNames(Persona persona) {
        if (persona == null || persona.getRoles() == null) {
            return Set.of();
        }
        return persona.getRoles().stream()
                .map(PersonaRol -> PersonaRol.getRol())
                .filter(rol -> rol != null)
                .map(Rol::getCodigo)
                .filter(codigo -> codigo != null)
                .collect(Collectors.toSet());
    }
}
