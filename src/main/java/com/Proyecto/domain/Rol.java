
package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="rol")
public class Rol implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long rol; 
    private String descripcion;
    
   

    public Rol() {
    }

    public Rol(Long rol, String descripcion) {
        this.rol = rol;
        this.descripcion = descripcion;
    }

    
}