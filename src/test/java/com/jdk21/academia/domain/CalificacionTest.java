package com.jdk21.academia.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


public class CalificacionTest {

    @Test
    @DisplayName("Test Constructor sin argumentos")
    void testNoArgsConstructor() {
        Calificacion calificacion = new Calificacion();
        assertThat(calificacion).isNotNull();
        assertThat(calificacion.getIdCalificacion()).isNull();
        assertThat(calificacion.getFecha()).isNull();
        assertThat(calificacion.getNota()).isZero();
        assertThat(calificacion.getFechaCreacion()).isNull();
        assertThat(calificacion.getFechaActualizacion()).isNull();
        assertThat(calificacion.isActivo()).isFalse();
        assertThat(calificacion.getIdMatricula()).isNull();
    }

    @Test
    @DisplayName("Test constructor con todos los argumentos")
    void testAllArgsConstructor() {
        LocalDate fecha = LocalDate.of(2025, 1, 1);
        LocalDate fechaCreacion = LocalDate.of(2025, 1, 2);
        LocalDate fechaActualizacion = LocalDate.of(2025, 1, 3);

        Calificacion calificacion = new Calificacion(
                1L,
                fecha,
                95,
                fechaCreacion,
                fechaActualizacion,
                true,
                10L
        );

        assertThat(calificacion.getIdCalificacion()).isEqualTo(1L);
        assertThat(calificacion.getFecha()).isEqualTo(fecha);
        assertThat(calificacion.getNota()).isEqualTo(95);
        assertThat(calificacion.getFechaCreacion()).isEqualTo(fechaCreacion);
        assertThat(calificacion.getFechaActualizacion()).isEqualTo(fechaActualizacion);
        assertThat(calificacion.isActivo()).isTrue();
        assertThat(calificacion.getIdMatricula()).isEqualTo(10L);
    }

    @Test
    @DisplayName("Test getters y setters")
    void testGettersAndSetters() {
        Calificacion calificacion = new Calificacion();

        LocalDate fecha = LocalDate.of(2025, 2, 1);
        LocalDate fechaCreacion = LocalDate.of(2025, 2, 2);
        LocalDate fechaActualizacion = LocalDate.of(2025, 2, 3);

        calificacion.setIdCalificacion(2L);
        calificacion.setFecha(fecha);
        calificacion.setNota(80);
        calificacion.setFechaCreacion(fechaCreacion);
        calificacion.setFechaActualizacion(fechaActualizacion);
        calificacion.setActivo(true);
        calificacion.setIdMatricula(20L);

        assertThat(calificacion.getIdCalificacion()).isEqualTo(2L);
        assertThat(calificacion.getFecha()).isEqualTo(fecha);
        assertThat(calificacion.getNota()).isEqualTo(80);
        assertThat(calificacion.getFechaCreacion()).isEqualTo(fechaCreacion);
        assertThat(calificacion.getFechaActualizacion()).isEqualTo(fechaActualizacion);
        assertThat(calificacion.isActivo()).isTrue();
        assertThat(calificacion.getIdMatricula()).isEqualTo(20L);
    }

    @Test
    @DisplayName("Test equals y de hashCode en base idCalificacion")
    void testEqualsAndHashCode() {
        Calificacion c1 = new Calificacion();
        c1.setIdCalificacion(1L);

        Calificacion c2 = new Calificacion();
        c2.setIdCalificacion(1L);

        Calificacion c3 = new Calificacion();
        c3.setIdCalificacion(2L);

        assertThat(c1).isEqualTo(c2);
        assertThat(c1).hasSameHashCodeAs(c2);
        assertThat(c1).isNotEqualTo(c3);
    }

    @Test
    @DisplayName("Test toString incluye la idCalificacion")
    void testToString() {
        Calificacion c = new Calificacion();
        c.setIdCalificacion(5L);
        String toString = c.toString();
        assertThat(toString).contains("5");
        assertThat(toString).contains("Calificacion");
    }
}
