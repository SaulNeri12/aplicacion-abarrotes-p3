package control;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
import objetosNegocio.ProductoGranel;

/**
 * Esta clase contiene métodos que generan objetos del tipo DefaultTableModel
 * y DefaultComboBoxModel para crear instancias de Jtable y JComboBox.
 * @author Equipo Siete
 */
public class Conversiones {
    private String nombreColumnasTablaProductos [] = {"Clave", "Nombre", "Tipo", "Unidad"};
    private String nombreColumnasTablaInventarios [] = {"Clave", "Nombre", "Tipo", "Unidad", "Cantidad"};
    private String nombreColumnasTablaMovimientos [] = {"Clave Mcvimiento", "Fecha", "Procesado","Producto"};
    
    /** 
     * Genera un objeto de tipo DefaultTableModel a partir de una lista de
     * productos.
     * @param listaProductos lista a convertir
     * @return  Objeto de tipo DefaultTableModel con los atributos de las
     * canciones.
     */
    public DefaultTableModel productosTableModel(List<Producto> listaProductos){      
        Object tabla[][];
        
        if (listaProductos != null) {
            tabla = new Object [listaProductos.size()][4];
            for (int i = 0; i < listaProductos.size(); i++) {
                //Obtenemos un producto de la lista de productos
                Producto producto = listaProductos.get(i);
                //Almacenamos sus atributos en la fila del arreglo
                tabla[i][0] = producto.getClave();
                tabla[i][1] = producto.getNombre();
                tabla[i][2] = producto.getTipo();
                tabla[i][3] = producto.getUnidad();
            }
            return new DefaultTableModel(tabla, nombreColumnasTablaProductos);
        }
        return null;
    }
    
    /** 
     * Genera un objeto de tipo DefaultTableModel a partir de una lista de
     * productos granel.
     * @param listaProductosGranel lista a convertir
     * @return Objeto de tipo DefaultTableModel con los atributos de las
     * canciones.
     */
    public DefaultTableModel productosGranelTableModel(List<ProductoGranel> listaProductosGranel){
        Object tabla [][];
        if (listaProductosGranel != null) {
            tabla = new Object[listaProductosGranel.size()][5];
            for (int i = 0; i < listaProductosGranel.size(); i++) {
                //Obtenemos un producto granel de la lista de productos granel
                ProductoGranel productoG = listaProductosGranel.get(i);
                //Almacenamos sus atributos en la fila del arreglo
                tabla[i][0] = productoG.getClave();
                tabla[i][1] = productoG.getNombre();
                tabla[i][2] = productoG.getTipo();
                tabla[i][3] = productoG.getUnidad();
                tabla[i][4] = productoG.getCantidad();
            }
            return new DefaultTableModel(tabla, nombreColumnasTablaInventarios);
        }
        return null;
    }
    
    /** 
     * Genera un objeto de tipo DefaultTableModel a partir de una lista de
     * productos granel.
     * @param listaProductosEmpacado lista a convertir
     * @return Objeto de tipo DefaultTableModel con los atributos de las
     * canciones.
     */
    public DefaultTableModel productosEmpacadoTableModel(List<ProductoEmpacado> listaProductosEmpacado){
        Object tabla [][];
        if (listaProductosEmpacado != null) {
            tabla = new Object[listaProductosEmpacado.size()][5];
            for (int i = 0; i < listaProductosEmpacado.size(); i++) {
                //Obtenemos un producto empacados de la lista de productos empacados
                ProductoEmpacado productoE = listaProductosEmpacado.get(i);
                //Almacenamos sus atributos en la fila del arreglo
                tabla[i][0] = productoE.getClave();
                tabla[i][1] = productoE.getNombre();
                tabla[i][2] = productoE.getTipo();
                tabla[i][3] = productoE.getUnidad();
                tabla[i][4] = productoE.getCantidad();
            }
            return new DefaultTableModel(tabla, nombreColumnasTablaInventarios);
        }
        return null;
    }
    
