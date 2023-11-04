package objetosNegocio;

import objetosServicio.Fecha;

/**
 * Clase sobre los movimientos de productos empacados.
 * @author Enrique Rodriguez
 */
public class MovimientoEmpacado extends Movimiento{
    private ProductoEmpacado productoEmpacado;
    
    /**
     * El constructor por ausencia.
     */
    public MovimientoEmpacado() {
    }
    
    /**
     * Método constructror que inicializa los atributos de la clase al de sus 
     * parámetros.
     * 
     * @param cveMovimiento Clave del movimiento.
     * @param fecha Fecha del movimiento.
     * @param procesado Valor booleano dependiendo si el producto fue o 
     * no procesado
     * @param productoEmpacado Producto de clase empacado.
     */
    public MovimientoEmpacado(String cveMovimiento, Fecha fecha, boolean procesado, ProductoEmpacado productoEmpacado) {
       super(cveMovimiento, fecha, procesado);
       this.productoEmpacado = productoEmpacado;
    }
    
    /**
     * Método constructor que reciba sólo la clave del movimiento e inicialice 
     * con ella el atributo de su clase, los demás atributos de la clase se 
     * inicializarán a null o false.
     * 
     * @param cveMovimiento Clave del movimiento.
     */
    public MovimientoEmpacado(String cveMovimiento) {
        super(cveMovimiento);
        this.setProcesado(false);
        
    }    
    
    /**
     * Método que obtiene un producto de tipo empacado.
     * 
     * @return Producto de tipo empacado.
     */
    public ProductoEmpacado getProductoEmpacado() {
        return productoEmpacado;
    }
    
    /**
     * Método que obtiene un producto de tipo empacado.
     * 
     * @param productoEmpacado Producto de tipo empacado.
     */
    public void setProductoEmpacado(ProductoEmpacado productoEmpacado) {
        this.productoEmpacado = productoEmpacado;
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
        return  super.toString()+ ", " + productoEmpacado;
    }

    
}
