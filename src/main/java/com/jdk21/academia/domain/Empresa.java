

package com.jdk21.academia.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @ToString.Include
    @Column(name = "id_empresa")
    private Long idEmpresa;

    @Column(name = "cif", nullable = false, unique = true)
    private String cif;

    @Column(name = "nombre_legal", nullable = false)
    private String nombreLegal;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "representante")
    private String representante;

    //Auto-incluidos
    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_actualizacion", insertable = false, updatable = false)
    private LocalDateTime fechaActualizacion;

    @Column(name = "activo", insertable = false)
    private boolean activo;

//     @PrePersist
//     protected void onCreate() {
//         fechaCreacion = LocalDateTime.now();
//         fechaActualizacion = LocalDateTime.now();
//     }

//     @PreUpdate
//     protected void onUpdate() {
//         fechaActualizacion = LocalDateTime.now();
//     }
}