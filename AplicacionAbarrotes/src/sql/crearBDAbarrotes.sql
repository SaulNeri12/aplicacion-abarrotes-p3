
--	PASOS PARA CREAR LA BASE DE DATOS 
--
--		CONFIGURA LA BASE DATOS PICANDOLE EN "CONFIG" EN LA VENTANA DEL XAMPP EN LA 
--		OPCION DE MYSQL Y PON EL PUERTO EN 9999 EN EL ARCHIVO DE TEXTO
--
--		1. Abre XAMPP y inicia el servidor de MySQL
--		2. Metete al CMD y ejecuta este comando 
--
--			mysql -u root -P 9999
--
--			(ASEGURATE DE QUE EL PUERTO EN TU MYSQL DE XAMPP SEA 9999 PORQUE EL PROYECTO ESTA 
--			PENSADO PARA ESE, SINO PONES 9999 NO VA A CONECTARSE A LA BASE DE DATOS)
--
--		3. Despues de que haya entrado a MySQL (MariaDB) ejecuta este comando (CON TODO Y PUNTO Y COMA )
--
--			create database abarrotes;
--
--		4. Si se creo debe aparecer algo como "0 rows affected", despues de eso ejecutaras otro comando
--			(PRIMERO ASEGURATE DE QUE ESTAS EN LA CARPETA "sql" DEL PROYECTO DE JAVA Y DESPUES EJECUTAS ESTO)
--
--			source crearBDAbarrotes.sql;
--
--		5. Debe de aparecer algo como "Query OK, 0 rows affected (0.053 sec)" 6 veces
--		6. Asegurate de que se crearon todas las tablas ejecutando el comando
--			
--			show tables;

-- @author: Saul Neri

-- [ Tablas de productos ]
CREATE TABLE IF NOT EXISTS Productos (
	clave_producto VARCHAR(10) NOT NULL UNIQUE,
	nombre VARCHAR(50) NOT NULL,
	tipo ENUM('E', 'G'),
	unidad VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS ProductosGranel (
	clave_producto VARCHAR(10) NOT NULL,
	cantidad FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS ProductosEmpacados (
	clave_producto VARCHAR(10) NOT NULL,
	cantidad BIGINT NOT NULL
);

-- [ Tablas de Movimientos ]
CREATE TABLE IF NOT EXISTS Movimientos (
	clave_movimiento VARCHAR(10) NOT NULL UNIQUE,
	fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	ultimo_cambio TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS MovimientosEmpacado (
	clave_movimiento VARCHAR(10) NOT NULL,
	clave_producto VARCHAR(10) NOT NULL,
	cantidad BIGINT NOT NULL -- checar si es optimo despues...
);

CREATE TABLE IF NOT EXISTS MovimientosGranel (
	clave_movimiento VARCHAR(10) NOT NULL,
	clave_producto VARCHAR(10) NOT NULL,
	cantidad FLOAT NOT NULL -- checar si es optimo despues...
);
