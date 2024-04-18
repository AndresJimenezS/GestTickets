
package com.Proyecto.service.impl;

import com.Proyecto.dao.EstadoTicketDao;
import com.Proyecto.domain.EstadoTicket;
import com.Proyecto.service.EstadoTicketService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EstadoTicketServiceImpl implements EstadoTicketService{
    
    @Autowired
    private EstadoTicketDao estadoTicketDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<EstadoTicket> getEstadoTickets() {
        List<EstadoTicket> lista = estadoTicketDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public EstadoTicket getEstadoTicket(EstadoTicket estadoTicket) {
        return estadoTicketDao.findById(estadoTicket.getEstadoTicket()).orElse(null);
    }

    @Override
    @Transactional
    public void save(EstadoTicket estadoTicket) {
        estadoTicketDao.save(estadoTicket);
    }

    @Override
    @Transactional
    public void delete(EstadoTicket estadoTicket) {
        estadoTicketDao.delete(estadoTicket);
    }
}
