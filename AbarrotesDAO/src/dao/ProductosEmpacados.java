package dao;

import conexion.ConexionAbarrotesBD;
import excepciones.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
import plantillas.ConsultasAbarrotes;

/**
 *
 * @author Equipo 07
 */
public class ProductosEmpacados {
    private ConexionAbarrotesBD conexionBD;
    private List<ProductoEmpacado> productosEmpacados;
    
    /** 
     * Constructor que crea como instanica una lista de productos empacados.
     */
    public ProductosEmpacados() {
        this.conexionBD = new ConexionAbarrotesBD();
        this.productosEmpacados = new ArrayList();
    }
    
    /**
     * Crea una instancia del manejador del inventario de productos y 
     * accede a la base de datos a partir de la conexion dada.
     * @param conexion Conexion a la base de datos de abarrotes
     */
    public ProductosEmpacados(ConexionAbarrotesBD conexion) {
        this.conexionBD = conexion;
        this.productosEmpacados = new ArrayList<>();
    }
    
    /**
     * Metodo que obtiene el producto empacado que se da por parametro, siempre y cuando su clave sea la misma,
     * regresara null si no existe.
     * @param productoEmpacado Producto empacado.
     * @return Producto empacado.
     */
    public Producto obten(ProductoEmpacado productoEmpacado) throws DAOException {
        Producto producto = null;
        ProductoEmpacado empacado = null;
        
        try {
            // busca la informacion del producto en la tabla Productos
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_POR_CLAVE, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
            );
            
            stmt.setString(1, productoEmpacado.getClave());
            
            ResultSet rs = stmt.executeQuery();
            
            // si no existe el producto...
            if (!rs.next()) {
                
                rs.close();
                stmt.close();
                
                return null;
            }
            
            // indica que se requiere el primero encontrado
            rs.first();
            
            // obtiene los datos del primer producto encontrado encontrado
            String claveProducto = rs.getString("clave_producto");
            String nombre = rs.getString("nombre");
            String tipo = rs.getString("tipo");
            String unidad = rs.getString("unidad");

            producto = new Producto(
                    claveProducto,
                    nombre, 
                    tipo.toCharArray()[0], 
                    unidad
            );
            
            // busca la informacion del producto empacado en la tabla ProductosEmpacados
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_EMPACADOS_POR_CLAVE, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
            );
            
            stmt.setString(1, productoEmpacado.getClave());
            
            rs = stmt.executeQuery();
            
            // si no existe el producto empacado...
            if (!rs.next()) {
                
                rs.close();
                stmt.close();
                
                return null;
            }
            
            // indica que se requiere el primero encontrado
            rs.first();
            
            empacado = new ProductoEmpacado(producto);
            
            int cantidad = rs.getInt("cantidad");
            
            empacado.setCantidad(cantidad);
            
            System.out.println("#" + empacado);
            
            rs.close();
            stmt.close();
            
            return empacado;
            
        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
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
