package com.jdk21.academia.features.alumno.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jdk21.academia.domain.Alumno;
import com.jdk21.academia.features.alumno.dto.AlumnoDto;
import com.jdk21.academia.features.alumno.mapper.AlumnoMapper;
import com.jdk21.academia.features.alumno.repository.AlumnoRepository;

@Service
public class AlumnoService {

    // @Autowired: Spring inyecta el repository y mapper automáticamente.
    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AlumnoMapper alumnoMapper;

    // Método para crear: Recibe DTO validado, convierte a entidad, comprueba email único, salva y retorna DTO.
    public AlumnoDto createAlumno(AlumnoDto alumnoDTO) {
        // Si email existe, lanza excepción
        if (alumnoRepository.findByEmail(alumnoDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe: " + alumnoDTO.getEmail());
        }

        // Convertimos DTO a entidad con mapper.
        Alumno alumno = alumnoMapper.toEntity(alumnoDTO);

        // si activo es null, setear default (pero validado en DTO).
        if (alumno.getActivo() == null) {
            alumno.setActivo(true); // Default a activo.
        }

        Alumno savedAlumno = alumnoRepository.save(alumno);

        // Convertimos de vuelta a DTO.
        return alumnoMapper.toDTO(savedAlumno);
    }

    // Leer uno
    public Optional<AlumnoDto> getAlumnoById(Long id) {

        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);

        // Si presente, mapea a DTO; sino, retorna empty.
        // Usamos map() de Optional: aplica función si valor presente.
        return alumnoOpt.map(alumnoMapper::toDTO);
    }

    // Leer todos
    public List<AlumnoDto> getAllAlumnos() {

        List<Alumno> alumnos = alumnoRepository.findAll();

        // Mapea lista con mapper.
        return alumnoMapper.toDTOList(alumnos);
    }

    // Actualizar
    public AlumnoDto updateAlumno(Long id, AlumnoDto alumnoDTO) {
    

        Alumno existingAlumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));

        // Comprobamos email: Si cambiado y existe otro con mismo email.
        if (!existingAlumno.getEmail().equals(alumnoDTO.getEmail()) &&
            alumnoRepository.findByEmail(alumnoDTO.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe: ");
        }

        // Nota: Podríamos agregar @Mapping(target="...", source="...") en mapper para update, pero por simplicidad manual.
        existingAlumno.setNombre(alumnoDTO.getNombre());
        existingAlumno.setApellidos(alumnoDTO.getApellidos());
        existingAlumno.setTelefono(alumnoDTO.getTelefono());
        existingAlumno.setEmail(alumnoDTO.getEmail());
        existingAlumno.setContrasenia(alumnoDTO.getContrasenia());
        existingAlumno.setFecha_nacimiento(alumnoDTO.getFecha_nacimiento());
        existingAlumno.setLocalidad(alumnoDTO.getLocalidad());
        existingAlumno.setProvincia(alumnoDTO.getProvincia());
        existingAlumno.setActivo(alumnoDTO.getActivo());

        Alumno updatedAlumno = alumnoRepository.save(existingAlumno);

        return alumnoMapper.toDTO(updatedAlumno);
    }

    // Eliminar
    public void deleteAlumno(Long id) {
        if (!alumnoRepository.existsById(id)) {
            throw new RuntimeException("Alumno no encontrado con id: " + id);
        }
        alumnoRepository.deleteById(id);
    }
}
