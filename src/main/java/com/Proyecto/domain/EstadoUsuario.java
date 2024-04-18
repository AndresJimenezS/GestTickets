
package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;


@Data
@Entity
@Table(name="estado_usuario")
public class EstadoUsuario implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
//    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "estado_usuario")
    private Long estadoUsuario; 
    private String descripcion;
    
   

    public EstadoUsuario() {
    }

    public EstadoUsuario(Long estadoUsuario, String descripcion) {
        this.estadoUsuario = estadoUsuario;
        this.descripcion = descripcion;
    }

    
}