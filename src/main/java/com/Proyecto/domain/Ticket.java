package com.Proyecto.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ticket")
    private Long idTicket;
    private String titulo;
    private String descripcion;
    private String comentariosUsuario;
    private String comentarioTecnico;
    
    @Column(name="fecha_registro_usuario", insertable = false)
    private LocalDateTime fechaRegistroUsuario;
    
    @Column(name="fecha_registra_tecnico", insertable = false)
    private LocalDateTime fechaRegistraTecnico;
    
    @ManyToOne
    @JoinColumn(name = "tipoIncidencia")
    private Incidencia incidencia;
    
    @ManyToOne
    @JoinColumn(name = "estado")
    private EstadoTicket estado;
    
    @ManyToOne
    @JoinColumn(name = "prioridad")
    private Prioridad prioridad;
    
    @ManyToOne
    @JoinColumn(name = "idUsuario")
    private Usuario usuario;
    
    @ManyToOne
    @JoinColumn(name = "idTecnico")
    private Usuario tecnico;

    public Ticket() {
    }

//    public Ticket(Long idUsuario, Long estado, Long prioridad, String titulo, String descripcion, String comentariosUsuario, String fechaRegistroUsuario, String fechaRegistraTecnico, Long idTecnico, String comentarioTecnico) {
//        this.idUsuario = idUsuario;
//        this.estado = estado;
////        this.tipoIncidencia = tipoIncidencia;
//        this.prioridad = prioridad;
//        this.titulo = titulo;
//        this.descripcion = descripcion;
//        this.comentariosUsuario = comentariosUsuario;
//        this.fechaRegistroUsuario = fechaRegistroUsuario;
//        this.fechaRegistraTecnico = fechaRegistraTecnico;
//        this.idTecnico = idTecnico;
//        this.comentarioTecnico = comentarioTecnico;
//    }

     
    
    

}
