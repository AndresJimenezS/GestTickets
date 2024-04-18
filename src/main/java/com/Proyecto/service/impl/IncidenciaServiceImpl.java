
package com.Proyecto.service.impl;

import com.Proyecto.dao.IncidenciaDao;
import com.Proyecto.domain.Incidencia;
import com.Proyecto.service.IncidenciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class IncidenciaServiceImpl implements IncidenciaService{
    
    @Autowired
    private IncidenciaDao incidenciaDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Incidencia> getIncidencias() {
        List<Incidencia> lista = incidenciaDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Incidencia getIncidencia(Incidencia incidencia) {
        return incidenciaDao.findById(incidencia.getIncidencia()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Incidencia incidencia) {
        incidenciaDao.save(incidencia);
    }

    @Override
    @Transactional
    public void delete(Incidencia incidencia) {
        incidenciaDao.delete(incidencia);
    }
}
