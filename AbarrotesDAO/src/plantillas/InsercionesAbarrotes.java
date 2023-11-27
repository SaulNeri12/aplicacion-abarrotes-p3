
package plantillas;

/**
 * Contiene las instrucciones de consultas SQL para crear nuevos elementos para tablas de la
 * base de datos de "AplicacionAbarrotes".
 * @author Saul Neri
 */
public class InsercionesAbarrotes {
    public static String AGREGAR_PRODUCTO           = "INSERT INTO Productos (clave_producto, nombre, tipo, unidad) VALUES (?, ?, ?, ?)";
    public static String AGREGAR_PRODUCTO_EMPACADO  = "INSERT INTO ProductosEmpacados (clave_producto, cantidad) VALUES (?, ?)";
    public static String AGREGAR_PRODUCTO_GRANEL    = "INSERT INTO ProductosGranel (clave_producto, cantidad) VALUES (?, ?)";
    public static String REGISTRAR_USUARIO          = "INSERT INTO Usuarios (id, nombre, telefono, email, contrasena, rol) VALUES (?, ?, ?, ?, ?, ?)";
    public static String AGREGAR_MOVIMIENTO_GRANEL  = "INSERT INTO MovimientosGranel (clave_movimiento, usuario_id, clave_producto, cantidad, procesado) VALUES (?,?,?,?,?)";
    public static String AGREGAR_MOVIMIENTO_EMPACADO  = "INSERT INTO MovimientosEmpacado (clave_movimiento, usuario_id, clave_producto, cantidad, procesado) VALUES (?,?,?,?,?)";
    
}
