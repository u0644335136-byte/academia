package com.jdk21.academia.features.comunidad.controller;

import com.jdk21.academia.domain.Comunidad;
import com.jdk21.academia.features.comunidad.repository.ComunidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ComunidadGraphQLController {

    private final ComunidadRepository repository;

    // ✅ CONSULTA: obtener todas las comunidades
    @QueryMapping
    public List<Comunidad> comunidades() {
        return repository.findAll();
    }

    // ✅ CONSULTA: obtener comunidad por ID
@QueryMapping
public Comunidad comunidadById(@Argument Long idComunidad) {
    if (idComunidad == null) {
        throw new IllegalArgumentException("El parámetro idComunidad no puede ser nulo.");
    }

    return repository.findById(idComunidad)
            .orElseThrow(() -> new RuntimeException("No se encontró la comunidad con id " + idComunidad));
}

    // ✅ MUTACIÓN: crear una nueva comunidad
    @MutationMapping
    public Comunidad crearComunidad(@Argument String codigo,
                                    @Argument String nombre,
                                    @Argument String capital) {
        Comunidad c = new Comunidad();
        c.setCodigo(codigo);
        c.setNombre(nombre);
        c.setCapital(capital);
        c.setActivo(true);
        return repository.save(c);
    }

    // ✅ MUTACIÓN: desactivar una comunidad (borrado lógico)
    @MutationMapping
    public boolean desactivarComunidad(@Argument Long idComunidad) {
        Optional<Comunidad> comunidadOpt = repository.findById(idComunidad);
        if (comunidadOpt.isPresent()) {
            Comunidad c = comunidadOpt.get();
            c.setActivo(false);
            repository.save(c);
            return true;
        }
        return false;
    }
}
