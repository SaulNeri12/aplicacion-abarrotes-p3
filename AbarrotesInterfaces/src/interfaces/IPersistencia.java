package interfaces;
import excepciones.PersistenciaException;
import java.util.List;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.MovimientoGranel;  
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
import objetosNegocio.ProductoGranel;
import objetosNegocio.Usuario;
import objetosServicio.Periodo;

/**
 * Provee de todos los procedimientos requeridos por clases tipo persistencia para
 * el manejo de operaciones en la aplicacion de abarrote.
 * @author Equipo 07
 */
public interface IPersistencia {
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Devuelve el usuario especificado si se aloja en la base de datos de abarrotes
     * @param usuario Usuario a buscar
     * @return Usuario si se encuentra registrado
     * @throws PersistenciaException Si ocurre un error interno en la busqueda
     */
    public Usuario obten(Usuario usuario) throws PersistenciaException;
    
    /**
     * Agrega el usuario dado a la base de datos de abarrotes
     * @param usuario Usuario a registrar
     * @throws PersistenciaException Si el usuario ya esta registrado o si ocurre
     * error interno
     */
    public void agregar(Usuario usuario) throws PersistenciaException;
    
    /**
     * Actualiza los datos del usuario especificado en la base de datos de abarrotes
     * @param usuario Usuario a actualizar datos
     * @throws PersistenciaException Si no se encuentra registrado el usuario o si
     * ocurre un error interno
     */
    public void actualizar(Usuario usuario) throws PersistenciaException;
    
    /**
     * Elimina el usuario de la base de datos de abarrotes
     * @param usuario Usuario a eliminar
     * @throws PersistenciaException Si el usuario no existe o si ocurre un 
     * error interno en la eliminacion del usuario
     */
    public void eliminar(Usuario usuario) throws PersistenciaException;
    
    
     /**
     * Metodo el cual permite obtener el producto del parametro dado,
     * siempre y cuando este si exista dentro de la lista a solicitar.
     * @param producto producto de abarrote
     * @return producto solicitado
     * @throws PersistenciaException arroja error si el producto no esta en la lista
     */
    Producto obten(Producto producto) throws PersistenciaException;
    
    /**
     * Metodo el cual permite obtener la venta del parametro dado,
     * siempre y cuando este si exista dentro de la lista a solicitar.
     * 
     * @param venta movimiento de venta, Empacado
     * @return regresa el movimiento de venta Empacado solicitado
     * @throws PersistenciaException arroja error si la venta no esta en la lista
     */
    MovimientoEmpacado obtenVenta(MovimientoEmpacado venta) throws PersistenciaException;
    
    /**
     * Metodo el cual permite obtener la compra del parametro dado,
     * siempre y cuando este si exista dentro de la lista a solicitar.
     * 
     * @param compra movimiento de compra, Empacado
     * @return regresa el movimiento de compra Empacado solicitado
     * @throws PersistenciaException arroja error si la compra no esta en la lista
     */
    MovimientoEmpacado obtenCompra(MovimientoEmpacado compra) throws PersistenciaException;
    
    /** 
     * Metodo el cual permite obtener la venta del parametro dado,
     * siempre y cuando este si exista dentro de la lista a solicitar.
     * 
     * @param venta movimiento de venta, Granel
     * @return regresa el movimiento de venta Granel solicitado
     * @throws PersistenciaException arroja error si la venta no esta en la lista
     */
    MovimientoGranel obtenVenta(MovimientoGranel venta) throws PersistenciaException;
    
    /**
     * Metodo el cual permite obtener la compra del parametro dado,
     * siempre y cuando este si exista dentro de la lista a solicitar.
     * 
     * @param compra movimiento de compra, Granel
     * @return regresa el movimiento de compra Granel solicitado
     * @throws PersistenciaException arroja error si la compra no esta en la lista
     */
    MovimientoGranel obtenCompra(MovimientoGranel compra) throws PersistenciaException;
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Metodo que agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     * 
     * @param producto producto abarrotes
     * @throws PersistenciaException arroja error si ya existe en la lista
     */
    public void agregar(Producto producto) throws PersistenciaException;
    
    /**
     * Metodo que agrega una venta dada en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     * 
     * @param venta Movimiento de venta, empacado
     * @throws PersistenciaException arroja error si ya existe en la lista 
     */
    public void agregarVenta(MovimientoEmpacado venta) throws PersistenciaException;
    
    /**
     * Metodo que agrega una compra dada en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion. 
     * 
     * @param compra Movimiento de compra, empacado
     * @throws PersistenciaException arroja error si ya existe en la lista 
     */
    public void agregarCompra(MovimientoEmpacado compra) throws PersistenciaException;
    
    /**
     * Metodo que agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion. 
     * 
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException arroja error si ya existe en la lista 
     */
    public void agregarVenta(MovimientoGranel venta) throws PersistenciaException;
    
