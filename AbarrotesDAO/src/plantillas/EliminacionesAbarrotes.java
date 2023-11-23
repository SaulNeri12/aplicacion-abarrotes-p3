
package plantillas;

/**
 * Contiene las instrucciones de eliminicaciones SQL necesarias para eliminar elementos de
 * Abarrotes de la base de datos
 * base de datos de "AplicacionAbarrotes".
 * @author Saul Neri
 */
public class EliminacionesAbarrotes {
    public static String ELIMINAR_PRODUCTO_EMPACADO = "DELETE FROM ProductosEmpacados WHERE clave_producto = ?";
    public static String ELIMINAR_PRODUCTO      = "DELETE FROM Productos WHERE clave_producto = ?";
    public static String ELIMINAR_MOVIMIENTO    = "DELETE FROM Movimientos WHERE clave_movimiento = ?";
    public static String ELIMINAR_USUARIO       = "DELETE FROM Usuarios WHERE id = ?";
}
