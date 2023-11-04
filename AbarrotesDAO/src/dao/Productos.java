package dao;

import excepciones.DAOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import objetosNegocio.Producto;

/**
 *
 * @author Enrique Rodriguez
 */
public class Productos extends Producto {

private List<Producto> productos;
    
    /** 
     * Constructor que crea como instanica una lista de Productos para los productos del abarrotes.
     */
    public Productos(){
        this.productos = new ArrayList<Producto>();
    }
    
    /** 
     * Metodo que obtiene el producto que se da por parametro, siempre y cuando su clave sea la misma,
     * regresara null si no existe.
     * @param producto producto a enviar
     * @return regresa el producto que se busca en la lista
     */
    public Producto obten(Producto producto){
        Producto product1 = null;
        Iterator<Producto> it = productos.iterator();
        while (it.hasNext()) {
            product1 = it.next();
            if (product1.equals(producto)) {
                return product1;
            }
        }
        return null;
    }
    
    /** 
     * Metodo que sin restringir clave repetida, agrega a la lista un producto dado.
     * @param producto producto
     */
    public void agrega(Producto producto){
        if (!productos.contains(producto)) {
           productos.add(producto); 
        }
    }
    
    /** 
     * Metodo que reemplaza un producto de la lista (si existe), por el producto dado en el parametro,
     * deben tener la misma clave.
     * @param producto prodcuto
     * @throws excepciones.DAOException error
     */
    public void actualiza(Producto producto) throws DAOException {
        int i = productos.indexOf(producto);
        if (i>=0) {
            productos.set(i, producto);
        }
        else {
            throw new DAOException("El producto no se pudo actualizar");
        }
    }

    /**
     * Metodo que elimina un producto de la lista (si existe), por el producto
     * dado en el parametro, deben tener la misma clave.
     *
     * @param producto producto
     * @throws excepciones.DAOException error
     */
    public void elimina(Producto producto) throws DAOException {
        if (!productos.remove(producto)) {
            throw new DAOException("El producto no se pudo eliminar");
        }
    }

    /**
     * Metodo que muestra la lista de todos los productos del abarrotes.
     *
     * @return regresa los productos
     */
    public List<Producto> lista() {
        return productos;
    }
    
    /** 
     * Metodo que muestra una lista de productos de un mismo tipo.
     * @param tipo tipo de producto
     * @return regresa lista de productos de un mismo tipo
     */
    public List<Producto> lista(char tipo) {
        List<Producto>productosTipo = new ArrayList();
        for (Producto producto : productos) {
            if (producto.getTipo()==tipo) {
                productosTipo.add(producto);
            }
        }  
        return productosTipo;
    }

}
