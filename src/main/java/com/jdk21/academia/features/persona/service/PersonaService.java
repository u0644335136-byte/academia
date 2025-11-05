package com.jdk21.academia.features.persona.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdk21.academia.domain.Discapacidad;
import com.jdk21.academia.domain.Persona;
import com.jdk21.academia.domain.PersonaRol;
import com.jdk21.academia.domain.Rol;
import com.jdk21.academia.features.persona.dto.AsignarRolDTO;
import com.jdk21.academia.features.persona.dto.CreatePersonaDTO;
import com.jdk21.academia.features.persona.dto.PersonaDTO;
import com.jdk21.academia.features.persona.dto.UpdatePersonaDTO;
import com.jdk21.academia.features.persona.mapper.PersonaMapper;
import com.jdk21.academia.features.persona.repository.DiscapacidadRepository;
import com.jdk21.academia.features.persona.repository.PersonaRepository;
import com.jdk21.academia.features.persona.repository.RolRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonaService {
    
    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final DiscapacidadRepository discapacidadRepository;
    private final PersonaMapper personaMapper;

    @Transactional
    public PersonaDTO createPersona(CreatePersonaDTO createDTO) {
        
        // Validar unicidad de teléfono
        if (createDTO.telefono() != null) {
            Optional<Persona> existingByTelefono = personaRepository.findByTelefono(createDTO.telefono());
            if (existingByTelefono.isPresent()) {
                throw new IllegalArgumentException("Ya existe una persona con el teléfono: " + createDTO.telefono());
            }
        }
        
        // Convertir DTO a Entity
        Persona persona = personaMapper.toEntity(createDTO);
        
        // Asignar discapacidad si se proporciona
        if (createDTO.idDiscapacidad() != null) {
            Optional<Discapacidad> discapacidad = discapacidadRepository.findById(createDTO.idDiscapacidad());
            persona.setIdDiscapacidad(discapacidad.orElse(null));
        }
        
        // Establecer valores por defecto
        if (persona.getActivo() == null) {
            persona.setActivo(true);
        }
        
        // Guardar la persona (sin roles todavía)
        Persona personaGuardada = personaRepository.save(persona);
        
        // Asignar roles si se proporcionaron
        if (createDTO.roles() != null && !createDTO.roles().isEmpty()) {
            asignarRoles(personaGuardada.getIdPersona(), createDTO.roles());
            // Recargar la persona con los roles
            personaGuardada = personaRepository.findById(personaGuardada.getIdPersona())
                .orElseThrow(() -> new RuntimeException("Error al recargar la persona creada"));
        }
        
        return personaMapper.toDTO(personaGuardada);
    }
    
    @Transactional(readOnly = true)
    public List<PersonaDTO> getAllPersonas() {
        return personaRepository.findAll().stream()
            .map(personaMapper::toDTO)
            .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public PersonaDTO getPersonaById(Long id) {
        Persona persona = personaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la persona con ID: " + id));
        return personaMapper.toDTO(persona);
    }
    
    @Transactional
    public PersonaDTO updatePersona(Long id, UpdatePersonaDTO updateDTO) {        
        Persona persona = personaRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la persona con ID: " + id));
        
        // Validar unicidad de teléfono si se está cambiando
        if (updateDTO.telefono() != null && !updateDTO.telefono().equals(persona.getTelefono())) {
            Optional<Persona> existingByTelefono = personaRepository.findByTelefono(updateDTO.telefono());
            if (existingByTelefono.isPresent()) {
                throw new IllegalArgumentException("Ya existe otra persona con el teléfono: " + updateDTO.telefono());
            }
        }
        
        // Actualizar campos del DTO
        personaMapper.updateEntityFromDTO(updateDTO, persona);
        
        // Actualizar discapacidad si se proporciona
        if (updateDTO.idDiscapacidad() != null) {
            Optional<Discapacidad> discapacidad = discapacidadRepository.findById(updateDTO.idDiscapacidad());
            persona.setIdDiscapacidad(discapacidad.orElse(null));
        }
        
        // Actualizar roles si se proporcionan
        if (updateDTO.roles() != null) {
            // Eliminar roles existentes
            persona.getRoles().clear();
            personaRepository.save(persona);
            // Asignar nuevos roles
            asignarRoles(id, updateDTO.roles());
            // Recargar la persona
            persona = personaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Error al recargar la persona actualizada"));
        }
        
        Persona personaActualizada = personaRepository.save(persona);
        return personaMapper.toDTO(personaActualizada);
    }
    
    @Transactional
    public void deletePersona(Long id) {        
        if (!personaRepository.existsById(id)) {
            throw new IllegalArgumentException("No se encontró la persona con ID: " + id);
        }
        personaRepository.deleteById(id);
    }

    @Transactional
    public void asignarRoles(Long idPersona, Set<String> codigosRoles) {        
        Persona persona = personaRepository.findById(idPersona)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la persona con ID: " + idPersona));
        
        for (String codigoRol : codigosRoles) {
            Rol rol = rolRepository.findByCodigo(codigoRol)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró el rol con código: " + codigoRol));
            
            // Verificar si el rol ya está asignado
            boolean yaAsignado = persona.getRoles().stream()
                .anyMatch(pr -> pr.getRol().getIdRol().equals(rol.getIdRol()));
            
            if (!yaAsignado) {
                PersonaRol personaRol = new PersonaRol();
                personaRol.setIdPersona(persona.getIdPersona());
                personaRol.setIdRol(rol.getIdRol());
                personaRol.setPersona(persona);
                personaRol.setRol(rol);
                
                persona.getRoles().add(personaRol);
            }
        }
        
        personaRepository.save(persona);
    }
    
    @Transactional
    public void asignarRol(AsignarRolDTO asignarRolDTO) {
        asignarRoles(asignarRolDTO.idPersona(), Set.of(asignarRolDTO.codigoRol()));
    }
    

    @Transactional
    public void removerRol(Long idPersona, String codigoRol) {        
        Persona persona = personaRepository.findById(idPersona)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró la persona con ID: " + idPersona));
        
        Rol rol = rolRepository.findByCodigo(codigoRol)
            .orElseThrow(() -> new IllegalArgumentException("No se encontró el rol con código: " + codigoRol));
        
        // Eliminar el PersonaRol correspondiente
        persona.getRoles().removeIf(pr -> pr.getRol().getIdRol().equals(rol.getIdRol()));
        
        personaRepository.save(persona);
    }
}

