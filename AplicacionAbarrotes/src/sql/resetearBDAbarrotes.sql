-- @author: Saul Neri

-- TODO: La llave foranea que esta pendiente por definirla en el archivo 
--       "crearBDAbarrotes.sql" permitiria borrar todas las tablas derivadas
--       de la tabla Productos y Movimientos
DROP TABLE Productos IF EXISTS;
DROP TABLE ProductosEmpacados IF EXISTS;
DROP TABLE ProductosGranel IF EXISTS;

DROP TABLE Movimientos IF EXISTS;
DROP TABLE MovimientosEmpacado IF EXISTS;
DROP TABLE MovimientosGranel IF EXISTS;

-- DROP TABLE Usuarios IF EXISTS;
-- DROP TABLE Administrador IF EXISTS;