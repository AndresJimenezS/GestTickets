package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="distrito")
public class Distrito implements Serializable{
    
     private static final long serialVersionUID = 1L;
    
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Id
    private Long distrito; 
    private String nombreDistrito;
    
   @ManyToOne
    @JoinColumn(name = "provincia")
    private Provincia provincia;
   
    @ManyToOne
    @JoinColumn(name = "canton")
    private Canton canton;
   

    public Distrito() {
    }

    public Distrito(String nombreDistrito) {
        this.nombreDistrito = nombreDistrito;
    }

       
}
