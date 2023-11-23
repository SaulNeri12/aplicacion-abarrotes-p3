package dao;

import excepciones.DAOException;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.Producto;

import conexion.ConexionAbarrotesBD;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import objetosServicio.Fecha;
import plantillas.ConsultasAbarrotes;
import plantillas.EliminacionesAbarrotes;
import plantillas.InsercionesAbarrotes;
import plantillas.ModificacionesAbarrotes;

/**
 * Permite realizar operaciones para manejar productos almacenados en la base de
 * datos Abarrotes.
 *
 * @author Saul Neri
 */
public class Productos {

    private ConexionAbarrotesBD conexionBD;
    // donde se alojaran los productos que se vayan guardando con la PAGINACION
    private List<Producto> productos;
    // variables necesarias para paginacion de consulta de Productos
    private int desplazamiento = 0;
    private int limiteListaProductos = 20;

    /**
     * Crea una instancia del manejador del inventario de productos.
     */
    public Productos() {
        this.conexionBD = new ConexionAbarrotesBD();
        productos = new ArrayList<>();
    }

    /**
     * Crea una instancia del manejador del inventario de productos y accede a
     * la base de datos a partir de la conexion dada.
     *
     * @param conexion Conexion a la base de datos de abarrotes
     */
    public Productos(ConexionAbarrotesBD conexion) {
        this.conexionBD = conexion;
        productos = new ArrayList<>();
    }

    /**
     * Obtiene el producto especificado de la base de datos
     *
     * @param producto producto a buscar
     * @return Devuelve el producto si se encuentra en la base de datos o null
     * si no lo encuentra
     * @throws excepciones.DAOException Si ocurre un error de busqueda
     */
    public Producto obten(Producto producto) throws DAOException {
        Producto productoEncontrado = null;

        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_POR_CLAVE,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setString(1, producto.getClave());

            ResultSet rs = stmt.executeQuery();
            
            //System.out.println();
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

            productoEncontrado = new Producto(
                    claveProducto,
                    nombre,
                    tipo.toCharArray()[0],
                    unidad
            );

            rs.close();
            stmt.close();

            return productoEncontrado;

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
            //throw new DAOException("Ocurrio un error de busqueda");    
            //System.out.println("#" + ex);
        }
        //return null; // no encontrado
    }

    /**
     * Agrega un producto a la base de datos de Abarrotes.
     *
     * @param producto producto a agregar
     * @throws DAOException Si el producto ya se encuentra registrado
     */
    public void agrega(Producto producto) throws DAOException {
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(InsercionesAbarrotes.AGREGAR_PRODUCTO);

            stmt.setString(1, producto.getClave());
            stmt.setString(2, producto.getNombre());
            stmt.setString(3, String.valueOf(producto.getTipo()));
            stmt.setString(4, producto.getUnidad());

            int numAnadidos = stmt.executeUpdate();

            //System.out.println("# anadidos: "+numAnadidos);
            stmt.close();

        } catch (SQLException ex) {
            throw new DAOException(ex.getMessage());
        }
    }

    /**
     * Metodo que reemplaza un producto de la lista (si existe), por el producto
     * dado en el parametro, deben tener la misma clave.
     *
     * @param producto producto a actualizar
     * @throws DAOException Si no se pudo actualizar el producto
     */
    public void actualiza(Producto producto) throws DAOException {
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(ModificacionesAbarrotes.ACTUALIZAR_PRODUCTO);

            stmt.setString(1, producto.getNombre());
            stmt.setString(2, String.valueOf(producto.getTipo()));
            stmt.setString(3, producto.getUnidad());
            // WHERE clave_producto = ...
            stmt.setString(4, producto.getClave());

            int numModificados = stmt.executeUpdate();

            if (numModificados <= 0) {
                throw new DAOException("No se pudo actualizar el producto ya que no se encuentra registrado");
            }

            //System.out.println(numModificados + "shesh");
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println("#" + ex.getClass());
            throw new DAOException("No se pudo actualizar el producto debido a un error, intentelo mas tarde...");
        }
    }

    /**
     * Metodo que elimina un producto de la lista (si existe), por el producto
     * dado en el parametro, deben tener la misma clave.
     *
     * @param producto producto a eliminar
     * @throws DAOException Si no se pudo eliminar el producto
     */
    public void elimina(Producto producto) throws DAOException {
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(EliminacionesAbarrotes.ELIMINAR_PRODUCTO);

            // WHERE clave_producto = ...
            stmt.setString(1, producto.getClave());

            int numEliminados = stmt.executeUpdate();

            if (numEliminados <= 0) {
                throw new DAOException("No se pudo eliminar el producto debido a que no se encuentra registrado");
            }

            //System.out.println(numModificados + "shesh");
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println("#" + ex.getClass());
            throw new DAOException("No se pudo eliminar el producto debido a un error, intentelo mas tarde...");
        }
    }

    /**
     * Devuelve el numero total de productos registrados
     * @return Numero total de productos
     * @throws DAOException Si ocurre un error en la cuenta
     */
    public int numeroTotalProductos() throws DAOException {
        int resultados = 0;
        
        try {
            //Statement stmt = this.conexionBD.getConexionMySQL().prepareStatement("SELECT count(*) FROM Productos");
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement("SELECT count(*) AS total FROM Productos");
            
            ResultSet rs = stmt.executeQuery();

            resultados = rs.getInt("count(*)");
            
            if (resultados <= 0) {
                return 0;
            }
            
            rs.close();
            stmt.close();

            return resultados;

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException(ex.getMessage());
        }
    }
    
    /**
     * Carga un determinado numero de productos de la base de datos para ser
     * ingresados a la lista de acceso rapido.
     * @throws DAOException Si ocurre un error en la busqueda
     */
    public void consultarProductos() throws DAOException {
        Producto productoEncontrado = null;

        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.PRODUCTOS_TODOS,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, this.limiteListaProductos);
            stmt.setInt(2, this.desplazamiento);

            ResultSet rs = stmt.executeQuery();

            rs.first();
            
            // obtiene el primer dato
            String claveProducto = rs.getString("clave_producto");
            String nombre = rs.getString("nombre");
            String tipo = rs.getString("tipo");
            String unidad = rs.getString("unidad");

            productoEncontrado = new Producto(
                    claveProducto,
                    nombre,
                    tipo.toCharArray()[0],
                    unidad
            );

            productos.add(productoEncontrado);
            
            // por cada producto en la busqueda...
            while (rs.next()) {
                // obtiene los datos del primer producto encontrado encontrado...
                claveProducto = rs.getString("clave_producto");
                nombre = rs.getString("nombre");
                tipo = rs.getString("tipo");
                unidad = rs.getString("unidad");

                productoEncontrado = new Producto(
                        claveProducto,
                        nombre,
                        tipo.toUpperCase().toCharArray()[0],
                        unidad
                );
                
                productos.add(productoEncontrado);
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
     * Muestra la lista de todos los productos del abarrotes.
     *
     * @return regresa los productos
     */
    public List<Producto> lista() {
        return productos;
    }

    /**
     * Muestra una lista de productos de un mismo tipo.
     *
     * @param tipo tipo de producto
     * @return regresa lista de productos de un mismo tipo
     */
    public List<Producto> lista(char tipo) {
        List<Producto> productosTipo = new ArrayList();
        for (Producto producto : productos) {
            if (producto.getTipo() == tipo) {
                productosTipo.add(producto);
            }
        }
        return productosTipo;
    }

}
