package com.jdk21.academia.features.convocatoria.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import com.jdk21.academia.features.alumno.dto.AlumnoDto;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaRequestDTO;
import com.jdk21.academia.features.convocatoria.dto.ConvocatoriaResponseDTO;
import com.jdk21.academia.features.convocatoria.service.ConvocatoriaService;
import com.jdk21.academia.features.matricula.dto.MatriculaResponseDTO;
import com.jdk21.academia.features.matricula.service.MatriculaService;

@Controller
public class ConvocatoriaGraphQLController {

    private final ConvocatoriaService convocatoriaService;
    private final MatriculaService matriculaService;

    public ConvocatoriaGraphQLController(ConvocatoriaService convocatoriaService, MatriculaService matriculaService){
        this.convocatoriaService = convocatoriaService;
        this.matriculaService = matriculaService;
    }

    //GET
    @QueryMapping
    public List<ConvocatoriaResponseDTO> retornarTodasConvocatorias(){
        return convocatoriaService.getAll();
    }

    @QueryMapping Optional<ConvocatoriaResponseDTO> convocatoriaPorId(@Argument Long id){
        return convocatoriaService.getById(id);
    }


    //POST
    @MutationMapping
    public ConvocatoriaResponseDTO crearConvocatoria(@Argument("input") ConvocatoriaRequestDTO dto) {
        return convocatoriaService.create(dto);
    }


    //UPDATE
    @MutationMapping
    public ConvocatoriaResponseDTO actualizarConvocatoria(@Argument Long id, @Argument("input") ConvocatoriaRequestDTO dto) {
        return convocatoriaService.update(id, dto);
    }

    //SOFT-DELETE
    @MutationMapping
    public Boolean eliminarConvocatoria(@Argument Long id) {
        convocatoriaService.delete(id);
        return true;
    }

    // ============================================
    // RESOLVERS PARA QUERIES ANIDADAS (NUEVA FUNCIONALIDAD)
    // ============================================
    // Estos métodos se ejecutan AUTOMÁTICAMENTE cuando GraphQL necesita
    // resolver campos relacionados (relaciones) en una query anidada.
    //
    // Ejemplo de query que activa estos resolvers:
    //   query {
    //     convocatoriaPorId(id: "123") {
    //       codigo
    //       matriculas {  # ← Activa el resolver matriculas()
    //         codigo
    //         precio
    //         alumno {  # ← Activa el resolver alumno() en MatriculaGraphQLController
    //           nombre
    //         }
    //       }
    //       alumnos {  # ← Activa el resolver alumnos()
    //         nombre
    //         email
    //       }
    //     }
    //   }
    //
    // ¿POR QUÉ ES MEJOR QUE REST?
    // - REST: Necesitarías 3+ peticiones HTTP separadas
    // - GraphQL: Una sola petición, el cliente decide qué necesita
    // - GraphQL solo ejecuta los resolvers para los campos que el cliente solicita
    //   (si no pides "matriculas", no se ejecuta el método matriculas())
    // ============================================

    /**
     * Resolver para el campo "matriculas" en ConvocatoriaDto
     * Se ejecuta cuando el cliente solicita las matrículas de una convocatoria
     * 
     * @param convocatoria La convocatoria padre (GraphQL la pasa automáticamente)
     * @return Lista de matrículas asociadas a esta convocatoria
     */
    @SchemaMapping(typeName = "ConvocatoriaDto", field = "matriculas")
    public List<MatriculaResponseDTO> matriculas(ConvocatoriaResponseDTO convocatoria) {
        // GraphQL pasa automáticamente el objeto ConvocatoriaDto como parámetro
        // porque estamos resolviendo el campo "matriculas" de ese tipo
        return matriculaService.getMatriculasByConvocatoriaId(convocatoria.id());
    }

    /**
     * Resolver para el campo "alumnos" en ConvocatoriaDto
     * Se ejecuta cuando el cliente solicita los alumnos de una convocatoria
     * 
     * Este es un ejemplo de query compuesta: convocatoria -> matrículas -> alumnos
     * Útil para reportes: "¿Qué alumnos están matriculados en esta convocatoria?"
     * 
     * @param convocatoria La convocatoria padre (GraphQL la pasa automáticamente)
     * @return Lista de alumnos únicos que tienen matrícula en esta convocatoria
     */
    @SchemaMapping(typeName = "ConvocatoriaDto", field = "alumnos")
    public List<AlumnoDto> alumnos(ConvocatoriaResponseDTO convocatoria) {
        // Obtenemos todos los alumnos únicos que tienen matrícula en esta convocatoria
        return convocatoriaService.getAlumnosByConvocatoriaId(convocatoria.id());
    }

}


