package dao;

import conexion.ConexionAbarrotesBD;
import excepciones.DAOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.Producto;
import objetosNegocio.ProductoGranel;
import objetosNegocio.Usuario;
import objetosServicio.Fecha;
import objetosServicio.Periodo;
import plantillas.ConsultasAbarrotes;
import plantillas.EliminacionesAbarrotes;
import plantillas.ErrorMySQL;
import plantillas.InsercionesAbarrotes;
import plantillas.ModificacionesAbarrotes;

/**
 * Se encarga de obtener, agregar, actualizar y eliminar movimientos de productos
 * a granel en el sistema de abarrotes.
 * @author Equipo
 */
public class MovimientosGranel {
    private List<MovimientoGranel> movimientosGranel;
    private Productos productos;
    private Usuarios usuarios;
    private ConexionAbarrotesBD conexionBD;
    
    // variables necesarias para paginacion de consulta de Productos
    private int desplazamiento = 0;
    private int limiteListaMovimientos = 30;    
    
    /** 
     * Constructor que crea una instancia para almacenar los movimientos de producto a granel de la abarrotera.
     */
    public MovimientosGranel(ConexionAbarrotesBD conexion){
        this.movimientosGranel = new ArrayList();
        this.conexionBD = conexion;
        this.productos = new Productos(conexion);
        this.usuarios = new Usuarios(conexion);
    }
    
