package com.Proyecto.service;


import com.Proyecto.domain.Provincia;
import java.util.List;

public interface ProvinciaService {
    
//    Método que retorna la lista de provincias
    public List<Provincia> getProvincias();
    
    // Se obtiene un Provincia, a partir del id de un provincia
    public Provincia getProvincia(Provincia provincia);
    
    // Se inserta un nuevo provincia si el id del provincia esta vacío
    // Se actualiza un provincia si el id del provincia NO esta vacío
    public void save(Provincia provincia);
    
    // Se elimina el provincia que tiene el id pasado por parámetro
    public void delete(Provincia provincia);
}
