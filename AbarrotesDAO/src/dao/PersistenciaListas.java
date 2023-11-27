package dao;

import conexion.ConexionAbarrotesBD;
import excepciones.DAOException;
import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
import objetosNegocio.ProductoGranel;
import objetosNegocio.Usuario;
import objetosServicio.Periodo;

/**
 *
 * @author Equipo
 */
public class PersistenciaListas implements IPersistencia {

    private Productos catalogoProductos;
    private MovimientosGranel registroVentasGranel;
    private MovimientosGranel registroComprasGranel;
    private ProductosGranel inventarioProductosGranel;

    private MovimientosEmpacados registroVentasEmpacados;
    private MovimientosEmpacados registroComprasEmpacados;
    private ProductosEmpacados inventarioProductosEmpacados;

    private Usuarios usuarios;
    
    /**
     * Crea una persistencia para el acceso y registro de ventas de productos
     * a traves de la conexion a la base de datos dada.
     * @param conexion Conexion a la base de datos de abarrotes
     */
    public PersistenciaListas(ConexionAbarrotesBD conexion) {
        this.catalogoProductos = new Productos(conexion);
        this.registroVentasGranel = new MovimientosGranel(conexion);
        this.registroComprasGranel = new MovimientosGranel(conexion);
        this.inventarioProductosGranel = new ProductosGranel(conexion);
        this.registroVentasEmpacados = new MovimientosEmpacados(conexion);
        this.registroComprasEmpacados = new MovimientosEmpacados(conexion);
        this.inventarioProductosEmpacados = new ProductosEmpacados(conexion);
        this.usuarios = new Usuarios(conexion);
    }
    
