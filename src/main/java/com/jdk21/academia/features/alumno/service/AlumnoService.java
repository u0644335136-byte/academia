package com.jdk21.academia.features.alumno.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.jdk21.academia.domain.Alumno;
import com.jdk21.academia.features.alumno.dto.AlumnoCreateInputDto;
import com.jdk21.academia.features.alumno.dto.AlumnoDto;
import com.jdk21.academia.features.alumno.dto.AlumnoPageDto;
import com.jdk21.academia.features.alumno.dto.AlumnoUpdateInputDto;
import com.jdk21.academia.features.alumno.mapper.AlumnoMapper;
import com.jdk21.academia.features.alumno.repository.AlumnoRepository;
import com.jdk21.academia.features.alumno.repository.AlumnoSpecification;

@Service
public class AlumnoService {

    @Autowired
    private AlumnoRepository alumnoRepository;

    @Autowired
    private AlumnoMapper alumnoMapper;

    /**
     * Crea un nuevo alumno.
     * Valida que el email sea único y que la fecha de nacimiento sea en el pasado.
     */
    public AlumnoDto createAlumno(AlumnoCreateInputDto alumnoCreateInputDto) {
        if (alumnoRepository.findByEmail(alumnoCreateInputDto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe: " + alumnoCreateInputDto.getEmail());
        }
        validateFechaNacimiento(alumnoCreateInputDto.getFecha_nacimiento());
        Alumno alumno = alumnoMapper.toEntityFromCreateInput(alumnoCreateInputDto);
        if (alumno.getActivo() == null) {
            alumno.setActivo(true);
        }
        Alumno savedAlumno = alumnoRepository.save(alumno);
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

    /**
     * Actualiza un alumno existente.
     * Solo actualiza los campos proporcionados (actualización parcial).
     * Valida la fecha de nacimiento solo si se proporciona una nueva.
     */
    public AlumnoDto updateAlumno(Long id, AlumnoUpdateInputDto alumnoUpdateInputDto) {
        Alumno existingAlumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));
        if (alumnoUpdateInputDto.getEmail() != null && 
            !existingAlumno.getEmail().equals(alumnoUpdateInputDto.getEmail()) &&
            alumnoRepository.findByEmail(alumnoUpdateInputDto.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya existe: " + alumnoUpdateInputDto.getEmail());
        }
        if (alumnoUpdateInputDto.getFecha_nacimiento() != null) {
            validateFechaNacimiento(alumnoUpdateInputDto.getFecha_nacimiento());
        }
        updateAlumnoFields(existingAlumno, alumnoUpdateInputDto);
        Alumno updatedAlumno = alumnoRepository.save(existingAlumno);
        return alumnoMapper.toDTO(updatedAlumno);
    }

    /**
     * Valida que la fecha de nacimiento sea en el pasado.
     */
    private void validateFechaNacimiento(LocalDate fechaNacimiento) {
        if (fechaNacimiento != null && !fechaNacimiento.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("La fecha de nacimiento debe ser en el pasado");
        }
    }

    /**
     * Actualiza solo los campos proporcionados en el DTO de actualización.
     */
    private void updateAlumnoFields(Alumno existingAlumno, AlumnoUpdateInputDto updateDto) {
        if (updateDto.getNombre() != null) {
            existingAlumno.setNombre(updateDto.getNombre());
        }
        if (updateDto.getApellidos() != null) {
            existingAlumno.setApellidos(updateDto.getApellidos());
        }
        if (updateDto.getTelefono() != null) {
            existingAlumno.setTelefono(updateDto.getTelefono());
        }
        if (updateDto.getEmail() != null) {
            existingAlumno.setEmail(updateDto.getEmail());
        }
        if (updateDto.getContrasenia() != null) {
            existingAlumno.setContrasenia(updateDto.getContrasenia());
        }
        if (updateDto.getFecha_nacimiento() != null) {
            existingAlumno.setFecha_nacimiento(updateDto.getFecha_nacimiento());
        }
        if (updateDto.getLocalidad() != null) {
            existingAlumno.setLocalidad(updateDto.getLocalidad());
        }
        if (updateDto.getProvincia() != null) {
            existingAlumno.setProvincia(updateDto.getProvincia());
        }
        if (updateDto.getActivo() != null) {
            existingAlumno.setActivo(updateDto.getActivo());
        }
    }

    /**
     * Busca alumnos con filtros de búsqueda, estado activo y paginación.
     */
    public AlumnoPageDto searchAlumnos(String search, Boolean activo, Integer page, Integer size) {
        int pageNumber = page != null && page > 0 ? page - 1 : 0;
        int pageSize = size != null && size > 0 ? size : 10;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        String searchTerm = search != null && !search.trim().isEmpty() ? search.trim() : null;
        Specification<Alumno> spec = AlumnoSpecification.searchAndFilter(searchTerm, activo);
        Page<Alumno> pageResult = alumnoRepository.findAll(spec, pageable);
        List<AlumnoDto> alumnosDto = alumnoMapper.toDTOList(pageResult.getContent());
        AlumnoPageDto result = new AlumnoPageDto();
        result.setAlumnos(alumnosDto);
        result.setTotalElements(pageResult.getTotalElements());
        result.setTotalPages(pageResult.getTotalPages());
        result.setCurrentPage(pageResult.getNumber() + 1);
        result.setPageSize(pageResult.getSize());
        return result;
    }

    // Eliminar
    public void deleteAlumno(Long id) {
        Alumno existingAlumno = alumnoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado con id: " + id));
        if (Boolean.FALSE.equals(existingAlumno.getActivo())) {
            return;
        }
        existingAlumno.setActivo(false);
        alumnoRepository.save(existingAlumno);
    }
}
