
package plantillas;

/**
 * Contiene las instrucciones de consultas SQL para obtener datos de las tablas de la
 * base de datos de "AplicacionAbarrotes".
 * @author Saul Neri
 */
public class ConsultasAbarrotes {
    public static String PRODUCTOS_TODOS        = "SELECT * FROM Productos";
    public static String PRODUCTOS_POR_NOMBRE   = "SELECT * FROM Productos WHERE nombre = ?";
    public static String PRODUCTOS_POR_CLAVE    = "SELECT * FROM Productos WHERE clave_producto = ?";
    
    /*
    // productos empacados
    public static String PRODUCTOS_EMPACADOS_NOMBRE     = "SELECT * FROM ProductosEmpacados WHERE nombre = '%s';";
    public static String PRODUCTO_EMPACADO              = "SELECT * FROM ProductosEmpacados WHERE clave_producto = '%s' LIMIT 1;";
    // productos granel
    public static String PRODUCTOS_GRANEL       = "SELECT * FROM ProductosGranel WHERE clave_producto = '%s';";
    public static String PRODUCTO_GRANEL_POR_NOMBRE     = "SELECT * FROM ProductosGranel WHERE clave_producto = '%s'";
    */
}
