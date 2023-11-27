package dao;

import conexion.ConexionAbarrotesBD;
import excepciones.DAOException;
import excepciones.PersistenciaException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
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
 * empacados en el sistema de abarrotes.
 * @author Equipo
 */
public class MovimientosEmpacados {

    private Productos productos;
    private Usuarios usuarios;
    private ConexionAbarrotesBD conexionBD;
    private List<MovimientoEmpacado> movimientosEmpacados;

    // variables necesarias para paginacion de consulta de Productos
    private int desplazamiento = 0;
    private int limiteListaMovimientos = 30;

    /**
     * Constructor que crea una instancia para almacenar los movimientos de
     * producto a granel de la abarrotera.
     */
    public MovimientosEmpacados(ConexionAbarrotesBD conexion) {
        this.movimientosEmpacados = new ArrayList();
        this.conexionBD = conexion;
        this.productos = new Productos(conexion);
        this.usuarios = new Usuarios(conexion);
    }

    /**
     * Metodo que regresa la lista de movimiento de producto empacado que se da
     * por el parametro, siempre y cuando su clave sea la misma, regresara null
     * si no existe.
     *
     * @param movimientoEmpacado movimiento de producto empacado
     * @return Movimiento a granel
     * @throws excepciones.DAOException
     */
    public MovimientoEmpacado obten(MovimientoEmpacado movimientoEmpacado) throws DAOException {
        Producto encontrado = null;
        MovimientoEmpacado movimiento = null;
        
        Usuario usuarioEncontrado = usuarios.obten(new Usuario(movimientoEmpacado.getUsuario().getId()));

        if (usuarioEncontrado == null) {
            throw new DAOException(
                    String.format(
                            "El usuario responsable del movimiento [Clave: %d] no esta registrado",
                            movimientoEmpacado.getUsuario().getId()
                    )
            );
        }
        
        try {
            encontrado = productos.obten(new Producto(movimientoEmpacado.getProductoEmpacado().getClave()));

            if (encontrado == null) {
                return null;
            }

        } catch (DAOException ex) {
            throw new DAOException("No se pudo obtener el producto empacado debido a un error interno, intentelo mas tarde...");
        }

        //System.out.println("# ENCONTRAU -> " + encontrado);
        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.MOVIMIENTO_EMPACADO_POR_CLAVE,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            stmt.setString(1, movimientoEmpacado.getCveMovimiento());

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
            int usuarioID = rs.getInt("usuario_id");
            int cantidadMovimiento = rs.getInt("cantidad");
            boolean procesado = rs.getBoolean("procesado");
            java.sql.Date fechaRegistro = rs.getDate("fecha");
            Fecha fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);

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
            ProductoEmpacado productoEmpacado = new ProductoEmpacado(encontrado, cantidadMovimiento);

            movimiento = new MovimientoEmpacado(
                    claveMovimiento,
                    fecha,
                    procesado,
                    productoEmpacado,
                    usuarioEncontrado
            );

            rs.close();
            stmt.close();

