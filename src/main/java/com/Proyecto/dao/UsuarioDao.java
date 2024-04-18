package com.Proyecto.dao;

import com.Proyecto.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

public interface UsuarioDao extends JpaRepository<Usuario, Long>{
    Usuario findByUsername(String username);
    
    Usuario findByUsernameAndContrasena(String username, String Contrasena);

    Usuario findByUsernameOrEmail(String username, String email);

    boolean existsByUsernameOrEmail(String username, String email);
    
    // Oracle PL/SQL
    
    // SP INSERTAR USUARIO
    @Procedure(name = "insertarUsuario")
    public void insertarUsuario(
        @Param("in_usuario") String usuario,
        @Param("in_contrasena") String contrasena,
        @Param("in_nombre_completo") String nombreCompleto,
        @Param("in_cedula") Long cedula,
        @Param("in_email") String email,
        @Param("in_telefono") Long telefono,
        @Param("in_sexo") String sexo,
        @Param("in_pais") String pais,
        @Param("in_provincia") Long provincia,
        @Param("in_canton") Long canton,
        @Param("in_distrito") Long distrito,
        @Param("in_otras_senas") String otrasSenas,
        @Param("in_rol") Long rol,
        @Param("in_estado_usuario") Long estadoUsuario,
        @Param("in_terminos_condiciones") Long terminosCondiciones
    );
    
    
    // SP EDITAR USUARIO
    @Procedure(name = "editarUsuario")
    public void editarUsuario(
        @Param("in_id_usuario") Long idUsuario,
        @Param("in_email") String email,
        @Param("in_telefono") Long telefono,
        @Param("in_sexo") String sexo,
        @Param("in_pais") String pais,
        @Param("in_provincia") Long provincia,
        @Param("in_canton") Long canton,
        @Param("in_distrito") Long distrito,
        @Param("in_otras_senas") String otrasSenas
    );
    
    
    // SP ELIMINAR USUARIO
    @Procedure(name = "eliminarUsuario")
    public void eliminarUsuario(
        @Param("in_id") Long id
    );
    
    
    // SP EDITAR ROLES/ESTADO
     @Procedure(name = "editarRoles")
    public void editarRoles(
        @Param("in_usuario") String usuario,
        @Param("in_rol") Long rol,
        @Param("in_estado_usuario") Long estadoUsuario
    );
    
    // SP exportar lista de técnicos
    @Procedure(name = "exportarTecnicosCSV")
    public void exportarTecnicosCSV(
        @Param("p_file_name") String nombreArchivo
    );
}