    /**
     * Obtiene el usuario almacenado en la base de datos a traves del dado
     * como parametro
     * @param usuario Usuario a buscar
     * @return Objeto Usuario si lo encuentra, caso contrario devuelve null
     * @throws PersistenciaException Si ocurrio un error interno
     */
    @Override
    public Usuario obten(Usuario usuario) throws PersistenciaException {
        Usuario encontrado = null;
        try {
            encontrado = usuarios.obten(usuario);
            
            if (encontrado == null) {
                return null;
            }
            
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
        
        return encontrado;
    }

    /**
     * Agrega un usuario a la base de datos de abarrotes
     * @param usuario Usuario a registrar
     * @throws PersistenciaException Si ocurre un error interno al registrar
     * al usuario
     */
    @Override
    public void agregar(Usuario usuario) throws PersistenciaException {
        try {
            usuarios.agregar(usuario);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    /**
     * Actualiza los datos de un usuario ya existente en la base de datos de abarrotes
     * @param usuario Usuario a actualizar
     * @throws PersistenciaException Si el usuario ya esta registrado u ocurre un error
     * interno
     */
    @Override
    public void actualizar(Usuario usuario) throws PersistenciaException {
        Usuario encontrado = null;
        
        try {
            encontrado = usuarios.obten(usuario);
            
            if (encontrado == null) {
                throw new PersistenciaException("El usuario que se quiere actualizar sus datos no esta registrado");
            }
            
            usuarios.actualiza(usuario);
            
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    /**
     * Elimina un usuario de la base de datos de abarrotes
     * @param usuario Usuario a eliminar
     * @throws PersistenciaException Si el usuario no existe u ocurre un error 
     * interno
     */
    public void eliminar(Usuario usuario) throws PersistenciaException {
        Usuario encontrado = null;
        
        try {
            encontrado = usuarios.obten(usuario);
            
            if (encontrado == null) {
                throw new PersistenciaException("El usuario que se quiere eliminar no esta registrado");
            }
            
            usuarios.elimina(encontrado);
            
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    /**
     * Metodo el cual permite obtener el producto del parametro dado, siempre y
     * cuando este si exista dentro de la lista a solicitar.
     *
     * @param producto producto de abarrote
     * @return producto solicitado
     * @throws PersistenciaException arroja error si el producto no esta en la
     * lista
     */
    @Override
    public Producto obten(Producto producto) throws PersistenciaException {
        try {
            Producto encontrado = catalogoProductos.obten(producto);
            
            //System.out.println(encontrado);
            
            if (encontrado == null) {
                //throw new PersistenciaException("El producto no se encuentra registrado");
                return null;
            }
            
            return encontrado;
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Obiene la venta del parametro dado, siempre y cuando este si exista 
     * dentro de la lista a solicitar.
     * @param venta movimiento de venta, Empacado
     * @return regresa el movimiento de venta Empacado solicitado
     * @throws PersistenciaException arroja error si la venta no esta en la
     * lista
     */
    @Override
    public MovimientoEmpacado obtenVenta(MovimientoEmpacado venta) throws PersistenciaException {
        
        try {
            MovimientoEmpacado encontrado = registroVentasEmpacados.obten(venta);
            
            if (encontrado == null) {
                throw new PersistenciaException("No se encontro la venta");
            }
            
            return encontrado;
                    
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
        
    }

    /**
     * Metodo el cual permite obtener la compra del parametro dado, siempre y
     * cuando este si exista dentro de la lista a solicitar.
     * @param compra movimiento de compra, Empacado
     * @return regresa el movimiento de compra Empacado solicitado
     * @throws PersistenciaException arroja error si la compra no esta en la
     * lista
     */
    @Override
    public MovimientoEmpacado obtenCompra(MovimientoEmpacado compra) throws PersistenciaException {
        try {
            MovimientoEmpacado encontrado = registroComprasEmpacados.obten(compra);
            
            if (encontrado == null) {
                return null;
            }
            
            return encontrado;
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo el cual permite obtener la venta del parametro dado, siempre y
     * cuando este si exista dentro de la lista a solicitar.
     *
     * @param venta movimiento de venta, Granel
     * @return regresa el movimiento de venta Granel solicitado
     * @throws PersistenciaException arroja error si la venta no esta en la
     * lista
     */
    @Override
    public MovimientoGranel obtenVenta(MovimientoGranel venta) throws PersistenciaException {
        try {
            MovimientoGranel movimiento = registroVentasGranel.obten(venta);
            
            if (movimiento == null) {
                throw new PersistenciaException("No se encontro la venta");
            }
            
            return movimiento;
            
        } catch (DAOException ex) {
            throw new PersistenciaException("No se pudo obtener la venta de producto a granel debido a un error");
        }
    }

    /**
     * Metodo el cual permite obtener la compra del parametro dado, siempre y
     * cuando este si exista dentro de la lista a solicitar.
     *
     * @param compra movimiento de compra, Granel
     * @return regresa el movimiento de compra Granel solicitado
     * @throws PersistenciaException arroja error si la compra no esta en la
     * lista
     */
    @Override
    public MovimientoGranel obtenCompra(MovimientoGranel compra) throws PersistenciaException {
        try {
            MovimientoGranel movimiento = registroVentasGranel.obten(compra);
            
            if (movimiento == null) {
                throw new PersistenciaException("No se encontro la compra");
            }
            
            return movimiento;
            
        } catch (DAOException ex) {
            throw new PersistenciaException("No se pudo obtener la compra de producto a granel debido a un error");
        }
    }

    /**
     * Metodo que agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     *
     * @param producto producto abarrotes
     * @throws PersistenciaException arroja error si ya existe en la lista
     */
    @Override
    public void agregar(Producto producto) throws PersistenciaException {
        try {
            
            Producto encontrado = catalogoProductos.obten(producto);
            
            if (encontrado != null) {
                throw new PersistenciaException("El producto ya se encuentra registrado");
            }
            
            catalogoProductos.agrega(producto);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que agrega una venta dada en el parametro, a una lista, si esta se
     * enuentra en ella arrojara una excepcion.
     *
     * @param venta Movimiento de venta, empacado
     * @throws PersistenciaException arroja error si ya existe en la lista
     */
    @Override
    public void agregarVenta(MovimientoEmpacado venta) throws PersistenciaException {
        try {
            registroVentasEmpacados.agrega(venta);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que agrega una compra dada en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     *
     * @param compra Movimiento de compra, empacado
     * @throws PersistenciaException arroja error si ya existe en la lista
     */
    @Override
    public void agregarCompra(MovimientoEmpacado compra) throws PersistenciaException {
        try {
            registroComprasEmpacados.agrega(compra);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException arroja error si ya existe en la lista o 
     * si ocurre un error interno
     */
    @Override
    public void agregarVenta(MovimientoGranel venta) throws PersistenciaException {
        try {
            registroVentasGranel.agrega(venta);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     * @param compra Movimiento de compra, granel
     * @throws PersistenciaException arroja error si ya existe en la lista o 
     * si ocurre un error interno
     */
    @Override
    public void agregarCompra(MovimientoGranel compra) throws PersistenciaException {
        try {
            registroComprasGranel.agrega(compra);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que actualiza un producto de una lista, realizando cambios si es
     * que se realizaron, este arrojara una excepcion si el producto no se puede
     * actualizar.
     *
     * @param producto producto abarrotes
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    @Override
    public void actualizar(Producto producto) throws PersistenciaException {
        try {
            catalogoProductos.actualiza(producto);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que actualiza una venta de una lista, realizando cambios si es que
     * se realizaron, este arrojara una excepcion si el producto no se puede
     * actualizar.
     *
     * @param venta Movimiento de venta, empacado
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    @Override
    public void actualizarVenta(MovimientoEmpacado venta) throws PersistenciaException {
        try {
            registroVentasEmpacados.actualiza(venta);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que actualiza una compra de una lista, realizando cambios si es
     * que se realizaron, este arrojara una excepcion si el producto no se puede
     * actualizar.
     *
     * @param compra Movimiento de compra, Empacado
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    @Override
    public void actualizarCompra(MovimientoEmpacado compra) throws PersistenciaException {
        try {
            registroComprasEmpacados.actualiza(compra);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que actualiza una venta de una lista, realizando cambios si es que
     * se realizaron, este arrojara una excepcion si el producto no se puede
     * actualizar.
     *
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    @Override
    public void actualizarVenta(MovimientoGranel venta) throws PersistenciaException {
        try {
            registroVentasGranel.actualiza(venta);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que actualiza una compra de una lista, realizando cambios si es
     * que se realizaron, este arrojara una excepcion si el producto no se puede
     * actualizar.
     *
     * @param compra Movimiento de compra, granel
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    @Override
    public void actualizarCompra(MovimientoGranel compra) throws PersistenciaException {
        try {
            registroComprasGranel.actualiza(compra);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que elimina un producto de una lista, el cual primero se
     * verificara si existe, y se realizaran los cambios necesarios para que su
     * eliminacion repercuta en la lista/inventario/catalogo.
     *
     * @param producto prodcuto abarrotes
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void eliminar(Producto producto) throws PersistenciaException {
        try {
            catalogoProductos.elimina(producto);
        } catch (DAOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Metodo que elimina una venta de una lista, el cual primero se verificara
     * si existe, y se realizaran los cambios y validaciones necesarios para que
     * su eliminacion repercuta en la lista/inventario/catalogo.
     *
     * @param venta Movimiento de venta, empacado
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void eliminarVenta(MovimientoEmpacado venta) throws PersistenciaException {
        if (venta.getProcesado()) {
            throw new PersistenciaException("Una venta ya procesada no puede ser eliminada");
        } else {
            try {
                registroVentasEmpacados.elimina(venta);
            } catch (DAOException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    /**
     * Metodo que elimina una compra de una lista, el cual primero se verificara
     * si existe, y se realizaran los cambios y validaciones necesarios para que
     * su eliminacion repercuta en la lista/inventario/catalogo.
     *
     * @param compra Movimiento de compra, empacado
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void eliminarCompra(MovimientoEmpacado compra) throws PersistenciaException {
        if (compra.getProcesado()) {
            throw new PersistenciaException("Una compra ya procesada no puede ser eliminada");
        } else {
            try {
                registroComprasEmpacados.elimina(compra);
            } catch (DAOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    /**
     * Metodo que elimina una venta de una lista, el cual primero se verificara
     * si existe, y se realizaran los cambios y validaciones necesarios para que
     * su eliminacion repercuta en la lista/inventario/catalogo.
     *
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void eliminarVenta(MovimientoGranel venta) throws PersistenciaException {
        try {
            registroVentasGranel.elimina(venta);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que elimina una compra de una lista, el cual primero se verificara
     * si existe, y se realizaran los cambios y validaciones necesarios para que
     * su eliminacion repercuta en la lista/inventario/catalogo.
     *
     * @param compra Movimiento de compra, empacado
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void eliminarCompra(MovimientoGranel compra) throws PersistenciaException {
        try {
            registroComprasGranel.elimina(compra);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }
    /**
     * Metodo que actualiza un inventario de ventas empacado , realizando
     * cambios si es que se realizaron,se realizaran los cambios y validaciones
     * necesarios para que su acyualizacion se concrte, este arrojara una
     * excepcion si el inventario no se puede actualizar.
     *
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void actualizarInventarioVentasEmpacado() throws PersistenciaException {
        
        /*
        Iterator<MovimientoEmpacado> ventasE = registroVentasEmpacados.lista().iterator();
        if ((!registroVentasEmpacados.lista().isEmpty())) {
            while (ventasE.hasNext()) {
                MovimientoEmpacado movimientoEmpacado = ventasE.next();
                
                
                if (!movimientoEmpacado.getProcesado()) {
                    if (inventarioProductosEmpacados.lista().contains(movimientoEmpacado.getProductoEmpacado())) {
                        ProductoEmpacado productoE = (ProductoEmpacado) inventarioProductosEmpacados.lista().get(inventarioProductosEmpacados.lista().indexOf(movimientoEmpacado.getProductoEmpacado()));
                        ProductoEmpacado bproductoE = new ProductoEmpacado(productoE);
                        if ((productoE.getCantidad() - movimientoEmpacado.getProductoEmpacado().getCantidad()) >= 0) {
                            bproductoE.setCantidad((productoE.getCantidad() - movimientoEmpacado.getProductoEmpacado().getCantidad()));
                            try {
                                inventarioProductosEmpacados.actualiza(bproductoE);
                                movimientoEmpacado.setProcesado(true);
                            } catch (DAOException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                        
                    } else {
                        inventarioProductosEmpacados.agrega(movimientoEmpacado.getProductoEmpacado());
                    }

                }
            }
        } else {
            throw new PersistenciaException("No se pudo actualizar el inventario de ventas empacados");
        }
        */
        
    }

    /**
     * Metodo que actualiza un inventario de compras granel , realizando cambios
     * si es que se realizaron,se realizaran los cambios y validaciones
     * necesarios para que su acyualizacion se concrte, este arrojara una
     * excepcion si el inventario no se puede actualizar.
     *
     * @throws PersistenciaException arroja un error si este no se puede
     * actualizar
     */
    @Override
    public void actualizarInventarioComprasGranel() throws PersistenciaException {
        /*
        Iterator<MovimientoGranel> compra = registroComprasGranel.lista().iterator();
        if ((!registroComprasGranel.lista().isEmpty())) {
            while (compra.hasNext()) {
                MovimientoGranel movimientoG = compra.next();
                if (!movimientoG.getProcesado()) {
                    if (inventarioProductosGranel.lista().contains(movimientoG.getProductoGranel())) {
                        ProductoGranel productoGranel = (ProductoGranel) inventarioProductosGranel.lista().get(inventarioProductosGranel.lista().indexOf(movimientoG.getProductoGranel()));
                        ProductoGranel bprodcutoGranel = new ProductoGranel(productoGranel);
                        bprodcutoGranel.setCantidad((productoGranel.getCantidad()) + movimientoG.getProductoGranel().getCantidad());
                        try {
                            inventarioProductosGranel.actualiza(bprodcutoGranel);
                        } catch (DAOException ex) {
                            System.out.println(ex.getMessage());
                        }
                        movimientoG.setProcesado(true);
                    } else {
                        inventarioProductosGranel.agrega(movimientoG.getProductoGranel());
                        movimientoG.setProcesado(true);
                    }
                }
            }
        } else {
            throw new PersistenciaException("No se puede actualizar el inventario de compras de productos empacados.");
        }
        */
    }

    /**
     * Metodo que actualiza un inventario de ventas granel , realizando cambios
     * si es que se realizaron,se realizaran los cambios y validaciones
     * necesarios para que su acyualizacion se concrte, este arrojara una
     * excepcion si el inventario no se puede actualizar.
     *
     * @throws PersistenciaException arroja un error si este no se puede
     * actualizar
     */
    @Override
    public void actualizarInventarioVentasGranel() throws PersistenciaException {
        Iterator<MovimientoGranel> ventasG = registroVentasGranel.lista().iterator();
        if ((!registroVentasGranel.lista().isEmpty())) {
            while (ventasG.hasNext()) {
                MovimientoGranel movimientoGranel = ventasG.next();
                if (!movimientoGranel.getProcesado()) {
                    if (inventarioProductosGranel.lista().contains(movimientoGranel.getProductoGranel())) {
                        ProductoGranel productoG = (ProductoGranel) inventarioProductosGranel.lista().get(inventarioProductosGranel.lista().indexOf(movimientoGranel.getProductoGranel()));
                        ProductoGranel bproductoG = new ProductoGranel(productoG);
                        if ((productoG.getCantidad() - movimientoGranel.getProductoGranel().getCantidad()) >= 0) {
                            bproductoG.setCantidad((productoG.getCantidad() - movimientoGranel.getProductoGranel().getCantidad()));
                            try {
                                inventarioProductosGranel.actualiza(bproductoG);
                                movimientoGranel.setProcesado(true);
                            } catch (DAOException e) {
                                System.err.println(e.getMessage());
                            }
                        }
                    } else {
                        //inventarioProductosGranel.agrega(movimientoGranel.getProductoGranel());
                    }
                }
            }
        } else {
            throw new PersistenciaException("No se pudo actualizar el inventario de ventas granel");
        }
    }

    /**
     * Metodo que actualiza un inventario de compras empacado , realizando
     * cambios si es que se realizaron,se realizaran los cambios y validaciones
     * necesarios para que su acyualizacion se concrte, este arrojara una
     * excepcion si el inventario no se puede actualizar.
     *
     * @throws PersistenciaException si entra en una excepcion
     */
    @Override
    public void actualizarInventarioComprasEmpacado() throws PersistenciaException {
        /*
        Iterator<MovimientoEmpacado> comprasE = registroComprasEmpacados.lista().iterator();
        if ((!registroComprasEmpacados.lista().isEmpty())) {
            while (comprasE.hasNext()) {
                MovimientoEmpacado movimientoEmpacado = comprasE.next();
                if (!movimientoEmpacado.getProcesado()) {
                    if (inventarioProductosEmpacados.lista().contains(movimientoEmpacado.getProductoEmpacado())) {
                        ProductoEmpacado productoE = (ProductoEmpacado) inventarioProductosEmpacados.lista().get(inventarioProductosEmpacados.lista().indexOf(movimientoEmpacado.getProductoEmpacado()));
                        ProductoEmpacado bproductoE = new ProductoEmpacado(productoE);
                        bproductoE.setCantidad((productoE.getCantidad() + movimientoEmpacado.getProductoEmpacado().getCantidad()));
                        try {
                            inventarioProductosEmpacados.actualiza(bproductoE);
                        } catch (DAOException e) {
                            System.err.println(e.getMessage());
                        }
                        movimientoEmpacado.setProcesado(true);
                    } else {
                        inventarioProductosEmpacados.agrega(movimientoEmpacado.getProductoEmpacado());
                        movimientoEmpacado.setProcesado(true);
                    }
                }
            }
        } else {
            throw new PersistenciaException("No se pudo actualizar el inventario de compras empacado");
        }
        */
    }
    
    /**
     * Devuelve el numero total de productos registrados en el sistema
     * @return Numero total de productos
     * @throws PersistenciaException Si no se pudo obtener el numero total de productos
     */
    @Override
    public int consultarNumeroTotalProductos() throws PersistenciaException {
        try {
            int resultados = catalogoProductos.numeroTotalProductos();
            return resultados;
        } catch (DAOException ex) {
            throw new PersistenciaException("No se pudo obetener el numero total de productos");
        }
    }
    
    /**
     * Metodo que regresa una lista del catalogo de productos, si esta lista no
     * existe se arrojara un error.
     *
     * @return lista de productos
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<Producto> consultarProductos() throws PersistenciaException {
        try {
            catalogoProductos.consultarProductos();
        } catch (DAOException ex) {
            throw new PersistenciaException("Ocurrio un error en la busqueda de productos, intente mas tarde...");
            //throw new PersistenciaException(ex.getMessage());
        }
        
        if (!catalogoProductos.lista().isEmpty()) {
            return catalogoProductos.lista();
        } else {
            throw new PersistenciaException("Lista de productos vacia");
        }
    }

    /**
     * Metodo que regresara una lista de solo productos empacados, si esta lista
     * no existe nos mostrara un error.
     *
     * @return lista de productos empacados
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<Producto> consultarProductosEmpacados() throws PersistenciaException {
        try {
            return catalogoProductos.lista('E');
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que regresara una lista de solo productos granel, si esta lista no
     * existe nos mostrara un error.
     *
     * @return lista de productos granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<Producto> consultarProductosGranel() throws PersistenciaException {
        try {
            return catalogoProductos.lista('G');
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que consulta la lista de inventarios de productos empacados, si
     * esta lista no existe arrojara un error.
     *
     * @return lista de inventario de prodcutos empacados
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<ProductoEmpacado> consultarInventarioProductosEmpacados() throws PersistenciaException {
        if (!inventarioProductosEmpacados.lista().isEmpty()) {
            return inventarioProductosEmpacados.lista();
        } else {
            throw new PersistenciaException("No hay productos empacados en el inventario");
        }
    }

    /**
     * Metodo que consulta la lista de inventarios de productos empacados, si
     * esta lista no existe arrojara un error.
     *
     * @return lista de inventario de prodcutos granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<ProductoGranel> consultarInventarioProductosGranel() throws PersistenciaException {
        if (!inventarioProductosGranel.lista().isEmpty()) {
            return inventarioProductosGranel.lista();
        } else {
            throw new PersistenciaException("No hay productos a granel en el inventario");
        }
    }

    /**
     * Metodo que muestra la lista de ventas de solo los productos empacados, si
     * esta lista no existe arrojara un error.
     *
     * @return lista de ventas empacado
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoEmpacado> consultarVentasEmpacado() throws PersistenciaException {
        if (!registroVentasEmpacados.lista().isEmpty()) {
            return registroVentasEmpacados.lista();
        } else {
            throw new PersistenciaException("No hay ventas de productos empacados registradas");
        }
    }

    /**
     * Metodo que muestra la lista de ventas de solo los productos empacados que
     * esten dentro del periodo, si esta lista no existe arrojara un error.
     *
     * @param periodo lapso de dos fechas
     * @return lista de ventas empacado en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoEmpacado> consultarVentasEmpacado(Periodo periodo) throws PersistenciaException {
        try {
            List<MovimientoEmpacado> lista = registroVentasEmpacados.lista(periodo);
            
            if (lista.isEmpty()) {
                throw new PersistenciaException("El registro de ventas de productos empacados esta vacio");
            }
            
            return lista;
        } catch (DAOException ex) {
            throw new PersistenciaException("Ocurrio un error al obtener las ventas por periodo, intente mas tarde...");
        }
    }

    /**
     * Metodo que muestra la lista de ventas de solo los productos granel, si
     * esta lista no existe arrojara un error.
     *
     * @return lista de ventas granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoGranel> consultarVentasGranel() throws PersistenciaException {
        if (!registroVentasGranel.lista().isEmpty()) {
            return registroVentasGranel.lista();
        } else {
            throw new PersistenciaException("La lista de ventas de productos granel no existe");
        }
    }

    /**
     * Metodo que muestra la lista de ventas de solo los productos granel que
     * esten dentro del periodo, si esta lista no existe arrojara un error.
     * @param periodo lapso de dos fechas
     * @return lista de ventas granel en base al periodo
     * @throws PersistenciaException Si ocurre un error al devolver la lista
     */
    @Override
    public List<MovimientoGranel> consultarVentasGranel(Periodo periodo) throws PersistenciaException {
        try {
            return registroVentasGranel.lista(periodo);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    /**
     * Metodo que muestra la lista de compras empacado de solo los productos
     * empacado, si esta lista no existe arrojara un error.
     *
     * @return lista de compras empacado
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoEmpacado> consultarComprasEmpacado() throws PersistenciaException {
        if (!registroComprasEmpacados.lista().isEmpty()) {
            return registroComprasEmpacados.lista();
        } else {
            throw new PersistenciaException("La lista de compras de productos granel no existe");
        }
    }

    /**
     * Metodo que muestra la lista de compras de solo los productos empacados
     * que esten dentro del periodo, si esta lista no existe arrojara un error.
     *
     * @param periodo lapso de dos fechas
     * @return lista de compras empacado en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoEmpacado> consultarComprasEmpacado(Periodo periodo) throws PersistenciaException {
        try {
            List<MovimientoEmpacado> lista = registroComprasEmpacados.lista(periodo);
            
            if (lista.isEmpty()) {
                throw new PersistenciaException("No hay compras de productos empacados en el registro");
            }
            
            return lista;
        } catch (DAOException ex) {
            throw new PersistenciaException("Ocurrio un error al consultar las compras de productos empacados, intente mas tarde...");
        }
    }

    /**
     * Metodo que muestra la lista de compras de solo los productos granel, si
     * esta lista no existe arrojara un error.
     *
     * @return lista de compras granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoGranel> consultarComprasGranel() throws PersistenciaException {
        return registroComprasGranel.lista();
    }

    /**
     * Metodo que muestra la lista de compras de solo los productos granel que
     * esten dentro del periodo, si esta lista no existe arrojara un error.
     *
     * @param periodo lapso de dos fechas
     * @return lista de compras granel en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoGranel> consultarComprasGranel(Periodo periodo) throws PersistenciaException {
        try {
            return registroComprasGranel.lista(periodo);
        } catch (DAOException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }

    @Override
    public int consultarNumeroTotalProductosEmpacados() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int consultarNumeroTotalProductosGranel() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int consultarNumeroTotalMovimientosEmpacados() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int consultarNumeroTotalMovimientosGranel() throws PersistenciaException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
