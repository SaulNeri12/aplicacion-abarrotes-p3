
package plantillas;

/**
 * Contiene las instrucciones de actualizaciones SQL para modificar datos de las tablas de la
 * base de datos de "AplicacionAbarrotes".
 * @author Saul Neri
 */
public class ModificacionesAbarrotes {
    public static final String ACTUALIZAR_PRODUCTO    = "UPDATE Productos SET nombre = ?, tipo = ?, unidad = ? WHERE clave_producto = ?";
    public static final String ACTUALIZAR_PRODUCTO_EMPACADO = "UPDATE ProductosEmpacados SET cantidad = ? WHERE clave_producto = ?";
    public static final String ACTUALIZAR_PRODUCTO_GRANEL = "UPDATE ProductosGranel SET cantidad = ? WHERE clave_producto = ?";
    public static final String ACTUALIZAR_USUARIO     = "UPDATE Usuarios SET nombre = ?, telefono = ?, email = ?, contrasena = ?, rol = ? WHERE id = ?";
    public static final String ACTUALIZAR_MOVIMIENTO_GRANEL = "UPDATE MovimientosGranel SET clave_producto = ?, usuario_id = ?, cantidad = ? WHERE clave_movimiento = ?";
    public static final String ACTUALIZAR_MOVIMIENTO_EMPACADO = "UPDATE MovimientosEmpacado SET clave_producto = ?, usuario_id = ?, cantidad = ? WHERE clave_movimiento = ?";
    
}