     /**
     * Metodo que agrega un producto dado en el parametro, a una lista, si esta
     * se enuentra en ella arrojara una excepcion.
     * 
     * @param compra Movimiento de compra, granel
     * @throws PersistenciaException arroja error si ya existe en la lista 
     */
    public void agregarCompra(MovimientoGranel compra) throws PersistenciaException;
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Metodo que actualiza un producto de una lista, realizando cambios si es que se realizaron,
     * este arrojara una excepcion si el producto no se puede actualizar.
     * 
     * @param producto producto abarrotes
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    public void actualizar(Producto producto) throws PersistenciaException;
    
     /**
     * Metodo que actualiza una venta de una lista, realizando cambios si es que se realizaron,
     * este arrojara una excepcion si el producto no se puede actualizar.
     * 
     * @param venta Movimiento de venta, empacado
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    public void actualizarVenta(MovimientoEmpacado venta) throws PersistenciaException;
    
   /**
     * Metodo que actualiza una compra de una lista, realizando cambios si es que se realizaron,
     * este arrojara una excepcion si el producto no se puede actualizar.
     * 
     * @param compra Movimiento de compra, Empacado
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    public void actualizarCompra(MovimientoEmpacado compra) throws PersistenciaException;
    
     /**
     * Metodo que actualiza una venta de una lista, realizando cambios si es que se realizaron,
     * este arrojara una excepcion si el producto no se puede actualizar.
     *  
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    public void actualizarVenta(MovimientoGranel venta) throws PersistenciaException;
    
    /**
     * Metodo que actualiza una compra de una lista, realizando cambios si es que se realizaron,
     * este arrojara una excepcion si el producto no se puede actualizar. 
     * 
     * @param compra Movimiento de compra, granel
     * @throws PersistenciaException arroja un error si no se puede actualizar
     */
    public void actualizarCompra(MovimientoGranel compra) throws PersistenciaException;
    
    /////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Metodo que elimina un producto de una lista, el cual primero se verificara si existe, y 
     * se realizaran los cambios necesarios para que su eliminacion repercuta en la lista/inventario/catalogo.
     * 
     * @param producto prodcuto abarrotes
     * @throws PersistenciaException si entra en una excepcion
     */
    public void eliminar(Producto producto) throws PersistenciaException;
    
    /**
     * Metodo que elimina una venta de una lista, el cual primero se verificara si existe, y 
     * se realizaran los cambios y validaciones necesarios para que su eliminacion repercuta en la lista/inventario/catalogo.
     * 
     * @param venta Movimiento de venta, empacado
     * @throws PersistenciaException si entra en una excepcion
     */
    public void eliminarVenta(MovimientoEmpacado venta) throws PersistenciaException;
    
     /**
     * Metodo que elimina una compra de una lista, el cual primero se verificara si existe, y 
     * se realizaran los cambios y validaciones necesarios para que su eliminacion repercuta en la lista/inventario/catalogo.
     * 
     * @param compra Movimiento de compra, empacado
     * @throws PersistenciaException si entra en una excepcion
     */
    public void eliminarCompra(MovimientoEmpacado compra) throws PersistenciaException;
    
    /**
     *Metodo que elimina una venta de una lista, el cual primero se verificara si existe, y 
     * se realizaran los cambios y validaciones necesarios para que su eliminacion repercuta en la lista/inventario/catalogo.
     * 
     * @param venta Movimiento de venta, granel
     * @throws PersistenciaException si entra en una excepcion
     */
    public void eliminarVenta(MovimientoGranel venta) throws PersistenciaException;
    
    /**
     * Metodo que elimina una compra de una lista, el cual primero se verificara si existe, y 
     * se realizaran los cambios y validaciones necesarios para que su eliminacion repercuta en la lista/inventario/catalogo.
     * 
     * @param compra Movimiento de compra, empacado
     * @throws PersistenciaException si entra en una excepcion
     */
    public void eliminarCompra(MovimientoGranel compra) throws PersistenciaException;

    /////////////////////////////////////////////////////////////////////////////////////////
    
     /**
     *  Metodo que actualiza un inventario de ventas empacado , realizando cambios si es que se realizaron,se realizaran 
     * los cambios y validaciones necesarios para que su acyualizacion se concrte, este arrojara una excepcion si 
     * el inventario no se puede actualizar.
     * 
     * @throws PersistenciaException si entra en una excepcion
     */
    public void actualizarInventarioVentasEmpacado() throws PersistenciaException;
    
    /**
     *  Metodo que actualiza un inventario de compras granel , realizando cambios si es que se realizaron,se realizaran 
     * los cambios y validaciones necesarios para que su acyualizacion se concrte, este arrojara una excepcion si 
     * el inventario no se puede actualizar.
     * 
     * @throws PersistenciaException arroja un error si este no se puede actualizar
     */

