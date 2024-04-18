
package com.Proyecto.dao;


import com.Proyecto.domain.Incidencia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidenciaDao extends JpaRepository<Incidencia, Long>{
    
}
