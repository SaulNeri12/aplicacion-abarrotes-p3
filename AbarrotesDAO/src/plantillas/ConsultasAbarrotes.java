
package plantillas;

/**
 * Contiene las instrucciones de consultas SQL para obtener datos de las tablas de la
 * base de datos de "AplicacionAbarrotes".
 * @author Saul Neri
 */
public class ConsultasAbarrotes {
    public static String PRODUCTOS_TODOS        = "SELECT * FROM Productos LIMIT ? OFFSET ?";
    public static String PRODUCTOS_POR_NOMBRE   = "SELECT * FROM Productos WHERE nombre = ?";
    public static String PRODUCTOS_POR_CLAVE    = "SELECT * FROM Productos WHERE clave_producto = ?";
    public static String PRODUCTOS_EMPACADOS_TODOS = "SELECT * FROM ProductosEmpacados LIMIT ? OFFSET ?";
    
    public static String USUARIOS_TODOS         = "SELECT * FROM Usuarios LIMIT ? OFFSET ?";
    public static String USUARIOS_POR_ID        = "SELECT * FROM Usuarios WHERE id = ?";
    
    // productos empacados
    public static String PRODUCTOS_EMPACADOS_POR_NOMBRE = "SELECT * FROM ProductosEmpacados WHERE nombre = ?";
    public static String PRODUCTOS_EMPACADOS_POR_CLAVE  = "SELECT * FROM ProductosEmpacados WHERE clave_producto = ?";
    // productos granel
    public static String PRODUCTOS_GRANEL       = "SELECT * FROM ProductosGranel WHERE clave_producto = ?";
    public static String PRODUCTO_GRANEL_POR_NOMBRE     = "SELECT * FROM ProductosGranel WHERE clave_producto = ?";
}
