
package com.Proyecto.service.impl;

import com.Proyecto.dao.DistritoDao;
import com.Proyecto.domain.Distrito;
import com.Proyecto.service.DistritoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DistritoServiceImpl implements DistritoService{
    
    @Autowired
    private DistritoDao distritoDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Distrito> getDistritos() {
        List<Distrito> lista = distritoDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Distrito getDistrito(Distrito distrito) {
        return distritoDao.findById(distrito.getDistrito()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Distrito distrito) {
        distritoDao.save(distrito);
    }

    @Override
    @Transactional
    public void delete(Distrito distrito) {
        distritoDao.delete(distrito);
    }
}
