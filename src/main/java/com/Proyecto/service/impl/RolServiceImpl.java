
package com.Proyecto.service.impl;

import com.Proyecto.dao.RolDao;
import com.Proyecto.domain.Rol;
import com.Proyecto.service.RolService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RolServiceImpl implements RolService{
    
    @Autowired
    private RolDao rolDao;
    
    
    @Override
    @Transactional(readOnly = true)
    public List<Rol> getRoles() {
        List<Rol> lista = rolDao.findAll();
        return lista;
    }
    
    @Override
    @Transactional(readOnly = true)
    public Rol getRol(Rol rol) {
        return rolDao.findById(rol.getRol()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Rol rol) {
        rolDao.save(rol);
    }

    @Override
    @Transactional
    public void delete(Rol rol) {
        rolDao.delete(rol);
    }
}
