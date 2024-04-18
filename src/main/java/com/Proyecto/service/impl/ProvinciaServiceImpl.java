
package com.Proyecto.service.impl;

import com.Proyecto.dao.ProvinciaDao;
import com.Proyecto.domain.Provincia;
import com.Proyecto.service.ProvinciaService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProvinciaServiceImpl implements ProvinciaService{
    
    @Autowired
    private ProvinciaDao provinciaDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Provincia> getProvincias() {
        List<Provincia> lista = provinciaDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Provincia getProvincia(Provincia provincia) {
        return provinciaDao.findById(provincia.getProvincia()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Provincia provincia) {
        provinciaDao.save(provincia);
    }

    @Override
    @Transactional
    public void delete(Provincia provincia) {
        provinciaDao.delete(provincia);
    }
}