    /** 
     * Metodo que regresa la lista de movimiento de producto granel que se da por el parametro, 
     * siempre y cuando su clave sea la misma, regresara null si no existe.
     * @param movimientoGranel movimiento granel
     * @return Movimiento a granel
     */
    public MovimientoGranel obten(MovimientoGranel movimientoGranel) throws DAOException{
        Producto encontrado = null;
        MovimientoGranel movimiento = null;
        
        Usuario usuarioEncontrado = usuarios.obten(movimientoGranel.getUsuario());
        
        if (usuarioEncontrado == null) {
            throw new DAOException("No se encontro al usuario en la base de datos");
        }
        
        ProductoGranel productoGranel = movimientoGranel.getProductoGranel();
        
        try {
            encontrado = productos.obten(new Producto(productoGranel.getClave()));
            
            if (encontrado == null) {
                return null;
            }
            
        } catch (DAOException ex) {
            throw new DAOException("No se pudo obtener el producto a granel debido a un error interno, intentelo mas tarde...");
        }
        

        try {
            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.MOVIMIENTO_GRANEL_POR_CLAVE,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setString(1, movimientoGranel.getCveMovimiento());

            ResultSet rs = stmt.executeQuery();
            
            // si no existe el producto...
            if (!rs.next()) {
                rs.close();
                stmt.close();
                return null;
            }

            // indica que se requiere el primero encontrado
            rs.first();

            // obtiene los datos del primer movimiento granel encontrado
            String claveMovimiento = rs.getString("clave_movimiento");
            float cantidadMovimiento = rs.getFloat("cantidad");
            boolean procesado = rs.getBoolean("procesado");
            java.sql.Date fechaRegistro = rs.getDate("fecha");
            Fecha fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);
            
            // NOTE: Se le asigna al producto la cantidad que se le anadio al inventario
            // al momento de agregar el movimiento, si queremos pasar directamente el
            // producto encontrado a la creacion del movimiento, este sera incorrecto
            // dando lugar a movimientos granel incorrectos.
            productoGranel = new ProductoGranel(encontrado, cantidadMovimiento);
            
            movimiento = new MovimientoGranel(
                    claveMovimiento,
                    fecha,
                    procesado,
                    productoGranel,
                    usuarioEncontrado
            );

            rs.close();
            stmt.close();

            return movimiento;

        } catch (SQLException ex) {
            System.out.println(ex.getErrorCode() + "###" + ex.getSQLState());
            throw new DAOException(ex.getMessage());
        }
    }
    
    /** 
     * Agrega el movimiento dado por el parametro a la lista de movimientos.
     * @param movimientoGranel movimiento granel
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void agrega(MovimientoGranel movimientoGranel) throws DAOException{
        PreparedStatement stmt;
        
        ProductoGranel productoBuscar = movimientoGranel.getProductoGranel();
        
        Producto encontrado = productos.obten(productoBuscar);
       
        if (encontrado == null) {
            throw new DAOException("El usuario responsable no se encontro en la base de datos");
        }
        
        if (encontrado.getTipo() != 'G') {
            throw new DAOException("El producto dado no es un producto granel");
        }
        
        Usuario usuarioEncontrado = usuarios.obten(new Usuario(movimientoGranel.getUsuario().getId()));
        if (usuarioEncontrado == null) {
            throw new DAOException("No se encontro al usuario responsable del movimiento en la base de datos");
        }
        
        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(InsercionesAbarrotes.AGREGAR_MOVIMIENTO_GRANEL);

            stmt.setString(1, movimientoGranel.getCveMovimiento());
            stmt.setInt(2, usuarioEncontrado.getId());
            stmt.setString(3, movimientoGranel.getProductoGranel().getClave());
            stmt.setFloat(4, movimientoGranel.getProductoGranel().getCantidad());
            stmt.setBoolean(5, false);

            int numAnadidos = stmt.executeUpdate();
            
            //System.out.println("# anadidos: "+numAnadidos);
            
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex);
            if (ex.getErrorCode() == ErrorMySQL.DUPLICATE) {
                throw new DAOException("El movimiento que se quiere agregar ya esta registrado con esa clave");
            }
            throw new DAOException("No se pudo agregar el movimiento de producto a granel debido a un error interno, intentelo mas tarde...");
        }
    }
    
    /** 
     * Remplaza el movimiento que coincida (con clave) con el producto dado en el parametro,
     * a menos que no exista.
     * @param movimientoGranel movimiento granle
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void actualiza(MovimientoGranel movimientoGranel) throws DAOException{
        PreparedStatement stmt;
        ProductoGranel productoGranel = movimientoGranel.getProductoGranel();
        Producto encontrado = productos.obten(new Producto(productoGranel.getClave()));
        MovimientoGranel movimientoModificar = this.obten(movimientoGranel);
        
        if (encontrado.getTipo() != 'G') {
            throw new DAOException("El producto dado no es un producto granel");
        } 
        
        if (movimientoModificar == null) {
            throw new DAOException("El movimiento especificado no existe en el registro");
        }
        
        if (movimientoModificar.getProcesado() == true) {
            throw new DAOException("No se puede modificar un movimiento ya procesado");
        }
        
        Usuario usuarioEncontrado = usuarios.obten(new Usuario(movimientoGranel.getUsuario().getId()));
        if (usuarioEncontrado == null) {
            throw new DAOException("No se encontro al usuario responsable del movimiento en la base de datos");
        }
        
        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(ModificacionesAbarrotes.ACTUALIZAR_MOVIMIENTO_GRANEL);

            stmt.setString(1, movimientoGranel.getProductoGranel().getClave());
            stmt.setInt(2, usuarioEncontrado.getId());
            stmt.setFloat(3, movimientoGranel.getProductoGranel().getCantidad());
            // WHERE clave_producto = ...
            stmt.setString(4, movimientoGranel.getCveMovimiento());
            
            int numModificados = stmt.executeUpdate();

            if (numModificados <= 0) {
                throw new DAOException("No se pudo modificar el movimiento");
            }

            //System.out.println(numModificados + "shesh");
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException("No se pudo actualizar el movimiento del producto a granel debido a un error, intentelo mas tarde...");
        }
    }
    
    /** 
     * Elimina el movimiento de producto granel de la lista (si existe) y si este
     * no esta procesado, por el movimiento de producto granel dado en el parametro,
     * deben tener la misma clave.
     * @param movimientoGranel movimiento granel
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void elimina(MovimientoGranel movimientoGranel) throws DAOException{
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(EliminacionesAbarrotes.ELIMINAR_MOVIMIENTO_GRANEL);

            // WHERE clave_producto = ...
            stmt.setString(1, movimientoGranel.getCveMovimiento());

            int numEliminados = stmt.executeUpdate();

            if (numEliminados <= 0) {
                throw new DAOException("El movimiento que se quiere eliminar no se encuentra registrado");
            }

            stmt.close();

        } catch (SQLException ex) {
            //System.out.println("#" + ex.getClass());
            throw new DAOException("No se pudo eliminar el movimiento del producto a granel debido a un error, intentelo mas tarde...");
        }
    }
    
    /**
     * Carga un determinado numero de productos a granel de la base de datos para ser
     * ingresados a la lista de acceso rapido.
     * @throws DAOException Si ocurre un error de busqueda
     */
    public void consultarMovimientosGranel() throws DAOException {
        MovimientoGranel movimiento = null;

        Usuario usuarioEncontrado = null;
        
        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.MOVIMIENTOS_GRANEL_TODOS,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setInt(1, this.limiteListaMovimientos);
            stmt.setInt(2, this.desplazamiento);

            ResultSet rs = stmt.executeQuery();

            // se posiciona en el primer elemento encontrado...
            rs.first();
            
            // obtiene los datos del primer movimiento granel encontrado
            String claveMovimiento = rs.getString("clave_movimiento");
            String claveProducto = rs.getString("clave_producto");
            int usuarioID = rs.getInt("usuario_id");
            float cantidadMovimiento = rs.getFloat("cantidad");
            boolean procesado = rs.getBoolean("procesado");
            java.sql.Date fechaRegistro = rs.getDate("fecha");
            Fecha fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);
            
            Producto encontrado = productos.obten(new Producto(claveProducto));
                     
            if (encontrado == null) {
                throw new DAOException(
                        String.format(
                                "El producto granel del movimiento [Clave: %s] no existe o no esta inventariado",
                                claveMovimiento
                        )
                );
            }
            
            usuarioEncontrado = usuarios.obten(new Usuario(usuarioID));
            
            if (usuarioEncontrado == null) {
                throw new DAOException(
                        String.format(
                                "El usuario responsable del movimiento [Clave: %d] no esta registrado",
                                usuarioID
                        )
                );
            }
            
            // NOTE: Se le asigna al producto la cantidad que se le anadio al inventario
            // al momento de agregar el movimiento, si queremos pasar directamente el
            // producto encontrado a la creacion del movimiento, este sera incorrecto
            // dando lugar a movimientos granel incorrectos.
            ProductoGranel productoGranel = new ProductoGranel(encontrado, cantidadMovimiento);
            
            movimiento = new MovimientoGranel(
                    claveMovimiento,
                    fecha,
                    procesado,
                    productoGranel,
                    usuarioEncontrado
            );

            movimientosGranel.add(movimiento);
            
            this.desplazamiento++;
            
            // por cada producto en la busqueda...
            while (rs.next()) {
                // se obtienen los productos siguientes al primero...          

                // obtiene los datos del primer movimiento granel encontrado
                claveMovimiento = rs.getString("clave_movimiento");
                usuarioID = rs.getInt("usuario_id");
                claveProducto = rs.getString("clave_producto");
                cantidadMovimiento = rs.getFloat("cantidad");
                procesado = rs.getBoolean("procesado");
                fechaRegistro = rs.getDate("fecha");
                fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);

                encontrado = productos.obten(new Producto(claveProducto));
                
                if (encontrado.getTipo() != 'G') {
                    throw new DAOException("Ha ocurrido un error en la busqueda debido a un dato erroneo, intente mas tarde...");
                }
                
                if (encontrado == null) {
                    throw new DAOException(
                            String.format(
                                    "El producto granel del movimiento [Clave: %s] no existe o no esta inventariado",
                                    claveMovimiento
                            )
                    );
                }
                
                usuarioEncontrado = usuarios.obten(new Usuario(usuarioID));
            
                if (usuarioEncontrado == null) {
                    throw new DAOException(
                            String.format(
                                    "El usuario responsable del movimiento [Clave: %d] no esta registrado",
                                    usuarioID
                            )
                    );
                }
                
                // NOTE: Se le asigna al producto la cantidad que se le anadio al inventario
                // al momento de agregar el movimiento, si queremos pasar directamente el
                // producto encontrado a la creacion del movimiento, este sera incorrecto
                // dando lugar a movimientos granel incorrectos.
                productoGranel = new ProductoGranel(encontrado, cantidadMovimiento);

                movimiento = new MovimientoGranel(
                        claveMovimiento,
                        fecha,
                        procesado,
                        productoGranel,
                        usuarioEncontrado
                );

                movimientosGranel.add(movimiento);
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
     * Metodo el cual muestra la lista de movimientos de productos granel.
     * 
     * @return lista de movimiento de producto granel
     */ 
    public List<MovimientoGranel> lista(){
        return movimientosGranel;
    }
    
    /** 
     * Devuelve la lista de movimientos de productos granel en un periodo dado.
     * @param periodo periodo
     * @return regresa una lista dentro de un periodo
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public List<MovimientoGranel> lista(Periodo periodo) throws DAOException{
        MovimientoGranel movimiento = null;

        // remueve todos los movimientos en la lista para agregar otros nuevos
        // ordenados por periodo ...
        this.movimientosGranel.clear();
        
        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.MOVIMIENTOS_GRANEL_TODOS,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            java.sql.Date fechaDesde = new java.sql.Date(
                    periodo.getDesde().getAnio(),
                    periodo.getDesde().getMes(),
                    periodo.getDesde().getDia()
            );
            
            java.sql.Date fechaHasta = new java.sql.Date(
                    periodo.getDesde().getAnio(),
                    periodo.getDesde().getMes(),
                    periodo.getDesde().getDia()
            );
            
            stmt.setDate(1, fechaDesde);
            stmt.setDate(2, fechaHasta);
            stmt.setInt(3, this.limiteListaMovimientos);
            stmt.setInt(4, this.desplazamiento);

            ResultSet rs = stmt.executeQuery();

            // se posiciona en el primer elemento encontrado...
            rs.first();
            
            // obtiene los datos del primer movimiento granel encontrado
            String claveMovimiento = rs.getString("clave_movimiento");
            int usuarioID = rs.getInt("usuario_id");
            String claveProducto = rs.getString("clave_producto");
            float cantidadMovimiento = rs.getFloat("cantidad");
            boolean procesado = rs.getBoolean("procesado");
            java.sql.Date fechaRegistro = rs.getDate("fecha");
            Fecha fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);
            
            Producto encontrado = productos.obten(new Producto(claveProducto));
            
            if (encontrado == null) {
                throw new DAOException(
                        String.format(
                                "El producto granel del movimiento [Clave: %s] no existe o no esta inventariado",
                                claveMovimiento
                        )
                );
            }
            
            Usuario usuarioEncontrado = usuarios.obten(new Usuario(usuarioID));
            
            if (usuarioEncontrado == null) {
                throw new DAOException(
                        String.format(
                                "El usuario responsable del movimiento [Clave: %d] no esta registrado",
                                usuarioID
                        )
                );
            }
            
            
            // NOTE: Se le asigna al producto la cantidad que se le anadio al inventario
            // al momento de agregar el movimiento, si queremos pasar directamente el
            // producto encontrado a la creacion del movimiento, este sera incorrecto
            // dando lugar a movimientos granel incorrectos.
            ProductoGranel productoGranel = new ProductoGranel(encontrado, cantidadMovimiento);
            
            movimiento = new MovimientoGranel(
                    claveMovimiento,
                    fecha,
                    procesado,
                    productoGranel,
                    usuarioEncontrado
            );

            movimientosGranel.add(movimiento);
            
            this.desplazamiento++;
            
            // por cada producto en la busqueda...
            while (rs.next()) {
                // se obtienen los productos siguientes al primero...          

                // obtiene los datos del primer movimiento granel encontrado
                claveMovimiento = rs.getString("clave_movimiento");
                claveProducto = rs.getString("clave_producto");
                cantidadMovimiento = rs.getFloat("cantidad");
                procesado = rs.getBoolean("procesado");
                fechaRegistro = rs.getDate("fecha");
                fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);
            
                productoGranel = (ProductoGranel) productos.obten(new Producto(claveProducto));

                if (productoGranel == null) {
                    throw new DAOException(
                            String.format(
                                    "El producto granel del movimiento [Clave: %s] no existe o no esta inventariado",
                                    claveMovimiento
                            )
                    );
                }
                
                usuarioEncontrado = usuarios.obten(new Usuario(usuarioID));
            
                if (usuarioEncontrado == null) {
                    throw new DAOException(
                            String.format(
                                    "El usuario responsable del movimiento [Clave: %d] no esta registrado",
                                    usuarioID
                            )
                    );
                }

                // NOTE: Se le asigna al producto la cantidad que se le anadio al inventario
                // al momento de agregar el movimiento, si queremos pasar directamente el
                // producto encontrado a la creacion del movimiento, este sera incorrecto
                // dando lugar a movimientos granel incorrectos.
                productoGranel.setCantidad(cantidadMovimiento);

                movimiento = new MovimientoGranel(
                        claveMovimiento,
                        fecha,
                        procesado,
                        productoGranel,
                        usuarioEncontrado
                );

                movimientosGranel.add(movimiento);
                this.desplazamiento++;
            }

            rs.close();
            stmt.close();
            
            return this.movimientosGranel;
            
        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException(ex.getMessage());
        }
    }
}
