
package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="incidencia")
public class Incidencia implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "tipo_incidencia")
    private Long incidencia; 
    private String descripcion;
    
   

    public Incidencia() {
    }

    public Incidencia(Long incidencia, String descripcion) {
        this.incidencia = incidencia;
        this.descripcion = descripcion;
    }

    
}