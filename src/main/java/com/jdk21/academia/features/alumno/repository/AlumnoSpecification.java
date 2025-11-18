package com.jdk21.academia.features.alumno.repository;

import org.springframework.data.jpa.domain.Specification;

import com.jdk21.academia.domain.Alumno;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * Especificaciones para búsquedas dinámicas de alumnos.
 */
public class AlumnoSpecification {

    /**
     * Crea una especificación para buscar alumnos por término de búsqueda y estado activo.
     */
    public static Specification<Alumno> searchAndFilter(String search, Boolean activo) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Filtro por estado activo
            if (activo != null) {
                predicates.add(criteriaBuilder.equal(root.get("activo"), activo));
            }

            // Filtro por término de búsqueda
            if (search != null && !search.trim().isEmpty()) {
                String searchPattern = "%" + search.toLowerCase() + "%";
                Predicate nombrePredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("nombre")), searchPattern);
                Predicate apellidosPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("apellidos")), searchPattern);
                Predicate emailPredicate = criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("email")), searchPattern);
                Predicate telefonoPredicate = criteriaBuilder.like(
                    root.get("telefono").as(String.class), "%" + search + "%");

                predicates.add(criteriaBuilder.or(
                    nombrePredicate,
                    apellidosPredicate,
                    emailPredicate,
                    telefonoPredicate
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

