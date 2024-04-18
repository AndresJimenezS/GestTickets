/*
-- SQL Plus, ingresar como sys dba
alter session set "_oracle_script"=true;
create user sistema_incidentes identified by 123;
grant all privileges to sistema_incidentes;

-- Crear carpeta en 'C:\Reportes iTicket' y después seguir ejecutando en SQLPLUS
CREATE DIRECTORY downloads_dir AS 'C:\Reportes iTicket';
GRANT READ, WRITE ON DIRECTORY downloads_dir TO sistema_incidentes;
GRANT EXECUTE ON UTL_FILE TO sistema_incidentes;

// se crea la conexiÃ³n llamada sistema_incidentes asociada al usuario project_manager
*/

// creación de secuencias
create sequence usuarios_seq
  START WITH 33
  INCREMENT BY 1
  NOCYCLE; 
//drop sequence usuarios_seq;

create sequence tickets_seq
  START WITH 38
  INCREMENT BY 1
  NOCYCLE;
//drop sequence tickets_seq;
// tablas
create table provincia (
  provincia INT NOT NULL PRIMARY KEY,
  nombre_provincia VARCHAR2(20) NOT NULL);
  
create table canton (
  provincia INT NOT NULL,
  canton INT NOT NULL PRIMARY KEY,
  nombre_canton VARCHAR(20) NOT NULL,
  CONSTRAINT fk_provincia_canton FOREIGN KEY (provincia) REFERENCES provincia(provincia));

create table distrito (
  provincia INT NOT NULL,
  canton INT NOT NULL,
  distrito INT NOT NULL PRIMARY KEY,
  nombre_distrito VARCHAR(28) NOT NULL,
  CONSTRAINT fk_provincia_canton2 FOREIGN KEY (provincia) REFERENCES provincia(provincia),
  CONSTRAINT fk_canton_distrito FOREIGN KEY (canton) REFERENCES canton(canton));
  
create table estado_usuario (
  estado_usuario INT NOT NULL PRIMARY KEY,
  descripcion VARCHAR(20) NOT NULL);
  
create table prioridad (
  prioridad INT NOT NULL PRIMARY KEY,
  descripcion VARCHAR(20) NOT NULL);
  
create table estado_ticket (
  estado_ticket INT NOT NULL PRIMARY KEY,
  descripcion VARCHAR(20) NOT NULL);

create table rol (
  rol INT NOT NULL PRIMARY KEY,
  descripcion VARCHAR(20) NOT NULL);
  
create table incidencia (
  tipo_incidencia INT NOT NULL PRIMARY KEY,
  descripcion VARCHAR(20) NOT NULL);
  
create table usuario (
  id_usuario INT DEFAULT usuarios_seq.NEXTVAL PRIMARY KEY,
  usuario VARCHAR(20) NOT NULL,
  contrasena VARCHAR(512) NOT NULL,
  nombre_completo VARCHAR(80) NOT NULL,
  cedula INT NOT NULL,
  email VARCHAR(30) NOT NULL,
  telefono INT NOT NULL,
  sexo VARCHAR(10) NOT NULL,
  pais VARCHAR(20) NOT NULL,
  provincia INT NOT NULL,
  canton INT NOT NULL,
  distrito INT NOT NULL,
  otras_senas VARCHAR(100),
  rol INT NOT NULL,
  fecha_registro date not null,
  estado_usuario int not null,
  terminos_condiciones VARCHAR(1), /* ver si se puede hacer booleano*/
  CONSTRAINT fk_usuario_provincia FOREIGN KEY (provincia) REFERENCES provincia(provincia),
  CONSTRAINT fk_usuario_canton FOREIGN KEY (canton) REFERENCES canton(canton),
  CONSTRAINT fk_usuario_distrito FOREIGN KEY (distrito) REFERENCES distrito(distrito),
  CONSTRAINT fk_usuario_rol FOREIGN KEY (rol) REFERENCES rol(rol),
  CONSTRAINT fk_usuario_estado_usuario FOREIGN KEY (estado_usuario) REFERENCES estado_usuario(estado_usuario));
  
 