    /** 
     * * Genera un objeto de tipo DefaultTableModel a partir de una lista de
     * peliculas. 
     * @param listaMovimientosGranel lista a convertir
     * @return Objeto de tipo DefaultTableModel con los atributos de las
     * canciones.
     */
    public DefaultTableModel movimientosGranelTableModel(List<MovimientoGranel> listaMovimientosGranel){
        Object tabla[][];
        if (listaMovimientosGranel != null) {
            tabla = new Object[listaMovimientosGranel.size()][4];
            for (int i = 0; i < listaMovimientosGranel.size(); i++) {
                //Obtenemos un movimiento granel de la lista de movimientos granel
                MovimientoGranel movimientoG = listaMovimientosGranel.get(i);
                //Almacenamos sus atributos en la fila del arreglo
                tabla[i][0] = movimientoG.getCveMovimiento() ;
                tabla[i][1] = movimientoG.getFecha();
                tabla[i][2] = movimientoG.getProcesado();
                tabla[i][3] = movimientoG.getProductoGranel();
            }
            return new DefaultTableModel(tabla,nombreColumnasTablaMovimientos);
        }
        return null;
    }
    
    /** 
     * * Genera un objeto de tipo DefaultTableModel a partir de una lista de
     * peliculas. 
     * @param listaMovimientosEmpacado lista a convertir
     * @return Objeto de tipo DefaultTableModel con los atributos de las
     * canciones.
     */
    public DefaultTableModel movimientosEmpacadoTableModel(List<MovimientoEmpacado> listaMovimientosEmpacado){
        Object tabla[][];
        if (listaMovimientosEmpacado != null) {
            tabla = new Object[listaMovimientosEmpacado.size()][4];
            for (int i = 0; i < listaMovimientosEmpacado.size(); i++) {
                //Obtenemos un movimiento granel de la lista de movimientos granel
                MovimientoEmpacado movimientoG = listaMovimientosEmpacado.get(i);
                //Almacenamos sus atributos en la fila del arreglo
                tabla[i][0] = movimientoG.getCveMovimiento() ;
                tabla[i][1] = movimientoG.getFecha();
                tabla[i][2] = movimientoG.getProcesado();
                tabla[i][3] = movimientoG.getProductoEmpacado();
            }
            return new DefaultTableModel(tabla,nombreColumnasTablaMovimientos);
        }
        return null;
    }
    
    /** 
     * Genera un objeto de tipo DefaultComboBoxModel a partir de una lista de
     * géneros. 
     * @param listaProductosGranel lista a convertir
     * @return Objeto de tipo DefaultComboBoxModel con los atributos de las
     * canciones.
     */
    public DefaultComboBoxModel productosGranelComboBoxModel(List<ProductoGranel> listaProductosGranel){
        DefaultComboBoxModel<ProductoGranel> defaultComboBoxModel = new DefaultComboBoxModel<>();
        if (listaProductosGranel != null) {
            //Por cada elemento de la lista
            for (int i = 0; i < listaProductosGranel.size(); i++) {
                //Se agrega a la instancia de la clase DefaultComboBoxModel
                defaultComboBoxModel.addElement(listaProductosGranel.get(i));
            }
            return defaultComboBoxModel;
        }
        return null;
    }
    
    /** 
     * Genera un objeto de tipo DefaultComboBoxModel a partir de una lista de
     * géneros. 
     * @param listaProductosEmpacado lista a convertir
     * @return Objeto de tipo DefaultComboBoxModel con los atributos de las
     * canciones.
     */
    public DefaultComboBoxModel productosEmpacadoComboBoxModel(List<ProductoEmpacado> listaProductosEmpacado){
        DefaultComboBoxModel<ProductoEmpacado> defaultComboBoxModel = new DefaultComboBoxModel<>();
        if (listaProductosEmpacado != null) {
            //Por cada elemento de la lista
            for (int i = 0; i < listaProductosEmpacado.size(); i++) {
                //Se agrega a la instancia de la clase DefaultComboBoxModel
                defaultComboBoxModel.addElement(listaProductosEmpacado.get(i));
            }
            return defaultComboBoxModel;
        }
        return null;
    }
}
