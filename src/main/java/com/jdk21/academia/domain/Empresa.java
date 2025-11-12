

package com.jdk21.academia.domain;

import java.io.Serializable;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "empresa")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@AttributeOverride(name = "id", column = @Column(name = "id_empresa"))
public class Empresa extends BaseEntity implements Serializable {
    
    @Column(name = "cif", nullable = false, unique = true)
    private String cif;

    @Column(name = "nombre_legal", nullable = false)
    private String nombreLegal;

    @Column(name = "razon_social")
    private String razonSocial;

    @Column(name = "representante")
    private String representante;

}

