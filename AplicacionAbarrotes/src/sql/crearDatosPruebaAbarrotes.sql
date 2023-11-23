-- Establece datos de prueba para la base de datos
-- @Author: Saul Neri

INSERT INTO Productos (clave_producto, nombre, tipo, unidad) 
VALUES ('AMC0101', 'Azucar', 'G', 'Kg');

INSERT INTO Productos (clave_producto, nombre, tipo, unidad) 
VALUES ('AMC0102', 'Sal', 'G', 'Kg');

INSERT INTO Productos (clave_producto, nombre, tipo, unidad) 
VALUES ('AMC0103', 'Azucar', 'G', 'Kg');

--[PRODUCTOS COMUNES]
INSERT INTO Productos (clave_producto, nombre, tipo, unidad) VALUES
('ABC1234', 'Arroz', 'G', 'Kg'),
('DEF5678', 'Detergente', 'E', 'ml'),
('GHI9012', 'Galletas', 'G', 'gr'),
('JKL3456', 'Jabón', 'E', 'lt'),
('MNO7890', 'Manzanas', 'G', 'Kg'),
('STU5678', 'Sopa', 'G', 'ml'),
('VWX9012', 'Vino', 'E', 'lt'),
('YZA3456', 'Yogur', 'G', 'ml'),
('BCD7890', 'Bananas', 'G', 'Kg'),
('HIJ5678', 'Harina', 'G', 'gr'),
('KLM9012', 'Ketchup', 'E', 'ml'),
('NOP3456', 'Naranjas', 'G', 'Kg'),
('QRS7890', 'Queso', 'E', 'gr'),
('TUV1234', 'Tomates', 'G', 'Kg'),
('WXY5678', 'Whisky', 'E', 'ml'),
('ZAB9012', 'Zanahorias', 'G', 'Kg'),
('CDE3456', 'Café', 'E', 'gr'),
('FGH7890', 'Fresas', 'G', 'Kg'),
('IJK1234', 'Insecticida', 'E', 'ml'),
('LMN5678', 'Leche', 'G', 'lt'),
('OPQ9012', 'Pan', 'E', 'gr'),
('UVW7890', 'Uvas', 'G', 'Kg'),
('XYZ1234', 'Xampú', 'E', 'ml'),
('ABC5678', 'Acelgas', 'G', 'Kg'),
('DEF9012', 'Desodorante', 'E', 'ml');

-- [VINOS Y LICORES]
INSERT INTO Productos (clave_producto, nombre, tipo, unidad) VALUES
('ALC001', 'Vino Tinto', 'G', 'lt'),
('ALC002', 'Cerveza Artesanal', 'G', 'ml'),
('ALC003', 'Whisky Escocés', 'G', 'ml'),
('ALC004', 'Tequila Reposado', 'G', 'ml'),
('ALC005', 'Gin Premium', 'G', 'ml'),
('ALC006', 'Vodka Ruso', 'G', 'ml'),
('ALC007', 'Champagne Brut', 'G', 'lt'),
('ALC008', 'Ron Añejo', 'G', 'ml'),
('ALC009', 'Licor de Café', 'G', 'ml'),
('ALC010', 'Sidra Española', 'G', 'lt'),
('ALC011', 'Vermut Italiano', 'G', 'lt'),
('ALC012', 'Absenta Suiza', 'G', 'ml'),
('ALC013', 'Cognac Francés', 'G', 'ml'),
('ALC014', 'Sake Japonés', 'G', 'ml'),
('ALC015', 'Brandy Español', 'G', 'ml'),
('ALC016', 'Cava Catalán', 'G', 'lt'),
('ALC017', 'Pisco Peruano', 'G', 'ml'),
('ALC018', 'Licores Irlandeses', 'G', 'ml'),
('ALC019', 'Aguardiente Colombiano', 'G', 'ml'),
('ALC020', 'Amaretto Italiano', 'G', 'ml');

-- [SABRITAS Y MAS]
INSERT INTO Productos (clave_producto, nombre, tipo, unidad) VALUES
('SNK0001', 'Papas Clásicas', 'E', 'pz'),
('SNK0002', 'Doritos Nacho', 'E', 'pz'),
('SNK0003', 'Lays Original', 'E', 'pz'),
('SNK0004', 'Pringles Sour Cream', 'E', 'pz'),
('SNK0005', 'Ruffles Queso', 'E', 'pz'),
('SNK0006', 'Cheetos Crunchy', 'E', 'pz'),
('SNK0007', 'Tostitos Salsa Verde', 'E', 'pz'),
('SNK0008', 'Fritos Chili Cheese', 'E', 'pz'),
('SNK0009', 'Popcorners Kettle', 'E', 'pz'),
('SNK0010', 'Sunchips Harvest Cheddar', 'E', 'pz'),
('SNK0011', 'Kettle Brand Sea Salt', 'E', 'pz'),
('SNK0012', 'Rold Gold Pretzels', 'E', 'pz'),
('SNK0013', 'Doritos Cool Ranch', 'E', 'pz'),
('SNK0014', 'Lays BBQ', 'E', 'pz'),
('SNK0015', 'Pringles Original', 'E', 'pz'),
('SNK0016', 'Cheetos Puffs', 'E', 'pz'),
('SNK0017', 'Takis Fuego', 'E', 'pz'),
('SNK0018', 'SunChips French Onion', 'E', 'pz'),
('SNK0019', 'Popcorn Indiana Aged White Cheddar', 'E', 'pz'),
('SNK0020', 'Doritos Flamin'' Hot', 'E', 'pz');




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


