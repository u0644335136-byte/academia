package com.jdk21.academia.features.profesor.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jdk21.academia.domain.Profesor;
import com.jdk21.academia.features.profesor.dto.ProfesorDto;
import com.jdk21.academia.features.profesor.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    @Transactional
    public ProfesorDto crearProfesor(ProfesorDto dto) {
        if (profesorRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("El email ya existe.");
        }

        try {

            Profesor profesor = Profesor.builder()
                    .activo(true)
                    .fechaCreacion(LocalDateTime.now())
                    .fechaActualizacion(LocalDateTime.now())
                    .nombre(dto.getNombre())
                    .apellidos(dto.getApellidos())
                    .telefono(dto.getTelefono())
                    .email(dto.getEmail())
                    .direccion(dto.getDireccion())
                    .localidad(dto.getLocalidad())
                    .provincia(dto.getProvincia())
                    .fechaNacimiento(dto.getFechaNacimiento())
                    .contrasenia(dto.getContrasenia())
                    .build();
            Profesor saved = profesorRepository.save(profesor);
            return mapToDto(saved);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear profesor: " + e.getMessage());
        }
    }

    @Transactional
    public Optional<ProfesorDto> actualizarProfesor(Long id, ProfesorDto dto) {
        return profesorRepository.findById(id).map(profesor -> {
            if (!profesor.getEmail().equals(dto.getEmail()) && profesorRepository.existsByEmail(dto.getEmail())) {
                throw new IllegalArgumentException("El nuevo email ya existe.");
            }
            try {
                profesor.setFechaActualizacion(LocalDateTime.now());
                profesor.setNombre(dto.getNombre());
                profesor.setApellidos(dto.getApellidos());
                profesor.setTelefono(dto.getTelefono());
                profesor.setEmail(dto.getEmail());
                profesor.setDireccion(dto.getDireccion());
                profesor.setLocalidad(dto.getLocalidad());
                profesor.setProvincia(dto.getProvincia());
                profesor.setFechaNacimiento(null);
                profesor.setContrasenia(dto.getContrasenia());
                Profesor saved = profesorRepository.save(profesor);
                return mapToDto(saved);
            } catch (Exception e) {
                throw new RuntimeException("Error al actualizar profesor: " + e.getMessage());
            }
        });
    }

    public Optional<Profesor> eliminarProfesor(Long id) {
        return profesorRepository.findById(id).map(profesor -> {
            try {
                profesor.setActivo(false);
                profesor.setFechaActualizacion(LocalDateTime.now());
                return profesorRepository.save(profesor);
            } catch (Exception e) {
                throw new RuntimeException("Error al eliminar profesor: " + e.getMessage());
            }
        });
    }

    // Nuevo método: findById
    public Optional<ProfesorDto> findById(Long id) {
        Profesor profesor = profesorRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Profesor con ID " + id + " no encontrado."));

        if (profesor.getActivo()) {
            return Optional.of(mapToDto(profesor));
        }

        return Optional.empty();
    }

    // Nuevo método: findAll
    public List<ProfesorDto> findAll() {
        List<Profesor> all = profesorRepository.findAll();
        List<Profesor> activos = all.stream()
                .filter(Profesor::getActivo)
                .collect(Collectors.toList());

        return activos.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ProfesorDto mapToDto(Profesor profesor) {
        return ProfesorDto.builder()
                .idProfesor(profesor.getIdProfesor())
                .nombre(profesor.getNombre())
                .apellidos(profesor.getApellidos())
                .telefono(profesor.getTelefono())
                .email(profesor.getEmail())
                .direccion(profesor.getDireccion())
                .localidad(profesor.getLocalidad())
                .provincia(profesor.getProvincia())
                .fechaNacimiento(profesor.getFechaNacimiento())
                .contrasenia(profesor.getContrasenia())
                .activo(profesor.getActivo())
                .build();
    }

}
