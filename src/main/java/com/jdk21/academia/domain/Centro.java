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
@AttributeOverride(name = "id", column = @Column(name = "id_centro"))
public class Centro extends BaseEntity implements Serializable {

    // Campos mapeados directamente a columnas existentes
    @Column(name = "codigo_centro", nullable = false, unique = true)
    private String codigo_centro;

    @Column(name = "responsable", nullable = false)
    private String responsable;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    //@Column(name = "id_empresa", nullable = false)
    @Transient
    private Long id_empresa;

    // Campo no existe en la BD - marcado como @Transient para que Hibernate lo ignore
    @Transient
    private Long id_comunidad;

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

}