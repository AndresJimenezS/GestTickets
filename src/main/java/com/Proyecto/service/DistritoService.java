package com.Proyecto.service;


import com.Proyecto.domain.Distrito;
import java.util.List;

public interface DistritoService {
    
//    Método que retorna la lista de distritos
    public List<Distrito> getDistritos();
    
    // Se obtiene un Distrito, a partir del id de un distrito
    public Distrito getDistrito(Distrito distrito);
    
    // Se inserta un nuevo distrito si el id del distrito esta vacío
    // Se actualiza un distrito si el id del distrito NO esta vacío
    public void save(Distrito distrito);
    
    // Se elimina el distrito que tiene el id pasado por parámetro
    public void delete(Distrito distrito);
}
