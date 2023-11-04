package dao;

import excepciones.DAOException;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;

/**
 *
 * @author Equipo 07
 */
public class ProductosEmpacados {

    private List<ProductoEmpacado> productosEmpacados;
    
    /** 
     * Constructor que crea como instanica una lista de productos empacados.
     */
    public ProductosEmpacados() {
        this.productosEmpacados = new ArrayList();
    }
    
    /**
     * Metodo que obtiene el producto empacado que se da por parametro, siempre y cuando su clave sea la misma,
     * regresara null si no existe.
     * 
     * @param productoEmpacado Producto empacado.
     * @return Producto empacado.
     */
    public Producto obten(ProductoEmpacado productoEmpacado) {
        if (productosEmpacados.contains(productoEmpacado)){
         return productoEmpacado;   
        }else{
            return null;
        }

    }
    
    /**
     * Metodo que sin restringir clave repetida, agrega a la lista un producto empacado dado.
     * 
     * @param productoEmpacado Producto empacado.
     */
    public void agrega(ProductoEmpacado productoEmpacado) {
        productosEmpacados.add(productoEmpacado);

    }
    
    /**
     * Metodo que reemplaza un producto granel de la lista (si existe), por el producto empacado dado en el parametro,
     * deben tener la misma clave.
     * 
     * @param productoEmpacado Producto empacado.
     * @throws DAOException error
     */
    
    public void actualiza(ProductoEmpacado productoEmpacado) throws DAOException {
        boolean check = false;
        for (int i = 0; i < productosEmpacados.size(); i++) {
            if (productoEmpacado.equals(productosEmpacados.get(i))) {
                productosEmpacados.set(i, productoEmpacado);
                check = true;
                break;
            }
            if (check == false) {
                throw new DAOException("**El producto a actualizar no existe**");
            }
        }
    }
    
    /**
     * Metodo que elimina un producto granel de la lista (si existe), por el producto graneñ dado en el parametro,
     * deben tener la misma clave.
     * 
     * @param productoEmpacado Producto empacado.
     * @throws DAOException error
     */
    public void elimina(ProductoEmpacado productoEmpacado) throws DAOException {
        if (!productosEmpacados.remove(productoEmpacado)) {
            throw new DAOException("El producto no se pudo eliminar");
        }
    }
    
    /**
     * Metodo el cual muestra la lista de productos granel.
     * 
     * @return regresa la lista
     */
    public List<ProductoEmpacado> lista() {
        return productosEmpacados;
    }

}
