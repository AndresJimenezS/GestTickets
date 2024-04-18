
package com.Proyecto.service.impl;

import com.Proyecto.dao.PrioridadDao;
import com.Proyecto.domain.Prioridad;
import com.Proyecto.service.PrioridadService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrioridadServiceImpl implements PrioridadService{
    
    @Autowired
    private PrioridadDao prioridadDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Prioridad> getPrioridades() {
        List<Prioridad> lista = prioridadDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Prioridad getPrioridad(Prioridad prioridad) {
        return prioridadDao.findById(prioridad.getPrioridad()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Prioridad prioridad) {
        prioridadDao.save(prioridad);
    }

    @Override
    @Transactional
    public void delete(Prioridad prioridad) {
        prioridadDao.delete(prioridad);
    }
}
