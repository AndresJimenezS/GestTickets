
package com.Proyecto.service.impl;

import com.Proyecto.dao.CantonDao;
import com.Proyecto.domain.Canton;
import com.Proyecto.service.CantonService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CantonServiceImpl implements CantonService{
    
    @Autowired
    private CantonDao cantonDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Canton> getCantones() {
        List<Canton> lista = cantonDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Canton getCanton(Canton canton) {
        return cantonDao.findById(canton.getCanton()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Canton canton) {
        cantonDao.save(canton);
    }

    @Override
    @Transactional
    public void delete(Canton canton) {
        cantonDao.delete(canton);
    }
}
