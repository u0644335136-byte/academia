package com.jdk21.academia.persona.domain;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "persona")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Persona implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id_persona;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "surname")
    private String surname;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "telefono", unique = true, nullable = false)
    private Integer telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "web")
    private String web;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "piso")
    private Integer piso;

    @Column(name = "puerta")
    private String puerta;

    @Column(name = "codigo_postal")
    private Integer codigoPostal;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "provincia")
    private String provincia;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private java.time.LocalDate fechaCreacion;
}
