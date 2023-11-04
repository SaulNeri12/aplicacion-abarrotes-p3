package objetosNegocio;

import objetosServicio.Fecha;

/**
 * Clase sobre los movimientos de productos a granel.
 * @author Enrique Rodriguez
 */
public class MovimientoGranel extends Movimiento{
    private ProductoGranel productoGranel;
    
    /**
     * Crea un nuevo movimiento de producto a granel sin datos asignados
     */
    public MovimientoGranel() {
    }
    
    /**
     * Crea un nuevo movimiento de producto a granel con los datos dados
     * @param cveMovimiento Clave del movimiento.
     * @param fecha Fecha del movimiento.
     * @param procesado Valor booleano dependiendo si el producto fue o 
     * no procesado
     * 
     * @param productoGranel Producto de clase a granel.
     */
    public MovimientoGranel(String cveMovimiento, Fecha fecha, boolean procesado, ProductoGranel productoGranel) {
       super(cveMovimiento, fecha, procesado);
       this.productoGranel = productoGranel;
    }
    
    /**
     * Método constructor que reciba sólo la clave del movimiento e inicialice 
     * con ella el atributo de su clase, los demás atributos de la clase se 
     * inicializarán a null o false.
     * 
     * @param cveMovimiento Clave del movimiento.
     */
    public MovimientoGranel(String cveMovimiento) {
        super(cveMovimiento);
        this.setProcesado(false);
        
    }    
    
    /**
     * Método que obtiene un producto de tipo granel.
     * 
     * @return Producto de tipo a granel. 
     */
    public ProductoGranel getProductoGranel() {
        return productoGranel;
    }
    
    /**
     * Método que asigna un producto de tipo granel.
     * 
     * @param productoGranel Producto de tipo a granel. 
     */
    public void setProductoGranel(ProductoGranel productoGranel) {
        this.productoGranel = productoGranel;
    }
    
    /**
     * Método que regresa una cadena con los valores de la clase 
     * (de su superclase y los propios).  Los valores de los atributos irán 
     * separados por comas.
     * 
     * @return Cadena de texto.
     */
    @Override
    public String toString() {
        return  super.toString()+ ", " + productoGranel;
    }
}