create table ticket (
  id_ticket INT DEFAULT tickets_seq.NEXTVAL PRIMARY KEY,
  id_usuario INT NOT NULL,
  estado INT NOT NULL,
  tipo_incidencia INT NOT NULL,
  prioridad INT NOT NULL,
  titulo VARCHAR(20) NOT NULL,
  descripcion VARCHAR(200) NOT NULL,
  comentarios_usuario VARCHAR(200) NOT NULL,
  fecha_registro_usuario date NOT NULL,
  fecha_registra_tecnico date NOT NULL,
  id_tecnico INT NOT NULL,
  comentario_tecnico VARCHAR(200) NOT NULL,
  CONSTRAINT fk_ticket_usuario FOREIGN KEY (id_usuario) REFERENCES usuario(id_usuario),
  CONSTRAINT fk_ticket_estado FOREIGN KEY (estado) REFERENCES estado_ticket(estado_ticket),
  CONSTRAINT fk_ticket_incidencia FOREIGN KEY (tipo_incidencia) REFERENCES incidencia(tipo_incidencia),
  CONSTRAINT fk_ticket_prioridad FOREIGN KEY (prioridad) REFERENCES prioridad(prioridad),
  CONSTRAINT fk_ticket_tecnico FOREIGN KEY (id_tecnico) REFERENCES usuario(id_usuario));
  
  
  -- Trigger al insertar ticket
BEGIN
  EXECUTE IMMEDIATE 'DROP TRIGGER inserta_ticket';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4080 THEN
      RAISE;
    END IF;
END;
CREATE TRIGGER inserta_ticket 
BEFORE INSERT ON ticket
FOR EACH ROW
BEGIN
	:NEW.fecha_registro_usuario := SYSDATE;
    :NEW.fecha_registra_tecnico := SYSDATE;
    :NEW.estado := 1;
    :NEW.prioridad := 1;
    :NEW.id_tecnico := 2;
END;

-- Trigger al insertar usuario
BEGIN
  EXECUTE IMMEDIATE 'DROP TRIGGER inserta_usuario';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4080 THEN
      RAISE;
    END IF;
END;
CREATE TRIGGER inserta_usuario 
BEFORE INSERT ON usuario
FOR EACH ROW
BEGIN
	:NEW.rol := 1;
    :NEW.estado_usuario := 2;
    :NEW.fecha_registro := SYSDATE;
END;

-- Trigger al actualizar tecnico
BEGIN
  EXECUTE IMMEDIATE 'DROP TRIGGER actualiza_tecnico';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4080 THEN
      RAISE;
    END IF;
END;
CREATE TRIGGER actualiza_tecnico 
BEFORE UPDATE ON ticket
FOR EACH ROW
BEGIN
	IF (:NEW.id_tecnico != :OLD.id_tecnico) then  
    :NEW.fecha_registra_tecnico := SYSDATE;
    :NEW.estado := 2;
    END IF;
END;

-- Trigger al activar usuario
BEGIN
  EXECUTE IMMEDIATE 'DROP TRIGGER activa_usuario';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4080 THEN
      RAISE;
    END IF;
END;
CREATE TRIGGER activa_usuario 
BEFORE UPDATE ON usuario
FOR EACH ROW
BEGIN
	IF (:OLD.estado_usuario = 2) then :NEW.estado_usuario := 1;
    END IF;
END;
  
-- SP para insertar ticket
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE insertarTicket';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
  
CREATE OR REPLACE PROCEDURE insertarTicket(
    in_id_usuario INT,
    in_tipo_incidencia INT,
    in_titulo varchar2,
    in_descripcion varchar2,
    in_comentarios_usuario varchar2,
    in_comentario_tecnico varchar2)
AS 
BEGIN
    INSERT INTO TICKET (id_usuario, tipo_incidencia, titulo, descripcion, comentarios_usuario, comentario_tecnico) VALUES
    (in_id_usuario, in_tipo_incidencia, in_titulo, in_descripcion, in_comentarios_usuario, in_comentario_tecnico);
END;
  
--exec sp_insertar_ticket(3, 2, 'prueba sp', 'descripcion', 'nada', '[pendiente: comentario de tÃ©cnico]'); --prueba

-- -- SP para editar ticket
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE editarTicket';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
CREATE OR REPLACE PROCEDURE editarTicket(
    in_id_ticket INT,
    in_id_usuario INT,
    in_estado INT,
    in_tipo_incidencia INT,
    in_prioridad INT,
    in_titulo varchar2,
    in_descripcion varchar2,
    in_comentarios_usuario varchar2,
    in_fecha_registro_usuario date,
    in_fecha_registra_tecnico date,
    in_id_tecnico INT,
    in_comentario_tecnico varchar2)
AS 
BEGIN
    UPDATE TICKET SET id_usuario = in_id_usuario, estado = in_estado, tipo_incidencia = in_tipo_incidencia, 
    prioridad = in_prioridad, titulo = in_titulo, descripcion = in_descripcion, comentarios_usuario = in_comentarios_usuario,
    fecha_registro_usuario = in_fecha_registro_usuario, fecha_registra_tecnico = in_fecha_registra_tecnico,
    id_tecnico = in_id_tecnico, comentario_tecnico = in_comentario_tecnico WHERE id_ticket = in_id_ticket;
