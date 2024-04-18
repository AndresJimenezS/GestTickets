
package com.Proyecto.dao;


import com.Proyecto.domain.EstadoTicket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoTicketDao extends JpaRepository<EstadoTicket, Long>{
    
}
