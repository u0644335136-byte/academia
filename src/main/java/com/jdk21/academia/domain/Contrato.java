package com.jdk21.academia.domain;

import java.io.Serializable;
import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "contrato")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)

public class Contrato implements Serializable {
    // Clave primaria (campo ya existente en la BD)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "id_contrato")
    private Long idContrato;

    // Campos mapeados directamente a columnas existentes
    @Column(name = "salario", nullable = false)
    private int salario;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "activo", nullable = false)
    private Boolean activo;

    @Column(name = "tipo")
    private String tipo;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fecha_inicio;

    @Column(name = "fecha_fin", nullable = false)
    private Date fecha_fin;

    /*
     * // Si hay FK (por ejemplo, id_persona)
     *
     * @ManyToOne(fetch = FetchType.LAZY)
     *
     * @JoinColumn(name="id_persona")
     * private Persona persona;
     */

    // Campo gestionado por trigger o default en BD (solo lectura)
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaCreacion;
    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private java.time.LocalDateTime fechaActualizacion;

}
