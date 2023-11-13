-- @author: Saul Neri

-- Este limitador se encarga de hacer que no se pueda ingresar un 
-- producto de tipo Granel (G) en la tabla de productos empacados.
CREATE TRIGGER verificaTipoEmpacado BEFORE INSERT ON ProductosEmpacados
FOR EACH ROW
BEGIN
	DECLARE tipo_producto CHAR(1);
	-- cuenta los si hay resultados con el ID del producto empacado
	-- a agregar a la tabla
	SELECT tipo INTO tipo_producto
	FROM Productos WHERE clave_producto = NEW.clave_producto;

	-- verifica si el producto que se ingresara NO es de tipo Empacado (E)
	IF tipo_producto <> 'G' THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'No se pudo guardar el producto debido a que es tipo Granel';
	END IF;
END;

-- Este limitador se encarga de hacer que no se pueda ingresar un 
-- producto de tipo Empacado (E) en la tabla de productos a granel (G).
CREATE TRIGGER verificaTipoGranel BEFORE INSERT ON ProductosGranel
FOR EACH ROW
BEGIN
	DECLARE tipo_producto CHAR(1);
	-- cuenta los si hay resultados con el ID del producto empacado
	-- a agregar a la tabla
	SELECT tipo INTO tipo_producto
	FROM Productos WHERE clave_producto = NEW.clave_producto;

	-- verifica si el producto que se ingresara NO es de tipo Empacado (E)
	IF tipo_producto <> 'E' THEN
		SIGNAL SQLSTATE '45000'
		SET MESSAGE_TEXT = 'No se pudo guardar el producto debido a que es tipo Empacado';
	END IF;
END;


