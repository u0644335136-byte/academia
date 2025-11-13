package com.jdk21.academia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name = "centro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Centro extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // IMPORTANTE
    @Column(name = "id_centro")
    private Long idCentro;

    // Campos mapeados directamente a columnas existentes
    @Column(name = "codigo_centro", nullable = false, unique = true)
    private String codigo_centro;

    @Column(name = "responsable", nullable = false)
    private String responsable;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "id_empresa", nullable = false)
    private String id_empresa;

    @Column(name = "id_comunidad", nullable = false)
    private String id_comunidad;

    @Column(name = "telefono")
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
    private Integer codigo_postal;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "provincia")
    private String provincia;

    // Campo gestionado por trigger o default en BD (solo lectura)
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaCreacion;
    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaActualizacion;

    @Column(name = "activo")
    private Boolean activo;

}