package objetosNegocio;

/**
 * Clase referente a un producto empacado.
 * 
 * @author Enrique Rodriguez
 */
public class ProductoEmpacado extends Producto {
    
    private int cantidad;
    
    /**
     *  El constructor por ausencia.
     */
    public ProductoEmpacado(){
        
    }
    
    /**
     * Un constructor que reciba como parámetros un objeto de tipo Producto y 
     * una cantidad (entera o flotante) y que inicialice con ellos los atributos
     * de su clase padre y de su clase al valor de los parámetros.
     * 
     * @param producto Producto.
     * @param cantidad Cantidad del producto.
     */
    public ProductoEmpacado(Producto producto, int cantidad) {
        super(producto);
        this.cantidad = cantidad;
    }
    
    /**
     * Un constructor que reciba sólo un producto e inicialice con él el atributo
     * de su clase padre, el atributo cantidad se inicializará a 0.
     * 
     * @param producto Producto.
     */
    public ProductoEmpacado(Producto producto){
        super(producto);
        this.cantidad=0;
    }
    
    /**
     * Método que obtiene la cantidad del producto.
     * 
     * @return Cantidad del producto.
     */
    public int getCantidad() {
        return cantidad;
    }
    
    /**
     * Método que asigna la cantidad del producto.
     * 
     * @param cantidad Cantidad del producto.
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    /**
     * El método toString() que regresa una cadena con los valores de los 
     * atributos de la clase (de su superclase y los propios). Los valores de 
     * los atributos irán separados por comas sin saltos de línea u otra 
     * información adicional.
     * 
     * @return Cadena de texto con los atributos.
     */
    @Override
    public String toString() {
        return super.toString() +", " + cantidad;
    }
    
    
}
