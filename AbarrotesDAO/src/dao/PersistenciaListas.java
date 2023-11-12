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

    /**
     * Constructor el cual crea intanbcias de Productos, Productos
     * Empacados/Granel y Movimiento Empacado/Granel
     
    public PersistenciaListas() {
        this.catalogoProductos = new Productos();
        this.registroVentasGranel = new MovimientosGranel();
        this.registroComprasGranel = new MovimientosGranel();
        this.inventarioProductosGranel = new ProductosGranel();
        this.registroVentasEmpacados = new MovimientosEmpacados();
        this.registroComprasEmpacados = new MovimientosEmpacados();
        this.inventarioProductosEmpacados = new ProductosEmpacados();
    }
    */
    
    /**
     * Crea una persistencia para el acceso y registro de ventas de productos
     * a traves de la conexion a la base de datos dada.
     * @param conexion Conexion a la base de datos de abarrotes
     */
    public PersistenciaListas(ConexionAbarrotesBD conexion) {
        this.catalogoProductos = new Productos(conexion);
        this.registroVentasGranel = new MovimientosGranel();
        this.registroComprasGranel = new MovimientosGranel();
        this.inventarioProductosGranel = new ProductosGranel();
        this.registroVentasEmpacados = new MovimientosEmpacados();
        this.registroComprasEmpacados = new MovimientosEmpacados();
        this.inventarioProductosEmpacados = new ProductosEmpacados();
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
     * Metodo el cual permite obtener la venta del parametro dado, siempre y
     * cuando este si exista dentro de la lista a solicitar.
     *
     * @param venta movimiento de venta, Empacado
     * @return regresa el movimiento de venta Empacado solicitado
     * @throws PersistenciaException arroja error si la venta no esta en la
     * lista
     */
    @Override
    public MovimientoEmpacado obtenVenta(MovimientoEmpacado venta) throws PersistenciaException {
        try {
            return registroVentasEmpacados.obten(venta);
        } catch (PersistenciaException e) {
            throw new PersistenciaException("El registro de ventas de productos empacados que se busca no existe");
        }
    }

    /**
     * Metodo el cual permite obtener la compra del parametro dado, siempre y
     * cuando este si exista dentro de la lista a solicitar.
     *
     * @param compra movimiento de compra, Empacado
     * @return regresa el movimiento de compra Empacado solicitado
     * @throws PersistenciaException arroja error si la compra no esta en la
     * lista
     */
    @Override //
    public MovimientoEmpacado obtenCompra(MovimientoEmpacado compra) throws PersistenciaException {
        try {
            return registroComprasEmpacados.obten(compra);
        } catch (Exception e) {
            throw new PersistenciaException("El registro de compras de productos empacados que se busca no existe");
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
            return registroVentasGranel.obten(venta);
        } catch (Exception e) {
            throw new PersistenciaException("El registro de ventas de productos a granel que se busca no existe");
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
            return registroComprasGranel.obten(compra);
        } catch (Exception e) {
            throw new PersistenciaException("El registro de compras de productos a granel que se busca no existe");
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
        boolean catalogo = false;
        if (registroVentasEmpacados.obten(venta) == null) {
            for (Producto producto : catalogoProductos.lista()) {
                if (producto.getClave().equalsIgnoreCase(venta.getProductoEmpacado().getClave())) {
                    catalogo = true;
                    registroVentasEmpacados.agrega(venta);
                    break;
                }
            }

        } else {
            throw new PersistenciaException("Error: La venta ya esta registrada");
        }
        if (catalogo == false) {
            throw new PersistenciaException("Error: Producto inexistente");
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
        boolean catalogo = false;
        if (registroComprasEmpacados.obten(compra) == null) {
            for (Producto producto : catalogoProductos.lista()) {
                if (producto.getClave().equalsIgnoreCase(compra.getProductoEmpacado().getClave())) {
                    catalogo = true;
                    registroComprasEmpacados.agrega(compra);
                    break;
                }
            }

        } else {
            throw new PersistenciaException("Error: La venta ya esta registrada");
        }
        if (catalogo == false) {
            throw new PersistenciaException("Error: Producto inexistente");
        }
    }

    /**
     * Metodo que agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     *
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException arroja error si ya existe en la lista
     */
    @Override
    public void agregarVenta(MovimientoGranel venta) throws PersistenciaException {
        boolean catalogo = false;
        if (registroVentasGranel.obten(venta) == null) {
            for (Producto producto : catalogoProductos.lista()) {
                if (producto.getClave().equalsIgnoreCase(venta.getProductoGranel().getClave())) {
                    catalogo = true;
                    registroVentasGranel.agrega(venta);
                    break;
                }
            }

        } else {
            throw new PersistenciaException("Error: La venta ya esta registrada");
        }
        if (catalogo == false) {
            throw new PersistenciaException("Error: Producto inexistente");
        }
    }

    /**
     * Metodo que agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     *
     * @param compra Movimiento de compra, granel
     * @throws PersistenciaException arroja error si ya existe en la lista
     */
    @Override
    public void agregarCompra(MovimientoGranel compra) throws PersistenciaException {
        boolean catalogo = false;
        if (registroComprasGranel.obten(compra) == null) {
            for (Producto producto : catalogoProductos.lista()) {
                if (producto.getClave().equalsIgnoreCase(compra.getProductoGranel().getClave())) {
                    catalogo = true;
                    registroComprasGranel.agrega(compra);
                    break;
                }
            }

        } else {
            throw new PersistenciaException("Error: La venta ya esta registrada");
        }
        if (catalogo == false) {
            throw new PersistenciaException("Error: Producto inexistente");
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
        if (venta.getProcesado()) {
            throw new PersistenciaException("Una venta ya procesada no puede ser eliminada");
        } else {
            try {
                registroVentasGranel.elimina(venta);
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
    public void eliminarCompra(MovimientoGranel compra) throws PersistenciaException {
            if (compra.getProcesado()) {
                throw new PersistenciaException ("Una compra ya procesada no puede ser eliminada");
            }else {
                try {
                    registroComprasGranel.elimina(compra);
                } catch (DAOException e) {
                    System.out.println(e.getMessage());
                }
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
                        inventarioProductosGranel.agrega(movimientoGranel.getProductoGranel());
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
        if (!catalogoProductos.lista().isEmpty()) {
            return catalogoProductos.lista();
        } else {
            throw new PersistenciaException("La lista de productos no existe");
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
        if (!catalogoProductos.lista('E').isEmpty()) {
            return catalogoProductos.lista('E');
        } else {
            throw new PersistenciaException("La lista de productos empacado no existe");
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
        if (!catalogoProductos.lista('G').isEmpty()) {
            return catalogoProductos.lista('G');
        } else {
            throw new PersistenciaException("La lista de productos Granel no existe");
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
            throw new PersistenciaException("La lista de inventario de productos empacado no existe");
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
            throw new PersistenciaException("La lista de inventario de productos granel no existe");
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
            throw new PersistenciaException("La lista de ventas de productos empacados no existe");
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
        if (!registroVentasEmpacados.lista(periodo).isEmpty()) {
            return registroVentasEmpacados.lista(periodo);
        } else {
            throw new PersistenciaException("La lista de ventas de productos empacados en el periodo no existe");
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
     *
     * @param periodo lapso de dos fechas
     * @return lista de ventas granel en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    @Override
    public List<MovimientoGranel> consultarVentasGranel(Periodo periodo) throws PersistenciaException {
        if (!registroVentasGranel.lista(periodo).isEmpty()) {
            return registroVentasGranel.lista(periodo);
        } else {
            throw new PersistenciaException("La lista de ventas de productos granel en el periodo no existe");
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
        if (!registroComprasEmpacados.lista(periodo).isEmpty()) {
            return registroComprasEmpacados.lista(periodo);
        } else {
            throw new PersistenciaException("La lista de compras de productos granel en el periodo no existe");
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
        if (!registroComprasGranel.lista().isEmpty()) {
            return registroComprasGranel.lista();
        } else {
            throw new PersistenciaException("La lista de compras de productos granel no existe");
        }
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
        if (!registroComprasGranel.lista(periodo).isEmpty()) {
            return registroComprasGranel.lista(periodo);
        } else {
            throw new PersistenciaException("La lista de compras de productos granel en el periodo no existe");
        }
    }
}
