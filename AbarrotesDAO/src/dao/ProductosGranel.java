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
import objetosNegocio.ProductoGranel;
import plantillas.ConsultasAbarrotes;
import plantillas.EliminacionesAbarrotes;
import plantillas.ErrorMySQL;
import plantillas.InsercionesAbarrotes;
import plantillas.ModificacionesAbarrotes;

/**
 *
 * @author Equipo
 */
public class ProductosGranel {    
    private Productos productos;
    
    private ConexionAbarrotesBD conexionBD;
    // donde se alojaran los productos que se vayan guardando con la PAGINACION
    private List<ProductoGranel> productosGranel;
    // variables necesarias para paginacion de consulta de Productos
    private int desplazamiento = 0;
    private int limiteListaProductos = 20;    
    
    /**
     * Crea una instancia del manejador del inventario de productos a granel y 
     * accede a la base de datos a partir de la conexion dada.
     * @param conexion Conexion a la base de datos de abarrotes
     */
    public ProductosGranel (ConexionAbarrotesBD conexion) {
        this.conexionBD = conexion;
        this.productosGranel = new ArrayList<>();
        this.productos = new Productos(this.conexionBD);
    }
    
    /** 
     * Obtiene el producto granel especificado si se encuentra en el inventario, 
     * siempre y cuando su clave sea la misma, regresara null si no existe.
     * @param productoGranel Producto a granel
     * @return Producto a granel.
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public Producto obten(ProductoGranel productoGranel) throws DAOException{
        Producto producto = null;
        ProductoGranel granel = null;
    
        producto = productos.obten(productoGranel);
    
        if (producto.getTipo() != 'G') {
            throw new DAOException("El producto dado no es un producto a granel");
        }
        
        try {
            // busca la informacion del producto en la tabla Productos
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_GRANEL_POR_CLAVE, 
                    ResultSet.TYPE_SCROLL_SENSITIVE, 
                    ResultSet.CONCUR_UPDATABLE
            );
            
            stmt.setString(1, productoGranel.getClave());
            
            ResultSet rs = stmt.executeQuery();
               
            
            
            // si no existe el producto empacado...
            if (!rs.next()) {
                
                rs.close();
                stmt.close();
                
                return null;
            }
            
            // indica que se requiere el primero encontrado
            rs.first();
            
            granel = new ProductoGranel(producto);
            
            float cantidad = rs.getFloat("cantidad");
            
            granel.setCantidad(cantidad);
            
            //System.out.println("#" + empacado);
            
            rs.close();
            stmt.close();
            
            return granel;
            
        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            //System.out.println(ex.getMessage());
            throw new DAOException("No se pudo obtener el producto granel debido a un error interno, intentelo mas tarde...");
        }
    }
    
    /** 
     * Agrega el producto a granel dado al inventario
     * @param productoGranel Producto a granel.
     * @throws excepciones.DAOException
     */
    public void agrega(ProductoGranel productoGranel) throws DAOException {
        PreparedStatement stmt;
        
        Producto producto = productos.obten(productoGranel);
       
        if (producto.getTipo() != 'G') {
            throw new DAOException("El producto dado no es un producto granel");
        }
        
        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(InsercionesAbarrotes.AGREGAR_PRODUCTO_GRANEL);

            stmt.setString(1, productoGranel.getClave());
            stmt.setFloat(2, productoGranel.getCantidad());

            int numAnadidos = stmt.executeUpdate();
            
            //System.out.println("# anadidos: "+numAnadidos);
            
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            if (ex.getErrorCode() == ErrorMySQL.DUPLICATE) {
                throw new DAOException("El producto a granel ya se encuentra registrado");
            }
            throw new DAOException("No se pudo agregar el producto a granel debido a un error interno, intentelo mas tarde...");
        }
    }
    
    /** 
     * Actualiza la cantidad del producto a granel dado reemplazando la cantidad
     * del producto guardado en la base de datos, con la cantidad del producto 
     * a granel dado como parametro.
     * @param productoGranel Producto a granel
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void actualiza(ProductoGranel productoGranel) throws DAOException{
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(ModificacionesAbarrotes.ACTUALIZAR_PRODUCTO_GRANEL);

            stmt.setFloat(1, productoGranel.getCantidad());
            // WHERE clave_producto = ...
            stmt.setString(2, productoGranel.getClave());

            int numModificados = stmt.executeUpdate();

            if (numModificados <= 0) {
                throw new DAOException("No se pudo actualizar el producto a granel ya que no se encuentra registrado");
            }

            //System.out.println(numModificados + "shesh");
            stmt.close();

        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode());
            throw new DAOException("No se pudo actualizar el producto a granel debido a un error, intentelo mas tarde...");
        }
    }
    
    /** 
     * Elimina del inventario de productos a granel el producto especificado
     * @param productoGranel Producto a granel
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void elimina(ProductoGranel productoGranel) throws DAOException{
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(EliminacionesAbarrotes.ELIMINAR_PRODUCTO_EMPACADO);

            // WHERE clave_producto = ...
            stmt.setString(1, productoGranel.getClave());

            int numEliminados = stmt.executeUpdate();

            if (numEliminados <= 0) {
                throw new DAOException("El producto a granel no se encuentra inventariado");
            }

            stmt.close();

        } catch (SQLException ex) {
            //System.out.println("#" + ex.getClass());
            throw new DAOException("No se pudo eliminar el producto a granel debido a un error, intentelo mas tarde...");
        }
    }
    
    /**
     * Carga un determinado numero de productos a granel de la base de datos para ser
     * ingresados a la lista de acceso rapido.
     * @throws DAOException Si ocurre un error de busqueda
     */
    public void consultarProductosGranel() throws DAOException {
        Producto productoEncontrado = null;

        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_GRANEL_TODOS,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, this.limiteListaProductos);
            stmt.setInt(2, this.desplazamiento);

            ResultSet rs = stmt.executeQuery();

            rs.first();
            
            // obtiene el primer producto encontrado...
            String claveProducto = rs.getString("clave_producto");
            float cantidad = rs.getFloat("cantidad");

            productoEncontrado = productos.obten(new Producto(claveProducto));
            
            if (productoEncontrado == null) {
                rs.close();
                stmt.close();
                throw new DAOException("Algo salio mal en la consulta, intentelo mas tarde...");
            }
            
            ProductoGranel productoGranel = new ProductoGranel(
                productoEncontrado,
                cantidad   
            );

            productosGranel.add(productoGranel);
            
            // por cada producto en la busqueda...
            while (rs.next()) {
                // se obtienen los productos siguientes al primero...          
                productoEncontrado = productos.obten(new Producto(claveProducto));

                productoGranel = new ProductoGranel(
                        productoEncontrado,
                        cantidad
                );
                
                if (productoEncontrado == null) {
                    rs.close();
                    stmt.close();
                    throw new DAOException("Algo salio mal en la consulta, intentelo mas tarde...");
                }
                
                productosGranel.add(productoGranel);
            }

            rs.close();
            stmt.close();

            this.desplazamiento += this.limiteListaProductos;

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException(ex.getMessage());
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