            return movimiento;

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode() + "###" + ex.getSQLState());
            throw new DAOException(ex.getMessage());
        }
    }

    /**
     * Agrega el movimiento dado por el parametro a la lista de movimientos.
     *
     * @param movimientoEmpacado movimiento empacado
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void agrega(MovimientoEmpacado movimientoEmpacado) throws DAOException {
        PreparedStatement stmt;

        ProductoEmpacado productoBuscar = movimientoEmpacado.getProductoEmpacado();
        Producto encontrado = productos.obten(new Producto(productoBuscar.getClave()));
        
        if (encontrado == null) {
            throw new DAOException("El producto manejado por el movimiento no existe");
        }

        if (encontrado.getTipo() != 'E') {
            throw new DAOException("El producto dado no es un producto empacado");
        }
        
        Usuario usuarioEncontrado = usuarios.obten(new Usuario(movimientoEmpacado.getUsuario().getId()));
        
        if (usuarioEncontrado == null) {
            throw new DAOException("El usuario responsable de este movimiento no existe");
        }
        

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(InsercionesAbarrotes.AGREGAR_MOVIMIENTO_EMPACADO);

            stmt.setString(1, movimientoEmpacado.getCveMovimiento());
            stmt.setInt(2, movimientoEmpacado.getUsuario().getId());
            stmt.setString(3, movimientoEmpacado.getProductoEmpacado().getClave());
            stmt.setFloat(4, movimientoEmpacado.getProductoEmpacado().getCantidad());
            stmt.setBoolean(5, false);

            int numAnadidos = stmt.executeUpdate();

            //System.out.println("# anadidos: "+numAnadidos);
            stmt.close();

        } catch (SQLException ex) {
            if (ex.getErrorCode() == ErrorMySQL.DUPLICATE) {
                throw new DAOException(
                        String.format("Ya existe un movimiento con dicha clave [CLAVE: %s]", movimientoEmpacado.getCveMovimiento())
                );
            }
            throw new DAOException("No se pudo agregar el movimiento de producto empacado debido a un error interno, intentelo mas tarde...");
        }
    }

    /**
     * Remplaza el movimiento que coincida (con clave) con el producto dado en
     * el parametro, a menos que no exista.
     *
     * @param movimientoEmpacado movimiento empacado
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void actualiza(MovimientoEmpacado movimientoEmpacado) throws DAOException {
        PreparedStatement stmt;
        ProductoEmpacado productoBuscar = movimientoEmpacado.getProductoEmpacado();
        Producto encontrado = productos.obten(new Producto(productoBuscar.getClave()));
        MovimientoEmpacado movimientoModificar = this.obten(movimientoEmpacado);

        if (encontrado.getTipo() != 'E') {
            throw new DAOException("El producto dado no es un producto empacado");
        }

        if (movimientoModificar == null) {
            throw new DAOException("El movimiento especificado no existe en el registro");
        }

        if (movimientoModificar.getProcesado() == true) {
            throw new DAOException("No se puede modificar un movimiento ya procesado");
        }
        
        Usuario usuarioEncontrado = usuarios.obten(new Usuario(movimientoEmpacado.getUsuario().getId()));
        if (usuarioEncontrado == null) {
            throw new DAOException("No se encontro el usuario responsable de este movimiento");
        }

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(ModificacionesAbarrotes.ACTUALIZAR_MOVIMIENTO_EMPACADO);

            stmt.setString(1, movimientoEmpacado.getProductoEmpacado().getClave());
            stmt.setInt(2, movimientoEmpacado.getUsuario().getId());
            stmt.setFloat(3, movimientoEmpacado.getProductoEmpacado().getCantidad());
            // WHERE clave_producto = ...
            stmt.setString(4, movimientoEmpacado.getCveMovimiento());

            int numModificados = stmt.executeUpdate();

            if (numModificados <= 0) {
                throw new DAOException("No se pudo modificar el movimiento");
            }

            //System.out.println(numModificados + "shesh");
            stmt.close();

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException("No se pudo actualizar el movimiento del producto empacado debido a un error, intentelo mas tarde...");
        }
    }

    /**
     * Elimina el movimiento de producto granel de la lista (si existe) y si
     * este no esta procesado, por el movimiento de producto granel dado en el
     * parametro, deben tener la misma clave.
     *
     * @param movimientoGranel movimiento granel
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public void elimina(MovimientoEmpacado movimientoGranel) throws DAOException {
        PreparedStatement stmt;

        try {
            stmt = this.conexionBD.getConexionMySQL().prepareStatement(EliminacionesAbarrotes.ELIMINAR_MOVIMIENTO_EMPACADO);

            // WHERE clave_producto = ...
            stmt.setString(1, movimientoGranel.getCveMovimiento());

            int numEliminados = stmt.executeUpdate();

            if (numEliminados <= 0) {
                throw new DAOException("El movimiento que se quiere eliminar no se encuentra registrado");
            }

            stmt.close();

        } catch (SQLException ex) {
            //System.out.println("#" + ex.getClass());
            throw new DAOException("No se pudo eliminar el movimiento del producto empacado debido a un error, intentelo mas tarde...");
        }
    }

    /**
     * Carga un determinado numero de productos empacados de la base de datos
     * para ser ingresados a la lista de acceso rapido.
     *
     * @throws DAOException Si ocurre un error de busqueda
     */
    public void consultarMovimientosEmpacados() throws DAOException {
        MovimientoEmpacado movimiento = null;

        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.MOVIMIENTOS_EMPACADOS_TODOS,
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
            int cantidadMovimiento = rs.getInt("cantidad");
            boolean procesado = rs.getBoolean("procesado");
            java.sql.Date fechaRegistro = rs.getDate("fecha");
            Fecha fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);

            Producto encontrado = productos.obten(new Producto(claveProducto));

            if (encontrado.getTipo() != 'E') {
                throw new DAOException("Ocurrio un error en la busqueda debido a un dato erroneo");
            }

            if (encontrado == null) {
                throw new DAOException(
                        String.format(
                                "El producto empacado del movimiento [Clave: %s] no existe o no esta inventariado",
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
            ProductoEmpacado productoEmpacado = new ProductoEmpacado(encontrado, cantidadMovimiento);

            movimiento = new MovimientoEmpacado(
                    claveMovimiento,
                    fecha,
                    procesado,
                    productoEmpacado,
                    usuarioEncontrado
            );

            movimientosEmpacados.add(movimiento);
            this.desplazamiento++;

            // por cada producto en la busqueda...
            while (rs.next()) {
                // se obtienen los productos siguientes al primero...          

                // obtiene los datos del primer movimiento granel encontrado
                claveMovimiento = rs.getString("clave_movimiento");
                claveProducto = rs.getString("clave_producto");
                usuarioID = rs.getInt("usuario_id");
                cantidadMovimiento = rs.getInt("cantidad");
                procesado = rs.getBoolean("procesado");
                fechaRegistro = rs.getDate("fecha");
                fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth(), fechaRegistro.getYear()+1900);

                encontrado = productos.obten(new Producto(claveProducto));

                if (encontrado.getTipo() != 'E') {
                    throw new DAOException("Ocurrio un error en la busqueda debido a un dato erroneo");
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
                productoEmpacado = new ProductoEmpacado(encontrado, cantidadMovimiento);

                movimiento = new MovimientoEmpacado(
                        claveMovimiento,
                        fecha,
                        procesado,
                        productoEmpacado,
                        usuarioEncontrado
                );

                movimientosEmpacados.add(movimiento);
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
    public List<MovimientoEmpacado> lista() {
        return movimientosEmpacados;
    }

    /**
     * Devuelve la lista de movimientos de productos empacados en un periodo
     * dado.
     *
     * @param periodo periodo
     * @return regresa una lista dentro de un periodo
     * @throws excepciones.DAOException Si ocurre un error interno
     */
    public List<MovimientoEmpacado> lista(Periodo periodo) throws DAOException {
        MovimientoEmpacado movimiento = null;

        // remueve todos los movimientos en la lista para agregar otros nuevos
        // ordenados por periodo ...
        this.movimientosEmpacados.clear();

        try {

            PreparedStatement stmt = this.conexionBD.getConexionMySQL().prepareStatement(
                    ConsultasAbarrotes.MOVIMIENTOS_EMPACADOS_POR_PERIODO,
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

            java.sql.Date fechaDesde = new java.sql.Date(
                    periodo.getDesde().getAnio(),
                    periodo.getDesde().getMes(),
                    periodo.getDesde().getDia()
            );

            java.sql.Date fechaHasta = new java.sql.Date(
                    periodo.getHasta().getAnio(),
                    periodo.getHasta().getMes(),
                    periodo.getHasta().getDia()
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
            String claveProducto = rs.getString("clave_producto");
            int usuarioID = rs.getInt("usuario_id");
            int cantidadMovimiento = rs.getInt("cantidad");
            boolean procesado = rs.getBoolean("procesado");
            java.sql.Date fechaRegistro = rs.getDate("fecha");
            Fecha fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);

            Producto encontrado = productos.obten(new Producto(claveProducto));

            if (encontrado.getTipo() != 'E') {
                throw new DAOException("Ocurrio un error en la busqueda debido a un dato erroneo");
            }

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
            ProductoEmpacado productoEmpacado = new ProductoEmpacado(encontrado, cantidadMovimiento);

            movimiento = new MovimientoEmpacado(
                    claveMovimiento,
                    fecha,
                    procesado,
                    productoEmpacado,
                    usuarioEncontrado
            );

            movimientosEmpacados.add(movimiento);

            this.desplazamiento++;

            // por cada producto en la busqueda...
            while (rs.next()) {
                // se obtienen los movimientos siguientes al primero...
                claveMovimiento = rs.getString("clave_movimiento");
                claveProducto = rs.getString("clave_producto");
                usuarioID = rs.getInt("usuario_id");
                cantidadMovimiento = rs.getInt("cantidad");
                procesado = rs.getBoolean("procesado");
                fechaRegistro = rs.getDate("fecha");
                fecha = new Fecha(fechaRegistro.getDate(), fechaRegistro.getMonth()+1, fechaRegistro.getYear()+1900);

                encontrado = productos.obten(new Producto(claveProducto));

                if (encontrado.getTipo() != 'E') {
                    throw new DAOException("Ocurrio un error en la busqueda debido a un dato erroneo");
                }

                if (encontrado == null) {
                    throw new DAOException(
                            String.format(
                                    "El producto empacado del movimiento [Clave: %s] no existe o no esta inventariado",
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
                productoEmpacado = new ProductoEmpacado(encontrado, cantidadMovimiento);

                movimiento = new MovimientoEmpacado(
                        claveMovimiento,
                        fecha,
                        procesado,
                        productoEmpacado,
                        usuarioEncontrado
                );

                movimientosEmpacados.add(movimiento);
                this.desplazamiento++;
            }

            rs.close();
            stmt.close();

            return this.movimientosEmpacados;

        } catch (SQLException ex) {
            //System.out.println(ex.getErrorCode());
            throw new DAOException(ex.getMessage());
        }
    }

}
