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
import plantillas.EliminacionesAbarrotes;
import plantillas.ErrorMySQL;
import plantillas.InsercionesAbarrotes;
import plantillas.ModificacionesAbarrotes;

/**
 * Se encarga de obtener, agregar, actualizar y eliminar productos empacados del
 * sistema de abarrotes.
 * @author Saul Neri
 */
public class ProductosEmpacados {
    private ConexionAbarrotesBD conexionBD;
    private List<ProductoEmpacado> productosEmpacados;
    
    private Productos productos;
    
    // variables necesarias para paginacion de consulta de Productos
    private int desplazamiento = 0;
    private int limiteListaProductos = 30;
    
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
        this.productos = new Productos(this.conexionBD);
    }
    
    /**
     * Metodo que obtiene el producto empacado que se da por parametro, siempre y cuando su clave sea la misma,
     * regresara null si no existe.
     * @param productoEmpacado Producto empacado.
     * @return Producto empacado.
     */
    public ProductoEmpacado obten(ProductoEmpacado productoEmpacado) throws DAOException {
        Producto producto = null;
        ProductoEmpacado empacado = null;
    
        producto = productos.obten(productoEmpacado);
    
        if (producto.getTipo() != 'E') {
            throw new DAOException("El producto dado no es un producto empacado");
        }
        
        try {
            // busca la informacion del producto en la tabla Productos
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_EMPACADOS_POR_CLAVE, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
            );
            
            stmt.setString(1, productoEmpacado.getClave());
            
            ResultSet rs = stmt.executeQuery();
            
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
            //System.out.println(ex.getErrorCode());
            //System.out.println(ex.getMessage());
            throw new DAOException("No se pudo obtener el producto empacado debido a un error interno, intentelo mas tarde...");
        }
    }
    
    /**
     * Agrega el producto empacado al inventario con una cantidad especificada en
     * el mismo producto.
     * @param productoEmpacado Producto empacado a agregar (inventariar)
     */
    public void agrega(ProductoEmpacado productoEmpacado) throws DAOException {
        PreparedStatement stmt;
        
        Producto producto = productos.obten(productoEmpacado);
        
        //System.out.println("AG ### " + producto.getTipo());
       
        if (producto.getTipo() != 'E') {
            throw new DAOException("El producto dado no es un producto empacado");
        }
        
        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(InsercionesAbarrotes.AGREGAR_PRODUCTO_EMPACADO);

            stmt.setString(1, productoEmpacado.getClave());
            stmt.setInt(2, productoEmpacado.getCantidad());

            int numAnadidos = stmt.executeUpdate();
            
            //System.out.println("# anadidos: "+numAnadidos);
            
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            if (ex.getErrorCode() == ErrorMySQL.DUPLICATE) {
                throw new DAOException("El producto empacado ya se encuentra registrado");
            }
            throw new DAOException("No se pudo agregar el producto empacado debido a un error interno, intentelo mas tarde...");
        }
    }
    
    /**
     * Reemplaza un producto granel de la lista si este existe, por el producto empacado dado en el parametro,
     * deben tener la misma clave.
     * @param productoEmpacado Producto empacado.
     * @throws DAOException error
     */
    public void actualiza(ProductoEmpacado productoEmpacado) throws DAOException {
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(ModificacionesAbarrotes.ACTUALIZAR_PRODUCTO_EMPACADO);

            stmt.setInt(1, productoEmpacado.getCantidad());
            // WHERE clave_producto = ...
            stmt.setString(2, productoEmpacado.getClave());

            int numModificados = stmt.executeUpdate();

            if (numModificados <= 0) {
                throw new DAOException("No se pudo actualizar el producto empacado ya que no se encuentra registrado");
            }

            //System.out.println(numModificados + "shesh");
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException("No se pudo actualizar el producto empacado debido a un error, intentelo mas tarde...");
        }
    }
    
    /**
     * Elimina el producto empacado dado si este existe en el inventario.
     * @param productoEmpacado Producto empacado.
     * @throws DAOException Si ocurre un error en la eliminacion del producto empacado
     */
    public void elimina(ProductoEmpacado productoEmpacado) throws DAOException {
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(EliminacionesAbarrotes.ELIMINAR_PRODUCTO_GRANEL);

            // WHERE clave_producto = ...
            stmt.setString(1, productoEmpacado.getClave());

            int numEliminados = stmt.executeUpdate();

            if (numEliminados <= 0) {
                throw new DAOException("El producto empacado no se encuentra inventariado");
            }

            stmt.close();

        } catch (SQLException ex) {
            //System.out.println("#" + ex.getClass());
            throw new DAOException("No se pudo eliminar el producto empacado debido a un error, intentelo mas tarde...");
        }
    }
    
    /**
     * Carga un determinado numero de productos empacados de la base de datos para ser
     * ingresados a la lista de acceso rapido.
     * @throws DAOException Si ocurre un error de busqueda
     */
    public void consultarProductosEmpacados() throws DAOException {
        Producto productoEncontrado = null;

        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_EMPACADOS_TODOS,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, this.limiteListaProductos);
            stmt.setInt(2, this.desplazamiento);

            ResultSet rs = stmt.executeQuery();

            rs.first();
            
            // obtiene el primer producto encontrado...
            String claveProducto = rs.getString("clave_producto");
            int cantidad = rs.getInt("cantidad");

            productoEncontrado = productos.obten(new Producto(claveProducto));
            
            if (productoEncontrado == null) {
                rs.close();
                stmt.close();
                throw new DAOException("Algo salio mal en la consulta, intentelo mas tarde...");
            }
            
            ProductoEmpacado productoEmpacado = new ProductoEmpacado(
                productoEncontrado,
                cantidad   
            );

            productosEmpacados.add(productoEmpacado);
            
            this.desplazamiento++;
            
            // por cada producto en la busqueda...
            while (rs.next()) {
                // se obtienen los productos siguientes al primero...          
                productoEncontrado = productos.obten(new Producto(claveProducto));

                productoEmpacado = new ProductoEmpacado(
                        productoEncontrado,
                        cantidad
                );
                
                if (productoEncontrado == null) {
                    rs.close();
                    stmt.close();
                    throw new DAOException("Algo salio mal en la consulta, intentelo mas tarde...");
                }
                
                productosEmpacados.add(productoEmpacado);
                
                this.desplazamiento++;
            }

            rs.close();
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException(ex.getMessage());
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
