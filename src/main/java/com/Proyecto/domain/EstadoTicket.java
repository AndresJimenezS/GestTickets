
package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="estado_ticket")
public class EstadoTicket implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "estado_ticket")
    private Long estadoTicket; 
    private String descripcion;
    
   

    public EstadoTicket() {
    }

    public EstadoTicket(Long estadoTicket, String descripcion) {
        this.estadoTicket = estadoTicket;
        this.descripcion = descripcion;
    }

    
}