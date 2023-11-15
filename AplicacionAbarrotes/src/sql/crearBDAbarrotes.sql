
-- @author: Jesus Castro

-- [ Tablas de productos ]
CREATE TABLE IF NOT EXISTS Productos (
	clave_producto VARCHAR(10) PRIMARY KEY,
	nombre VARCHAR(50) NOT NULL,
	tipo ENUM('E', 'G'),
	unidad VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS ProductosGranel (
	clave_producto VARCHAR(10) PRIMARY KEY,
	cantidad FLOAT NOT NULL,
    FOREIGN KEY (clave_producto) REFERENCES Productos(clave_producto)
);

CREATE TABLE IF NOT EXISTS ProductosEmpacados (
	clave_producto VARCHAR(10) PRIMARY KEY,
	cantidad BIGINT NOT NULL,
    FOREIGN KEY (clave_producto) REFERENCES Productos(clave_producto)
);

-- [ Tablas de Movimientos ]
CREATE TABLE IF NOT EXISTS Movimientos (
	clave_movimiento VARCHAR(10) PRIMARY KEY,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	ultimo_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS MovimientosEmpacado (
	clave_movimiento VARCHAR(10) PRIMARY KEY,
	clave_producto VARCHAR(10) NOT NULL,
	cantidad BIGINT NOT NULL, -- checar si es optimo despues...
    FOREIGN KEY (clave_movimiento) REFERENCES Movimientos(clave_movimiento)
);

CREATE TABLE IF NOT EXISTS MovimientosGranel (
	clave_movimiento VARCHAR(10) PRIMARY KEY,
	clave_producto VARCHAR(10) NOT NULL,
	cantidad FLOAT NOT NULL, -- checar si es optimo despues...
    FOREIGN KEY (clave_movimiento) REFERENCES Movimientos(clave_movimiento)
);

CREATE TABLE IF NOT EXISTS usuarios (
	id INT(6) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    email VARCHAR(50) NOT NULL,
	contrasena VARCHAR(65) NOT NULL,
    rol ENUM('administrador', 'repartidor') NOT NULL
) AUTO_INCREMENT = 100000;


