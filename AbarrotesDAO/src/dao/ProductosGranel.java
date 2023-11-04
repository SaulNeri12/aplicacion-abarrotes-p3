package dao;

import excepciones.DAOException;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.Producto;
import objetosNegocio.ProductoGranel;

/**
 *
 * @author Equipo
 */
public class ProductosGranel {
    private List<ProductoGranel> productosGranel;
    
    /** 
     * Constructor que crea como instanica una lista de productos a granel.
     */
    public ProductosGranel(){
        this.productosGranel = new ArrayList();
    }
    
    /** 
     * Metodo que obtiene el producto granel que se da por parametro, siempre y cuando su clave sea la misma,
     * regresara null si no existe.
     * @param productoGranel Producto a granel.
     * 
     * @return Producto a granel.
     */
    public Producto obten(ProductoGranel productoGranel){
        if (productosGranel.contains(productoGranel)){
         return productoGranel;   
        }else{
            return null;
        }
    }
    
    /** 
     * Metodo que sin restringir clave repetida, agrega a la lista un producto granel dado.
     * 
     * @param productoGranel Producto a granel.
     */
    public void agrega(ProductoGranel productoGranel){
        productosGranel.add(productoGranel);
    }
    
    /** 
     * Metodo que reemplaza un producto granel de la lista (si existe), por el producto granel dado en el parametro,
     * deben tener la misma clave.
     * 
     * @param productoGranel Producto a granel.
     * @throws excepciones.DAOException error
     */
    public void actualiza(ProductoGranel productoGranel) throws DAOException{
        boolean check = false;
        for (int i = 0; i < productosGranel.size(); i++) {
            if (productoGranel.equals(productosGranel.get(i))) {
                productosGranel.set(i, productoGranel);
                check= true;
                break;
            }
            if (check==false) {
                throw new DAOException("**El producto a actualizar no existe**");
            }
        }
    }
    
    /** 
     * Metodo que elimina un producto granel de la lista (si existe), por el producto graneñ dado en el parametro,
     * deben tener la misma clave.
     * 
     * @param productoGranel Producto a granel.
     * @throws excepciones.DAOException error
     */
    public void elimina(ProductoGranel productoGranel) throws DAOException{
        if (!productosGranel.remove(productoGranel)) {
            throw new DAOException("El producto granel no se pudo eliminar");
        }
    }
    
    /** 
     * Metodo el cual muestra la lista de productos granel.
     * 
     * @return regresa la lista de productos granel.
     */
    public List lista(){
        return productosGranel;
    }
}
