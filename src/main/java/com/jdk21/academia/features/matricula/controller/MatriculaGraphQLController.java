package com.jdk21.academia.features.matricula.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.jdk21.academia.features.alumno.dto.AlumnoDto;
import com.jdk21.academia.features.alumno.repository.AlumnoRepository;
import com.jdk21.academia.features.alumno.mapper.AlumnoMapper;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;
import com.jdk21.academia.features.convocatoria.service.ConvocatoriaService;
import com.jdk21.academia.features.matricula.dto.MatriculaRequestDTO;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;
import com.jdk21.academia.features.matricula.service.MatriculaService;

@Controller
public class MatriculaGraphQLController {

    private final MatriculaService matriculaService;
    
    @Autowired
    private ConvocatoriaService convocatoriaService;
    
    @Autowired
    private AlumnoRepository alumnoRepository;
    
    @Autowired
    private AlumnoMapper alumnoMapper;

    public MatriculaGraphQLController(MatriculaService matriculaService){
        this.matriculaService = matriculaService;
    }

    //GET
    @QueryMapping
    public List<MatriculaResponseDTO> retornarTodasMatriculas(){
        return matriculaService.getAll();
    }

    @QueryMapping Optional<MatriculaResponseDTO> matriculaPorId(@Argument Long id){
        return matriculaService.getById(id);
    }


    //POST
    @MutationMapping
    public MatriculaResponseDTO crearMatricula(@Argument("input") MatriculaRequestDTO dto) {
        return matriculaService.create(dto);
    }


    //UPDATE
    @MutationMapping
    public MatriculaResponseDTO actualizarMatricula(@Argument Long id, @Argument("input") MatriculaRequestDTO dto) {
        return matriculaService.update(id, dto);
    }

    //SOFT-DELETE
    @MutationMapping
    public Boolean eliminarMatricula(@Argument Long id) {
        matriculaService.delete(id);
        return true;
    }


    //Actualizar nota
    @MutationMapping
    public MatriculaResponseDTO actualizarCalificacion(@Argument Long id, @Argument int nota) {
        
        return matriculaService.actualizarCalificacion(id, nota);
    }

    // ============================================
    // RESOLVERS PARA QUERIES ANIDADAS (NUEVA FUNCIONALIDAD)
    // ============================================
    // Estos métodos permiten navegar desde una matrícula hacia sus entidades relacionadas.
    //
    // Ejemplo de query que activa estos resolvers:
    //   query {
    //     matriculaPorId(id: "456") {
    //       codigo
    //       precio
    //       convocatoria {  # ← Activa el resolver convocatoria()
    //         codigo
    //         fechaInicio
    //         cursoNombre
    //       }
    //       alumno {  # ← Activa el resolver alumno()
    //         nombre
    //         apellidos
    //         email
    //         telefono
    //       }
    //     }
    //   }
    //
    // VENTAJAS sobre tener solo idConvocatoria y idAlumno:
    // - No necesitas hacer peticiones adicionales para obtener datos relacionados
    // - El cliente decide qué campos necesita de la convocatoria/alumno
    // - Una sola query en lugar de múltiples peticiones HTTP
    // ============================================

    /**
     * Resolver para el campo "convocatoria" en MatriculaDto
     * Se ejecuta cuando el cliente solicita la convocatoria completa de una matrícula
     * 
     * @param matricula La matrícula padre (GraphQL la pasa automáticamente)
     * @return La convocatoria asociada a esta matrícula, o null si no existe
     */
    @SchemaMapping(typeName = "MatriculaDto", field = "convocatoria")
    public ConvocatoriaResponseDTO convocatoria(MatriculaResponseDTO matricula) {
        // Si no hay ID de convocatoria, retornamos null
        if (matricula.idConvocatoria() == null) {
            return null;
        }
        
        // Obtenemos la convocatoria completa usando el servicio
        // Esto es mejor que solo tener idConvocatoria porque el cliente puede
        // obtener todos los datos de la convocatoria en la misma query
        return convocatoriaService.getById(matricula.idConvocatoria())
                .orElse(null);
    }

    /**
     * Resolver para el campo "alumno" en MatriculaDto
     * Se ejecuta cuando el cliente solicita el alumno completo de una matrícula
     * 
     * @param matricula La matrícula padre (GraphQL la pasa automáticamente)
     * @return El alumno asociado a esta matrícula, o null si no existe
     */
    @SchemaMapping(typeName = "MatriculaDto", field = "alumno")
    public AlumnoDto alumno(MatriculaResponseDTO matricula) {
        // Si no hay ID de alumno, retornamos null
        if (matricula.idAlumno() == null) {
            return null;
        }
        
        // Obtenemos el alumno completo y lo convertimos a DTO
        // Esto es mejor que solo tener idAlumno y alumnoEmail porque el cliente
        // puede obtener todos los datos del alumno (nombre, apellidos, teléfono, etc.)
        return alumnoRepository.findById(matricula.idAlumno())
                .map(alumnoMapper::toDTO)
                .orElse(null);
    }

}
