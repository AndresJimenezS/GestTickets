
package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="prioridad")
public class Prioridad implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long prioridad; 
    private String descripcion;
    
   

    public Prioridad() {
    }

    public Prioridad(Long prioridad, String descripcion) {
        this.prioridad = prioridad;
        this.descripcion = descripcion;
    }

    
}