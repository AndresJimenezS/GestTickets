
package com.Proyecto.service.impl;

import com.Proyecto.dao.TicketDao;
import com.Proyecto.domain.Ticket;
import com.Proyecto.service.TicketService;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TicketServiceImpl implements TicketService{
    
    @Autowired
    private TicketDao ticketDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Ticket> getTickets() {
        List<Ticket> lista = ticketDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Ticket getTicket(Ticket ticket) {
        return ticketDao.findById(ticket.getIdTicket()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Ticket ticket) {
        ticketDao.save(ticket);
    }

    @Override
    @Transactional
    public void delete(Ticket ticket) {
        ticketDao.delete(ticket);
    }

    @Override
    @Transactional(readOnly=true) 
    public List<Ticket> metodoFecha(String fecha) {
        return ticketDao.metodoFecha(fecha);
    }
    
    @Override
    @Transactional(readOnly=true) 
    public List<Ticket> metodoEstado(String estado) {
        return ticketDao.metodoEstado(estado);
    }
    
    @Override
    @Transactional(readOnly=true) 
    public List<Ticket> metodoIncidente(String incidente) {
        return ticketDao.metodoIncidente(incidente);
    }
    
    @Override
    @Transactional(readOnly=true) 
    public List<Ticket> metodoTecnico(String tecnico) {
        return ticketDao.metodoTecnico(tecnico);
    }
    
    @Override
    @Transactional
    public void insertarTicket(Long idUsuario, Long tipoIncidencia, String titulo, String descripcion, String comentariosUsuario, String comentarioTecnico) {
        ticketDao.insertarTicket(idUsuario, tipoIncidencia, titulo, descripcion, comentariosUsuario, comentarioTecnico);
    }
    
    @Override
    @Transactional
    public void editarTicket(Long idTicket, Long idUsuario, Long estado, Long tipoIncidencia, Long prioridad, String titulo, String descripcion, String comentariosUsuario,
                LocalDateTime fechaRegistroUsuario, LocalDateTime fechaRegistraTecnico, Long idTecnico, String comentarioTecnico) {
        ticketDao.editarTicket(idTicket, idUsuario, estado, tipoIncidencia, prioridad, titulo, descripcion, comentariosUsuario,
                fechaRegistroUsuario, fechaRegistraTecnico, idTecnico, comentarioTecnico);
    }
    
    @Override
    @Transactional
    public void eliminarTicket(Long idTicket) {
        ticketDao.eliminarTicket(idTicket);
    }
}
