package com.Proyecto.service;


import com.Proyecto.domain.EstadoTicket;
import java.util.List;

public interface EstadoTicketService {
    
//    Método que retorna la lista de estadoTickets
    public List<EstadoTicket> getEstadoTickets();
    
    // Se obtiene un EstadoTicket, a partir del id de un estadoTicket
    public EstadoTicket getEstadoTicket(EstadoTicket estadoTicket);
    
    // Se inserta un nuevo estadoTicket si el id del estadoTicket esta vacío
    // Se actualiza un estadoTicket si el id del estadoTicket NO esta vacío
    public void save(EstadoTicket estadoTicket);
    
    // Se elimina el estadoTicket que tiene el id pasado por parámetro
    public void delete(EstadoTicket estadoTicket);
}