END;

--exec editarTicket(22, 3, 1, 2, 1, 'prueba sp upd', 'descripcion upd', 'nada upd', '2024-03-10', '2024-03-10', 2, '[pendiente: comentario de tÃ©cnico] upd'); --prueba

-- SP para eliminar ticket
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE eliminarTicket';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
CREATE OR REPLACE PROCEDURE eliminarTicket(in_id_ticket INT)
AS 
BEGIN
    Delete TICKET WHERE id_ticket = in_id_ticket;
    Commit;
END;
--exec eliminarTicket(2); --prueba


-- SP USUARIOS
// SP para insertar usuario
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE insertarUsuario';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
  
  
CREATE OR REPLACE PROCEDURE insertarUsuario(
    in_usuario VARCHAR2,
    in_contrasena VARCHAR2,
    in_nombre_completo VARCHAR2,
    in_cedula INT,
    in_email VARCHAR2,
    in_telefono INT,
    in_sexo VARCHAR2,
    in_pais VARCHAR2,
    in_provincia INT,
    in_canton INT,
    in_distrito INT,
    in_otras_senas VARCHAR2,
    in_rol INT,
    in_estado_usuario INT,
    in_terminos_condiciones INT )
AS 
BEGIN
    INSERT INTO USUARIO (usuario, contrasena, nombre_completo, cedula, email, telefono, sexo, pais, provincia,
                            canton, distrito, otras_senas, rol, fecha_registro, estado_usuario, terminos_condiciones ) 
    VALUES(in_usuario, in_contrasena, in_nombre_completo, in_cedula, in_email, in_telefono, in_sexo, in_pais,
            in_provincia, in_canton, in_distrito, in_otras_senas, in_rol, SYSDATE, in_estado_usuario, in_terminos_condiciones);
END;



// SP para editar usuario
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE editarUsuario';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
  
CREATE OR REPLACE PROCEDURE editarUsuario(
    in_id_usuario INT,
    in_email VARCHAR2,
    in_telefono INT,
    in_sexo VARCHAR2,
    in_pais VARCHAR2,
    in_provincia INT,
    in_canton INT,
    in_distrito INT,
    in_otras_senas VARCHAR2)
AS 
BEGIN
    UPDATE USUARIO SET 
    email = in_email, telefono = in_telefono, sexo = in_sexo, pais = in_pais, provincia = in_provincia, canton = in_canton, distrito = in_distrito, otras_senas = in_otras_senas 
    WHERE id_usuario = in_id_usuario;            
END;


// SP para eliminar usuario
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE eliminarUsuario';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
  
CREATE OR REPLACE PROCEDURE eliminarUsuario(
    in_id INT )
AS 
BEGIN
    DELETE USUARIO WHERE id_usuario = in_id;            
END;


// SP para editar ROLES
BEGIN
  EXECUTE IMMEDIATE 'DROP PROCEDURE editarRoles';
EXCEPTION
  WHEN OTHERS THEN
    IF SQLCODE != -4043 THEN
      RAISE;
    END IF;
END;
  
  
CREATE OR REPLACE PROCEDURE editarRoles(
    in_usuario VARCHAR2,
    in_rol INT,
    in_estado_usuario INT
   )
AS 
BEGIN
    UPDATE USUARIO SET 
    rol = in_rol, estado_usuario = in_estado_usuario 
    WHERE usuario = in_usuario;            
END;


--Indices

-- Ã?ndice en la tabla canton para la columna provincia
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de cantÃ³n por provincia.

CREATE INDEX idx_canton_provincia ON canton(provincia);

-- Ã?ndice en la tabla distrito para la columna provincia
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de distrito por provincia.

CREATE INDEX idx_distrito_provincia ON distrito(provincia);

-- Ã?ndice en la tabla distrito para la columna canton
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de distrito por cantÃ³n.

CREATE INDEX idx_distrito_canton ON distrito(canton);

-- Ã?ndice en la tabla usuario para la columna provincia
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de usuario por provincia.

CREATE INDEX idx_usuario_provincia ON usuario(provincia);

-- Ã?ndice en la tabla usuario para la columna canton
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de usuario por cantÃ³n.

CREATE INDEX idx_usuario_canton ON usuario(canton);

-- Ã?ndice en la tabla usuario para la columna distrito
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de usuario por distrito.

CREATE INDEX idx_usuario_distrito ON usuario(distrito);

