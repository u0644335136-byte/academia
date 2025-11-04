

package com.jdk21.academia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Empresa implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    private Long id_empresa;

    @Column(name = "cif", nullable = false, unique = true)
    private String cif;

    @Column(name = "nombre_legal", nullable = false)
    private String nombreLegal;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "representante")
    private String representante;

    @Column(name = "activo")
    private Boolean activo = true;

    // Si hay FK (por ejemplo, id_comunidad_autonoma)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id_persona")
    private Empresa empresa;

    // Campo gestionado por trigger o default en BD (solo lectura)
@Column(name= "fecha_creacion", insertable = false,updatable = false)
private java.time.LocalDate createdDate;
@Column(name= "fecha_actualiza", insertable = false,updatable = false)

private java.time.LocalDate updatedDate;
}