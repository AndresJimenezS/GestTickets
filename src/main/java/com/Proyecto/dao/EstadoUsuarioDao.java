
package com.Proyecto.dao;


import com.Proyecto.domain.EstadoUsuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadoUsuarioDao extends JpaRepository<EstadoUsuario, Long>{
    
}
