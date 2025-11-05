package com.jdk21.academia.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

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
    @Column(name = "id_persona")
    private Long idPersona;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PersonaRol> roles = new HashSet<>();

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "apellido")
    private String apellido;

    @Column(name = "activo")
    private Boolean activo;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Column(name = "telefono", unique = true)
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_discapacidad")
    private Discapacidad idDiscapacidad;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaActualizacion;
}