    public void actualizarInventarioComprasGranel() throws PersistenciaException;
    
    /**
     * Metodo que actualiza un inventario de ventas granel , realizando cambios si es que se realizaron,se realizaran 
     * los cambios y validaciones necesarios para que su acyualizacion se concrte, este arrojara una excepcion si 
     * el inventario no se puede actualizar.
     * 
     * @throws PersistenciaException arroja un error si este no se puede actualizar
     */
    public void actualizarInventarioVentasGranel() throws PersistenciaException;
   /**
     * Metodo que actualiza un inventario de compras empacado , realizando cambios si es que se realizaron,se realizaran 
     * los cambios y validaciones necesarios para que su acyualizacion se concrte, este arrojara una excepcion si 
     * el inventario no se puede actualizar.
     * 
     * @throws PersistenciaException si entra en una excepcion
     */
    public void actualizarInventarioComprasEmpacado() throws PersistenciaException;
    
   /**
     * Metodo que regresa una lista del catalogo de productos, si esta lista no existe
     * se arrojara un error.
     * 
     * @return lista de productos
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<Producto> consultarProductos() throws PersistenciaException;

    /////////////////////////////////////////////////////////////////////////////////////////
    
     /**
     * Metodo que regresara una lista de solo productos empacados, si esta lista no existe
     * nos mostrara un error.
     * 
     * @return lista de productos empacados
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<Producto> consultarProductosEmpacados() throws PersistenciaException;
    
    /**
     * Metodo que regresara una lista de solo productos granel, si esta lista no existe
     * nos mostrara un error.
     * 
     * @return lista de productos granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<Producto> consultarProductosGranel() throws PersistenciaException;

    /**
     * Metodo que consulta la lista de inventarios de productos empacados, si esta lista no existe
     * arrojara un error.
     * 
     * @return lista de inventario de prodcutos empacados
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<ProductoEmpacado> consultarInventarioProductosEmpacados() throws PersistenciaException;
    
   /**
     * Metodo que consulta la lista de inventarios de productos empacados, si esta lista no existe
     * arrojara un error.
     * 
     * @return regresa un inventario
     * @return lista de inventario de prodcutos granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<ProductoGranel> consultarInventarioProductosGranel() throws PersistenciaException;

    /////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Metodo que muestra la lista de ventas de solo los productos empacados, si esta lista no existe
     * arrojara un error.
     * 
     * @return lista de ventas empacado
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoEmpacado> consultarVentasEmpacado() throws PersistenciaException;
    
    /**
     * Metodo que muestra la lista de ventas de solo los productos empacados que esten dentro del periodo, 
     * si esta lista no existe arrojara un error.
     * 
     * @param periodo lapso de dos fechas
     * @return lista de ventas empacado en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoEmpacado> consultarVentasEmpacado(Periodo periodo) throws PersistenciaException;
    
    /**
     * Metodo que muestra la lista de ventas de solo los productos granel, si esta lista no existe
     * arrojara un error.
     * 
     * @return lista de ventas granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoGranel> consultarVentasGranel() throws PersistenciaException;

    /**
     * Metodo que muestra la lista de ventas de solo los productos granel que esten dentro del periodo, 
     * si esta lista no existe arrojara un error.
     * 
     * @param periodo lapso de dos fechas
     * @return lista de ventas granel en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoGranel> consultarVentasGranel(Periodo periodo) throws PersistenciaException;

    /////////////////////////////////////////////////////////////////////////////////////////
    
    /**
     * Metodo que muestra la lista de compras empacado de solo los productos empacado, si esta lista no existe
     * arrojara un error.
     * 
     * @return lista de compras empacado
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoEmpacado> consultarComprasEmpacado() throws PersistenciaException;

   /**
     * Metodo que muestra la lista de compras de solo los productos empacados que esten dentro del periodo, 
     * si esta lista no existe arrojara un error.
     * 
     * @param periodo lapso de dos fechas
     * @return lista de compras empacado en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoEmpacado> consultarComprasEmpacado(Periodo periodo) throws PersistenciaException;
    
    /**
     * Metodo que muestra la lista de compras de solo los productos granel, si esta lista no existe
     * arrojara un error.
     * 
     * @return lista de compras granel
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoGranel> consultarComprasGranel() throws PersistenciaException;

   /**
     * Metodo que muestra la lista de compras de solo los productos granel que esten dentro del periodo, 
     * si esta lista no existe arrojara un error.
     * 
     * @param periodo lapso de dos fechas
     * @return lista de compras granel en base al periodo
     * @throws PersistenciaException arrojara si no existe la lista
     */
    public List<MovimientoGranel> consultarComprasGranel(Periodo periodo) throws PersistenciaException;   
}