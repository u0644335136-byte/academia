package com.jdk21.academia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Entity
@Table(name="centro")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@AttributeOverride(name = "id", column = @Column(name = "id_centro"))
public class Centro extends BaseEntity implements Serializable {

    // Campos mapeados directamente a columnas existentes
    @Column(name = "codigo_centro", nullable = false, unique = true)
    private String codigo_centro;

    @Column(name = "responsable", nullable = false)
    private String responsable;

    @Column(name = "nombre", nullable = false)
    private String nombre;


    @Column(name = "telefono")
    private int telefono;

    @Column(name = "email")
    private String email;

    @Column(name = "web")
    private String web;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "numero")
    private int numero;

    @Column(name = "piso")
    private int piso;

    @Column(name = "puerta")
    private String puerta;

    @Column(name = "codigo_postal")
    private int codigo_postal;

    @Column(name = "localidad")
    private String localidad;

    @Column(name = "provincia")
    private String provincia;

    // Si hay FK (por ejemplo, id_comunidad_autonoma)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_empresa")
    private Empresa empresa;

}
