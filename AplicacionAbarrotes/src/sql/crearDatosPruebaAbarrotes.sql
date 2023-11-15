-- Establece datos de prueba para la base de datos
-- @Author: Saul Neri

INSERT INTO Productos (clave_producto, nombre, tipo, unidad) 
VALUES ('AMC0101', 'Azucar', 'G', 'Kg');

INSERT INTO Productos (clave_producto, nombre, tipo, unidad) 
VALUES ('AMC0102', 'Sal', 'G', 'Kg');

INSERT INTO Productos (clave_producto, nombre, tipo, unidad) 
VALUES ('AMC0103', 'Azucar', 'G', 'Kg');

-- insercion de usuarios
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Ana Gonzalez', '79121003', 'ana.gonzalez@gmail.com', 'a1b2c3d4', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Carlos Rodriguez', '612345678', 'carlos.rodriguez@gmail.com', 'e5f6g7h8', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Maria Lopez', '712345678', 'maria.lopez@gmail.com', 'i9j0k1l2', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Javier Martinez', '823456789', 'javier.martinez@gmail.com', 'm3n4o5p6', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Laura Perez', '934567890', 'laura.perez@gmail.com', 'q7r8s9t0', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Daniel Sanchez', '545678901', 'daniel.sanchez@gmail.com', 'u1v2w3x4', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Marta Gonzalez', '656789012', 'marta.gonzalez@gmail.com', 'y5z6a1b2', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Pedro Rodriguez', '767890123', 'pedro.rodriguez@gmail.com', 'c3d4e5f6', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Carmen Lopez', '878901234', 'carmen.lopez@gmail.com', 'g7h8i9j0', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Antonio Martinez', '989012345', 'antonio.martinez@gmail.com', 'k1l2m3n4', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Sara Perez', '109123456', 'sara.perez@gmail.com', 'o5p6q7r8', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Jorge Sanchez', '120234567', 'jorge.sanchez@gmail.com', 's9t0u1v2', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Elena Gonzalez', '231345678', 'elena.gonzalez@gmail.com', 'w3x4y5z6', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Luis Rodriguez', '342456789', 'luis.rodriguez@gmail.com', 'a1b2c3d4', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Ana Maria Lopez', '453567890', 'ana.lopez@gmail.com', 'e5f6g7h8', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Carlos Javier Martinez', '564678901', 'carlos.martinez@gmail.com', 'i9j0k1l2', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Maria Laura Perez', '675789012', 'maria.perez@gmail.com', 'm3n4o5p6', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Javier Daniel Sanchez', '786890123', 'javier.sanchez@gmail.com', 'q7r8s9t0', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Laura Marta Gonzalez', '897901234', 'laura.gonzalez@gmail.com', 'u1v2w3x4', 'repartidor');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Daniel Pedro Rodriguez', '908012345', 'daniel.rodriguez@gmail.com', 'y5z6a1b2', 'administrador');
INSERT INTO Usuarios (nombre, telefono, email, contrasena, rol) VALUES ('Marta Carmen Lopez', '111123456', 'marta.lopez@gmail.com', 'c3d4e5f6', 'repartidor');

UPDATE Usuarios SET contrasena = 'd785d63511a645a24875a109e0ef1da6560dd94d149b6734949a96556cb3449f';


