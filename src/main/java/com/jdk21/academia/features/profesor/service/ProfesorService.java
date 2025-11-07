package com.jdk21.academia.features.profesor.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import com.jdk21.academia.domain.Profesor;
import com.jdk21.academia.features.profesor.dto.ProfesorDto;
import com.jdk21.academia.features.profesor.repository.ProfesorRepository;
import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ProfesorService {

    private final ProfesorRepository profesorRepository;

    public Profesor crearProfesor(ProfesorDto dto){
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
            return profesorRepository.save(profesor);
    }

    public Optional<Profesor> actualizarProfesor(Long id, ProfesorDto dto){
            return profesorRepository.findById(id).map(profesor -> {
                profesor.setFechaActualizacion(LocalDateTime.now());
                profesor.setNombre(dto.getNombre());
                profesor.setApellidos(dto.getApellidos());
                profesor.setTelefono(dto.getTelefono());
                profesor.setEmail(dto.getEmail());
                profesor.setDireccion(dto.getDireccion());
                profesor.setLocalidad(dto.getLocalidad());
                profesor.setProvincia(dto.getProvincia());
                profesor.setFechaNacimiento(dto.getFechaNacimiento());
                profesor.setContrasenia(dto.getContrasenia());
                return profesorRepository.save(profesor);
            });
    }

    public Optional<Profesor> eliminarProfesor(Long id){
        return profesorRepository.findById(id).map(profesor ->{
            profesor.setActivo(false);
            profesor.setFechaActualizacion(LocalDateTime.now());
            return profesorRepository.save(profesor);
        });
    }
    
}
