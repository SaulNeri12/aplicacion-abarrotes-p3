
package plantillas;

/**
 * Contiene las instrucciones de consultas SQL para crear nuevos elementos para tablas de la
 * base de datos de "AplicacionAbarrotes".
 * @author Saul Neri
 */
public class InsercionesAbarrotes {
    public static String AGREGAR_PRODUCTO = "INSERT INTO Productos (clave_producto, nombre, tipo, unidad) VALUES (?, ?, ?, ?)";
    
}