-- Ã?ndice en la tabla usuario para la columna rol
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de usuario por rol.

CREATE INDEX idx_usuario_rol ON usuario(rol);

-- Ã?ndice en la tabla usuario para la columna estado_usuario
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de usuario por estado de usuario.

CREATE INDEX idx_usuario_estado_usuario ON usuario(estado_usuario);

-- Ã?ndice en la tabla ticket para la columna id_usuario
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de ticket por id de usuario.

CREATE INDEX idx_ticket_id_usuario ON ticket(id_usuario);

-- Ã?ndice en la tabla ticket para la columna estado
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de ticket por estado.

CREATE INDEX idx_ticket_estado ON ticket(estado);

-- Ã?ndice en la tabla ticket para la columna tipo_incidencia
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de ticket por tipo de incidencia.

CREATE INDEX idx_ticket_tipo_incidencia ON ticket(tipo_incidencia);

-- Ã?ndice en la tabla ticket para la columna prioridad
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de ticket por prioridad.

CREATE INDEX idx_ticket_prioridad ON ticket(prioridad);

-- Ã?ndice en la tabla ticket para la columna id_tecnico
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de ticket por id de tÃ©cnico asignado.

CREATE INDEX idx_ticket_id_tecnico ON ticket(id_tecnico);

-- Ã?ndice en la tabla ticket para la columna fecha_registro_usuario
-- Este Ã­ndice mejora el rendimiento de consultas que filtran o clasifican registros de ticket por fecha de registro del usuario.

CREATE INDEX idx_ticket_fecha_registro_usuario ON ticket(fecha_registro_usuario);


-- SP con cursor de sistema para exportar lista de técnicos en csv

create or replace procedure listarTecnicos(
    outCursor out sys_refcursor
) as
begin
    open outCursor for
    select id_usuario, usuario, nombre_completo, Fecha_Registro, Estado_Usuario from usuario where rol = 2;
end;
/*
declare fuera sys_refcursor;
begin 
    listarTecnicos(fuera);
    dbms_sql.return_result(fuera);
end;
*/
// SP para crear reporte con lista de técnicos
CREATE OR REPLACE PROCEDURE exportarTecnicosCSV(p_file_name IN VARCHAR2) AS
    v_cursor SYS_REFCURSOR;
    v_id_usuario usuario.id_usuario%TYPE;
    v_usuario usuario.usuario%TYPE;
    v_nombre_completo usuario.nombre_completo%TYPE;
    v_fecha_registro usuario.Fecha_Registro%TYPE;
    v_estado_usuario usuario.Estado_Usuario%TYPE;
    v_output UTL_FILE.FILE_TYPE;
    v_row VARCHAR2(4000);
BEGIN
    -- Ejecuta SP listarTecnicos usando cursor como parámetro de salida
    listarTecnicos(outCursor => v_cursor);

    -- Se abre archivo o se crea en caso de no existir
    v_output := UTL_FILE.FOPEN(upper('downloads_dir'), p_file_name, 'w');

    -- Nombres de variables
    UTL_FILE.PUT_LINE(v_output, 'ID_USUARIO|USUARIO|NOMBRE_COMPLETO|FECHA_REGISTRO|ESTADO_USUARIO');

    -- Recorrido del cursor para obtener los datos de interés
    LOOP
        FETCH v_cursor INTO v_id_usuario, v_usuario, v_nombre_completo, v_fecha_registro, v_estado_usuario;
        EXIT WHEN v_cursor%NOTFOUND;

        -- Concatenación de las filas que van a contener datos
        v_row := v_id_usuario || '|' || v_usuario || '|' || v_nombre_completo || '|' || v_fecha_registro || '|' || v_estado_usuario;

        -- Escritura de las filas en el archivo
        UTL_FILE.PUT_LINE(v_output, v_row);
    END LOOP;
	
    CLOSE v_cursor;

    -- Cierre del archivo
    UTL_FILE.FCLOSE(v_output);
EXCEPTION
    WHEN OTHERS THEN
        IF UTL_FILE.IS_OPEN(v_output) THEN
            UTL_FILE.FCLOSE(v_output);
        END IF;
        RAISE;
END;


// FUNCiÓN USUARIOS CURSOR
CREATE OR REPLACE FUNCTION getUsuarios
    RETURN SYS_REFCURSOR
IS
    c_usuarios SYS_REFCURSOR;
BEGIN
    OPEN c_usuarios FOR
        SELECT u.id_usuario, u.nombre_completo, u.cedula
        FROM usuario u;
    RETURN c_usuarios;
END;
        