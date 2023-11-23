package control;

import conexion.ConexionAbarrotesBD;
import excepciones.PersistenciaException;
import interfaces.IPersistencia;
import interfazUsuario.ConstantesGUI;
import interfazUsuario.DlgMovimiento;
import interfazUsuario.DlgProducto;
import interfazUsuario.DlgPeriodo;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.Producto;
import objetosNegocio.ProductoEmpacado;
import objetosNegocio.ProductoGranel;
import objetosServicio.Fecha;
import objetosServicio.Periodo;
import dao.PersistenciaListas;
import conexion.ConexionConfig;
import java.util.logging.Level;
import java.util.logging.Logger;
import objetosNegocio.Usuario;

/**
 *
 * @author EquipoSiete
 */
public class Control {

    private IPersistencia persistencia;
    private Conversiones conversiones;

    public ConexionAbarrotesBD conexionBD;
    
    public Usuario operador;
    
    /**
     * Método Constructor que crea dos instancias de las clases persistencia y
     * conversiones.
     */
    public Control() {
        ConexionConfig configConexion = new ConexionConfig("abarrotes", "9999", "root", "");
        conexionBD = new ConexionAbarrotesBD(configConexion);
    }
    
    /**
     * Inicializa la persistencia con la base de datos especificada
     */
    public void prepararDatos() {
        persistencia = new PersistenciaListas(conexionBD);
        conversiones = new Conversiones();
    }

    /**
     * Asigna el operador actual del sistema
     * @param usuario Usuario que operara el sistema
     */
    public void setOperador(Usuario usuario) {
        this.operador = usuario;
    }
    
    /**
     * Devuelve el operador del sistema
     * @return Operador
     */
    public Usuario getOperador() {
        return this.operador;
    }
    
    /**
     * Recoje el usuario del dialogo de Login y verifica los datos para validar
     * el ID y contrasena para su posterior intento de inicio de sesion
     * @param frame Frame padre
     * @param usuario Usuario que quiere iniciar sesion
     * @return Usuario encontrado
     * @throws PersistenciaException si el usuario no existe
     */
    public Usuario iniciarSesion(JFrame frame, Usuario usuario) throws PersistenciaException {
        Usuario encontrado = null;
        
        try {
            encontrado = persistencia.obten(usuario);
            
            if (encontrado == null) {
                throw new PersistenciaException("El usuario no existe");
            }
                    
            if (!usuario.getContrasena().equalsIgnoreCase(encontrado.getContrasena())) {
                throw new PersistenciaException("Contrasena incorrecta");
            }
            
            return encontrado;
            
        } catch (PersistenciaException ex) {
            throw new PersistenciaException(ex.getMessage());
        }
    }
    
    /**
     * Busca un producto por su clave y lo muestra en pantalla
     * @param frame Frame padre
     */
    public void buscarProducto(JFrame frame) {
        Producto producto = null, encontrado = null;
        StringBuffer respuesta = new StringBuffer("");
        DlgProducto dlgProducto;
        ConstantesGUI validar = null;

        //Captura la clave del producto
        String claveProducto = JOptionPane.showInputDialog(
                frame, 
                "Clave del producto:", 
                "Buscar producto", 
                JOptionPane.QUESTION_MESSAGE
        );
        
        try {
            encontrado = persistencia.obten(new Producto(claveProducto));
            
            if (encontrado == null) {
                throw new PersistenciaException("El producto no se encuentra registrado");
            }
            
            dlgProducto = new DlgProducto(
                    frame, 
                    "Producto buscado", 
                    true, 
                    encontrado, 
                    ConstantesGUI.DESPLEGAR, 
                    respuesta
            );
            
        } catch (PersistenciaException ex) {
            //System.out.println(ex);
            JOptionPane.showMessageDialog(
                    frame, 
                    ex.getMessage(), 
                    "Buscar producto", 
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    /**
     * Método capaz de agregar un producto mediante una ventana emergente que, a
     * continuación procederá a solicitar la clave del nuevo producto, si esta
     * clave, al usar el método obtener, no obtiene un producto, el producto
     * nuevo prodrá ser creado.
     * @param frame Ventana principal.
     */
    public void agregarProducto(JFrame frame) {
        Producto producto = null, encontrado = null;
        StringBuffer respuesta = new StringBuffer("");
        DlgProducto dlgProducto = null;
        ConstantesGUI validar = null;

        //Captura la clave del producto
        String claveProducto = JOptionPane.showInputDialog(
                frame, 
                "Clave del producto:", 
                "Agregar Producto", 
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (claveProducto == null) {
            return;
        }
        
        producto = new Producto(claveProducto);
              
        try {
            encontrado = persistencia.obten(producto);
            
            if (encontrado != null) {
                throw new PersistenciaException("El producto ya se encuentra registrado");
            }
            
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            
            return;
        }
        
        dlgProducto = new DlgProducto(
                frame,
                "Agregar Producto",
                true,
                producto,
                ConstantesGUI.AGREGAR,
                respuesta
        );
        
        // se cancelo la operacion a traves del dialogo
        if (dlgProducto.respuesta.toString().equals("Cancelar")) {
            return;
        }
        
        try {
            persistencia.agregar(producto);
            
            JOptionPane.showMessageDialog(
                frame,
                String.format("Se agrego el producto correctamente [CODIGO: %s]", producto.getClave()),
                "Operacion Completada",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage(),
                "Error - Agregar Producto",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Método capaz de agregar una compra de tipo movimiento granel, mediante
     * una ventana emergente que solicitará la clave de la nueva compra, si la
     * clave no coincide con la de una compra previa, agrega la compra.
     *
     * @param frame Ventana principal.
     */
    public void agregarCompraGranel(JFrame frame) {
        MovimientoGranel compraGranel, bcompraGranel = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoGranel> listaProductosGranel = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoGranel> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosGranel().isEmpty()) {

                listaProductos = persistencia.consultarProductosGranel();
                listaProductosGranel = new ArrayList<>();

                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosGranel.add(new ProductoGranel(listaProductos.get(i)));
                }

                productosComboBox = conversiones.productosGranelComboBoxModel(listaProductosGranel);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Agregar Compra Granel", JOptionPane.QUESTION_MESSAGE);
                try {

                    if ((!clave.equals("") && !clave.contains(" "))) {
                        compraGranel = new MovimientoGranel(clave);
                        try {
                            bcompraGranel = persistencia.obtenCompra(compraGranel);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, "La compra granel ya existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bcompraGranel == null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, compraGranel, productosComboBox, "Capture los datos de la compra", ConstantesGUI.AGREGAR, respuesta);
                            try {
                                persistencia.agregarCompra(compraGranel);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (bcompraGranel!=null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bcompraGranel, productosComboBox, "La compra ya esta registrada", ConstantesGUI.DESPLEGAR, respuesta);
                        }
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                            JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de agregar una venta de tipo movimiento granel, mediante una
     * ventana emergente que solicitará la clave de la nueva venta, si la clave
     * no coincide con la de una compra previa, agrega la venta.
     *
     * @param frame Ventana principal.
     */
    public void agregarVentaGranel(JFrame frame) {
        MovimientoGranel ventaGranel, bventaGranel = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoGranel> listaProductosGranel = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoGranel> productosComboBox = null;

        
        try {
            if (!persistencia.consultarProductosGranel().isEmpty()) {

                listaProductos = persistencia.consultarProductosGranel();
                listaProductosGranel = new ArrayList<>();

                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosGranel.add(new ProductoGranel(listaProductos.get(i)));
                }

                productosComboBox = conversiones.productosGranelComboBoxModel(listaProductosGranel);

                String clave = JOptionPane.showInputDialog(frame, "Clave de la venta:", "Agregar Venta Granel", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        ventaGranel = new MovimientoGranel(clave);
                        try {
                            bventaGranel = persistencia.obtenVenta(ventaGranel);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bventaGranel == null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, ventaGranel, productosComboBox, "Capture los datos de la venta", ConstantesGUI.AGREGAR, respuesta);
                            try {
                                persistencia.agregarVenta(ventaGranel);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        } 
                        if (bventaGranel!=null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bventaGranel, productosComboBox, "La venta ya esta registrada", ConstantesGUI.DESPLEGAR, respuesta);
                        }
                        
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de agregar una compra de tipo movimiento empacado, mediante
     * una ventana emergente que solicitará la clave de la nueva compra, si la
     * clave no coincide con la de una compra previa, agrega la compra.
     *
     * @param frame Ventana principal.
     */
    public void agregarCompraEmpacado(JFrame frame) {
        MovimientoEmpacado compraEmpacado, bcompraEmpacado = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoEmpacado> listaProductosEmpacados = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento;
        DefaultComboBoxModel<ProductoEmpacado> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosEmpacados().isEmpty()) {
                listaProductos = persistencia.consultarProductosEmpacados();
                listaProductosEmpacados = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosEmpacados.add(new ProductoEmpacado(listaProductos.get(i)));
                }

                productosComboBox = conversiones.productosEmpacadoComboBoxModel(listaProductosEmpacados);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Agregar compra", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        compraEmpacado = new MovimientoEmpacado(clave);
                        try {
                            bcompraEmpacado = persistencia.obtenCompra(compraEmpacado);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bcompraEmpacado == null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, compraEmpacado, productosComboBox, "Capture los datos de la compra", ConstantesGUI.AGREGAR, respuesta);
                            if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                                dlgMovimiento.dispose();
                            }
                            try {
                                persistencia.agregarCompra(compraEmpacado);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (bcompraEmpacado!=null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bcompraEmpacado, productosComboBox, "La compra ya esta registrada", ConstantesGUI.DESPLEGAR, respuesta);   
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de agregar una venta de tipo movimiento empacado, mediante
     * una ventana emergente que solicitará la clave de la nueva venta, si la
     * clave no coincide con la de una compra previa, agrega la venta.
     *
     * @param frame Ventana principal.
     */
    public void agregarVentaEmpacado(JFrame frame) {
        MovimientoEmpacado ventaEmpacado, bventaEmpacado = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoEmpacado> listaProductosEmpacado = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoEmpacado> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosEmpacados().isEmpty()) {
                listaProductos = persistencia.consultarProductosEmpacados();
                listaProductosEmpacado = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosEmpacado.add(new ProductoEmpacado(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosEmpacadoComboBoxModel(listaProductosEmpacado);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la venta:", "Agregar venta", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        ventaEmpacado = new MovimientoEmpacado(clave);
                        try {
                            bventaEmpacado = persistencia.obtenVenta(ventaEmpacado);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bventaEmpacado == null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, ventaEmpacado, productosComboBox, "Capture los datos de la venta", ConstantesGUI.AGREGAR, respuesta);
                            try {
                                persistencia.agregarVenta(ventaEmpacado);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        if (bventaEmpacado!=null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bventaEmpacado, productosComboBox, "La venta ya esta registrada", ConstantesGUI.DESPLEGAR, respuesta);
                        }
                     
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    //************************Actualiza***********************
    /**
     * Método capaz de actualizar los datos de un producto, se despliega una
     * ventana emergente donde solicitará la clave y si esta coincide con la de
     * un producto, podrá actualizarse.
     *
     * @param frame Ventana principal.
     */
    public void actualizaProducto(JFrame frame) {
        Producto producto = null, encontrado = null;
        StringBuffer respuesta = new StringBuffer("");
        DlgProducto dlgProducto = null;
        ConstantesGUI validar = null;

        //Captura la clave del producto
        String claveProducto = JOptionPane.showInputDialog(
                frame, 
                "Clave del producto:", 
                "Actualizar Producto", 
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (claveProducto == null) {
            return;
        }
        
        producto = new Producto(claveProducto);
              
        try {
            encontrado = persistencia.obten(producto);
            
            if (encontrado == null) {
                throw new PersistenciaException("El producto no esta registrado");
            }
            
            producto = encontrado;
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            
            return;
        }
        
        dlgProducto = new DlgProducto(
                frame,
                "Actualizar Producto",
                true,
                producto,
                ConstantesGUI.ACTUALIZAR,
                respuesta
        );
        
        // se cancelo la operacion a traves del dialogo
        if (dlgProducto.respuesta.toString().equals("Cancelar")) {
            return;
        }
        
        int opcion = JOptionPane.showConfirmDialog(
                frame, 
                "¿Deseas actualizar los datos del producto?", 
                "Actualizar Producto", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        // si no se confimar, cierra el dialogo...
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.DEFAULT_OPTION) {
            return;
        }
        
        try {
            persistencia.actualizar(producto);
            
            JOptionPane.showMessageDialog(
                frame,
                String.format("Se actualizaron los datos del producto [CODIGO: %s]", producto.getClave()),
                "Operacion Completada",
                JOptionPane.INFORMATION_MESSAGE
            );
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage(),
                "Error - Actualizar Producto",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Método capaz de actualizar los datos de una compra granel, se despliega
     * una ventana emergente donde solicitará la clave y si esta coincide con la
     * de una compra previa, podrá actualizarse.
     *
     * @param frame Ventana principal.
     */
    public void actualizarCompraGranel(JFrame frame) {
        MovimientoGranel compraGranel, bcompraGranel = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoGranel> listaProductosGranel = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoGranel> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosGranel().isEmpty()) {
                listaProductos = persistencia.consultarProductosGranel();
                listaProductosGranel = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosGranel.add(new ProductoGranel(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosGranelComboBoxModel(listaProductosGranel);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Editar compra", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        compraGranel = new MovimientoGranel(clave);
                        try {
                            bcompraGranel = persistencia.obtenCompra(compraGranel);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bcompraGranel != null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bcompraGranel, productosComboBox, "Edite los datos de la compra granel", ConstantesGUI.ACTUALIZAR, respuesta);

                            try {
                                persistencia.actualizarCompra(bcompraGranel);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        if (bcompraGranel == null) {
                            JOptionPane.showMessageDialog(frame, "La compra no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay compras de productos granel", "Cancelando...", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de actualizar los datos de una venta granel, se despliega
     * una ventana emergente donde solicitará la clave y si esta coincide con la
     * de una compra previa, podrá actualizarse.
     *
     * @param frame Ventana principal.
     */
    public void actualizarVentaGranel(JFrame frame) {
        MovimientoGranel ventaGranel, bventaGranel = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoGranel> listaProductosGranel = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoGranel> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosGranel().isEmpty()) {
                listaProductos = persistencia.consultarProductosGranel();
                listaProductosGranel = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosGranel.add(new ProductoGranel(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosGranelComboBoxModel(listaProductosGranel);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la venta:", "Editar venta", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        ventaGranel = new MovimientoGranel(clave);
                        try {
                            bventaGranel = persistencia.obtenVenta(ventaGranel);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bventaGranel != null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bventaGranel, productosComboBox, "Edite los datos de la venta granel", ConstantesGUI.ACTUALIZAR, respuesta);

                            try {
                                persistencia.actualizarVenta(bventaGranel);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        if (bventaGranel == null) {
                            JOptionPane.showMessageDialog(frame, "La venta no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay ventas de productos granel", "Cancelando...", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de actualizar los datos de una compra granel, se despliega
     * una ventana emergente donde solicitará la clave y si esta coincide con la
     * de una compra previa, podrá actualizarse.
     *
     * @param frame Ventana principal.
     */
    public void actualizarCompraEmpacado(JFrame frame) {
        MovimientoEmpacado compraEmpacado, bcompraEmpacado = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoEmpacado> listaProductosEmpacado = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoEmpacado> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosEmpacados().isEmpty()) {
                listaProductos = persistencia.consultarProductosEmpacados();
                listaProductosEmpacado = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosEmpacado.add(new ProductoEmpacado(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosEmpacadoComboBoxModel(listaProductosEmpacado);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Editar compra", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        compraEmpacado = new MovimientoEmpacado(clave);
                        try {
                            bcompraEmpacado = persistencia.obtenCompra(compraEmpacado);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bcompraEmpacado != null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bcompraEmpacado, productosComboBox, "Edite los datos de la compra empacado", ConstantesGUI.ACTUALIZAR, respuesta);

                            try {
                                persistencia.actualizarCompra(bcompraEmpacado);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        if (bcompraEmpacado == null) {
                            JOptionPane.showMessageDialog(frame, "La compra no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay compras de productos empacados", "Cancelando...", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de actualizar los datos de una venta empacado, se despliega
     * una ventana emergente donde solicitará la clave y si esta coincide con la
     * de una compra previa, podrá actualizarse.
     *
     * @param frame Ventana principal.
     */
    public void actualizarVentaEmpacado(JFrame frame) {
        MovimientoEmpacado ventaEmpacado, bventaEmpacado = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoEmpacado> listaProductosEmpacado = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento = null;
        DefaultComboBoxModel<ProductoEmpacado> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosEmpacados().isEmpty()) {
                listaProductos = persistencia.consultarProductosEmpacados();
                listaProductosEmpacado = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosEmpacado.add(new ProductoEmpacado(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosEmpacadoComboBoxModel(listaProductosEmpacado);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la venta:", "Editar venta", JOptionPane.QUESTION_MESSAGE);
                try {
                    if ((!clave.equals("") && !clave.contains(" "))) {
                        ventaEmpacado = new MovimientoEmpacado(clave);
                        try {
                            bventaEmpacado = persistencia.obtenVenta(ventaEmpacado);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (bventaEmpacado != null) {
                            dlgMovimiento = new DlgMovimiento(frame, true, bventaEmpacado, productosComboBox, "Edite los datos de la venta empacado", ConstantesGUI.ACTUALIZAR, respuesta);

                            try {
                                persistencia.actualizarCompra(bventaEmpacado);
                            } catch (PersistenciaException e) {
                                JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                            }
                        }

                        if (bventaEmpacado == null) {
                            JOptionPane.showMessageDialog(frame, "La venta no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                        if (respuesta.substring(0).equals(ConstantesGUI.CANCELAR)) {
                            dlgMovimiento.dispose();
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Ingrese una clave valida", "Cancelando...", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(frame, "Cancelado con éxito", "Cancelando...", JOptionPane.OK_CANCEL_OPTION);
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay compras de productos empacados", "Cancelando...", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** 
     * Método capaz de actualizar el inventario de las compras a granel, el cual
     * se vera reflejado en las el inventario de productos granel, agregando cantidades.
     * 
     * @param frame Ventana Principal.
     */
    public void actualizarInventarioComprasGranel(JFrame frame) {
        List<MovimientoGranel> compras;
        boolean comprasProcesadas = true;
        try {
            if (!persistencia.consultarComprasGranel().isEmpty()) {
                compras = persistencia.consultarComprasGranel();
                for (MovimientoGranel c : compras) {
                    if (!c.getProcesado()) {
                        comprasProcesadas = false;
                        try {
                            persistencia.actualizarInventarioComprasGranel();
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (comprasProcesadas) {
                    JOptionPane.showMessageDialog(frame, "Todas las compras estan procesadas", "Error!!.", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No hay compras registradas", "Error!!.", JOptionPane.ERROR_MESSAGE);

            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de actualizar el inventario de las ventas a granel, el cual
     * se vera reflejado en las el inventario de productos granel, quitando cantidades.
     * 
     * @param frame Ventana Principal.
     */
    public void actualizarInventarioVentasGranel(JFrame frame) {
        List<MovimientoGranel> ventas;
        boolean ventasProcesadas = true;
        try {
            if (!persistencia.consultarVentasGranel().isEmpty()) {
                ventas = persistencia.consultarVentasGranel();
                for (MovimientoGranel v : ventas) {
                    if (!v.getProcesado()) {
                        ventasProcesadas = false;
                        try {
                            persistencia.actualizarInventarioVentasGranel();
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (ventasProcesadas) {
                    JOptionPane.showMessageDialog(frame, "Todas las ventas estan procesadas", "Error!!.", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No hay ventas registradas", "Error!!.", JOptionPane.ERROR_MESSAGE);

            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de actualizar el inventario de las compras a empacado, el cual
     * se vera reflejado en las el inventario de productos empacado, agregando cantidades.
     * 
     * @param frame Ventana Principal.
     */
    public void actualizarInventarioComprasEmpacado(JFrame frame) {
        List<MovimientoEmpacado> compras;
        boolean comprasProcesadas = true;
        try {
            if (!persistencia.consultarComprasEmpacado().isEmpty()) {
                compras = persistencia.consultarComprasEmpacado();
                for (MovimientoEmpacado c : compras) {
                    if (!c.getProcesado()) {
                        comprasProcesadas = false;
                        try {
                            persistencia.actualizarInventarioComprasEmpacado();
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (comprasProcesadas) {
                    JOptionPane.showMessageDialog(frame, "Todas las compras estan procesadas", "Error!!.", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No hay compras registradas", "Error!!.", JOptionPane.ERROR_MESSAGE);

            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Método capaz de actualizar el inventario de las ventas empacado, el cual
     * se vera reflejado en las el inventario de productos empacados, quiatndo cantidades.
     * 
     * @param frame Ventana Principal.
     */
    public void actualizarInventarioVentasEmapacado(JFrame frame) {
        List<MovimientoEmpacado> ventas;
        boolean ventasProcesadas = true;
        try {
            if (!persistencia.consultarVentasEmpacado().isEmpty()) {
                ventas = persistencia.consultarVentasEmpacado();
                for (MovimientoEmpacado v : ventas) {
                    if (!v.getProcesado()) {
                        ventasProcesadas = false;
                        try {
                            persistencia.actualizarInventarioVentasEmpacado();
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
                if (ventasProcesadas) {
                    JOptionPane.showMessageDialog(frame, "Todas las ventas estan procesadas", "Error!!.", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "No hay ventas registradas", "Error!!.", JOptionPane.ERROR_MESSAGE);

            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    //************************Eliminar***********************
    
    /**
     * Método capaz de eliminar un producto mediante una ventana emergente que, a
     * continuación procederá a solicitar la clave del nuevo producto, si esta
     * clave, al usar el método obtener, si obtiene un producto, el producto
     * ´podra ser eliminado.
     * 
     * @param frame Ventana Principal.
     */
    public void eliminarProducto(JFrame frame) {
        Producto producto = null, encontrado = null;
        StringBuffer respuesta = new StringBuffer("");
        DlgProducto dlgProducto = null;
        ConstantesGUI validar = null;

        //Captura la clave del producto
        String claveProducto = JOptionPane.showInputDialog(
                frame, 
                "Clave del producto:", 
                "Eliminar Producto", 
                JOptionPane.QUESTION_MESSAGE
        );
        
        if (claveProducto == null) {
            return;
        }
        
        producto = new Producto(claveProducto);
              
        try {
            encontrado = persistencia.obten(producto);
            
            if (encontrado == null) {
                throw new PersistenciaException("El producto no se encuentra registrado");
            }
            
            producto = encontrado;
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
            
            return;
        }
        
        dlgProducto = new DlgProducto(
                frame,
                "Eliminar Producto",
                true,
                producto,
                ConstantesGUI.ELIMINAR,
                respuesta
        );
        
        // se cancelo la operacion a traves del dialogo
        if (dlgProducto.respuesta.toString().equals("Cancelar")) {
            //System.out.println("TRUE");
            return;
        }
        
        int opcion = JOptionPane.showConfirmDialog(
                frame, 
                "¿Deseas eliminar el producto?", 
                "Eliminar Producto", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        
        // si no se confimar, cierra el dialogo...
        if (opcion == JOptionPane.NO_OPTION || opcion == JOptionPane.DEFAULT_OPTION) {
            return;
        }
        
        try {
            persistencia.eliminar(producto);
            
            JOptionPane.showMessageDialog(
                frame,
                String.format("Producto eliminado [CODIGO: %s]", producto.getClave()),
                "Operacion Completada",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            
        } catch (PersistenciaException ex) {
            JOptionPane.showMessageDialog(
                frame,
                ex.getMessage(),
                "Error - Eliminar Producto",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    /**
     * Método capaz de eliminar un compra a granel mediante una ventana
     * emergente que, a continuación procederá a solicitar la clave de la
     * compra, si esta clave, al usar el método obtener, si obtiene una compra,
     * la compra podra ser eliminada.
     *
     * @param frame Ventana Principal.
     */
    public void eliminarCompraGranel(JFrame frame) {
        MovimientoGranel compraGranel, bcompraGranel = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoGranel> listaProductosGranel = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento;
        DefaultComboBoxModel<ProductoGranel> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosGranel().isEmpty()) {
                listaProductos = persistencia.consultarProductosGranel();
                listaProductosGranel = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosGranel.add(new ProductoGranel(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosGranelComboBoxModel(listaProductosGranel);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Agregar compra", JOptionPane.QUESTION_MESSAGE);
                if (!clave.equals(null)) {
                    compraGranel = new MovimientoGranel(clave);
                    try {
                        bcompraGranel = persistencia.obtenCompra(compraGranel);
                    } catch (PersistenciaException e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                    if (bcompraGranel != null) {
                        try {
                            persistencia.eliminarCompra(compraGranel);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (bcompraGranel == null) {
                        JOptionPane.showMessageDialog(frame, "La compra no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** 
     * Método capaz de eliminar un venta a granel mediante una ventana
     * emergente que, a continuación procederá a solicitar la clave de la
     * venta, si esta clave, al usar el método obtener, si obtiene una venta,
     * la venta podra ser eliminada.
     * 
     * @param frame 
     */
    public void eliminarVentaGranel(JFrame frame) {
        MovimientoGranel ventaGranel, bventaGranel = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoGranel> listaProductosGranel = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento;
        DefaultComboBoxModel<ProductoGranel> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosGranel().isEmpty()) {
                listaProductos = persistencia.consultarProductosGranel();
                listaProductosGranel = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosGranel.add(new ProductoGranel(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosGranelComboBoxModel(listaProductosGranel);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la venta:", "Agregar venta", JOptionPane.QUESTION_MESSAGE);
                if (!clave.equals(null)) {
                    ventaGranel = new MovimientoGranel(clave);
                    try {
                        bventaGranel = persistencia.obtenVenta(ventaGranel);
                    } catch (PersistenciaException e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                    if (bventaGranel != null) {
                        try {
                            persistencia.eliminarVenta(ventaGranel);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (bventaGranel == null) {
                        JOptionPane.showMessageDialog(frame, "La venta no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** 
     * Método capaz de eliminar un compra empacado mediante una ventana
     * emergente que, a continuación procederá a solicitar la clave de la
     * compra, si esta clave, al usar el método obtener, si obtiene una compra,
     * la compra podra ser eliminada.
     * 
     * @param frame ventana principal.
     */
    public void eliminarCompraEmpacado(JFrame frame) {
        MovimientoEmpacado compraEmpacado, bcompraEmpacado = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoEmpacado> listaProductosEmpacados = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento;
        DefaultComboBoxModel<ProductoEmpacado> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosEmpacados().isEmpty()) {
                listaProductos = persistencia.consultarProductosEmpacados();
                listaProductosEmpacados = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosEmpacados.add(new ProductoEmpacado(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosEmpacadoComboBoxModel(listaProductosEmpacados);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Agregar compra", JOptionPane.QUESTION_MESSAGE);
                if (!clave.equals(null)) {
                    compraEmpacado = new MovimientoEmpacado(clave);
                    try {
                        bcompraEmpacado = persistencia.obtenCompra(compraEmpacado);
                    } catch (PersistenciaException e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                    if (bcompraEmpacado != null) {
                        try {
                            persistencia.eliminarCompra(compraEmpacado);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (bcompraEmpacado == null) {
                        JOptionPane.showMessageDialog(frame, "La compra no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** 
     * Método capaz de eliminar un venta empacado mediante una ventana
     * emergente que, a continuación procederá a solicitar la clave de la
     * venta, si esta clave, al usar el método obtener, si obtiene una venta,
     * la venta podra ser eliminada.
     * 
     * @param frame 
     */
    public void eliminarVentaEmpacado(JFrame frame) {
        MovimientoEmpacado ventaEmpacado, bventaEmpacado = null;
        StringBuffer respuesta = new StringBuffer("");
        List<ProductoEmpacado> listaProductosEmpacados = null;
        List<Producto> listaProductos = null;
        DlgMovimiento dlgMovimiento;
        DefaultComboBoxModel<ProductoEmpacado> productosComboBox = null;

        try {
            if (!persistencia.consultarProductosEmpacados().isEmpty()) {
                listaProductos = persistencia.consultarProductosEmpacados();
                listaProductosEmpacados = new ArrayList<>();
                for (int i = 0; i < listaProductos.size(); i++) {
                    listaProductosEmpacados.add(new ProductoEmpacado(listaProductos.get(i)));
                }
                productosComboBox = conversiones.productosEmpacadoComboBoxModel(listaProductosEmpacados);
                String clave = JOptionPane.showInputDialog(frame, "Clave de la compra:", "Agregar compra", JOptionPane.QUESTION_MESSAGE);
                if (!clave.equals(null)) {
                    ventaEmpacado = new MovimientoEmpacado(clave);
                    try {
                        bventaEmpacado = persistencia.obtenVenta(ventaEmpacado);
                    } catch (PersistenciaException e) {
                        JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                    if (bventaEmpacado != null) {
                        try {
                            persistencia.eliminarVenta(ventaEmpacado);
                        } catch (PersistenciaException e) {
                            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    if (bventaEmpacado == null) {
                        JOptionPane.showMessageDialog(frame, "La compra no existe", "Error!!.", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Regresa un objeto Tabla con todos los productos.
     *
     * @param frame Ventana sobre la que se despliega.
     * @return Objeto Tabla con todas los productos.
     */ 
    public Tabla getTablaProductos(JFrame frame) {
        List<Producto> listaproductos = null;
        try {
            listaproductos = persistencia.consultarProductos();
            
            /*
            int i=1;
            for (Producto producto: listaproductos) {
                System.out.println(i + "# " + producto);
                i++;
            }
            */
                
            
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        //String titulo = String.format("Lista de Productos [%d / %d]", listaproductos.size(), persistencia.consultar);
                
        
        return new Tabla("Lista de Productos", conversiones.productosTableModel(listaproductos));
    }

    
    /**
     * Regresa un objeto Tabla con todos los productos granel.
     *
     * @param frame Ventana sobre la que se despliega.
     * @return Objeto Tabla con todas los productos.
     */
    public Tabla getTablaProductosGranel(JFrame frame) {
        List<Producto> listaproductosGranel = null;
        try {
            listaproductosGranel = persistencia.consultarProductosGranel();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        return new Tabla(
                String.format("Lista de Productos Granel [ Resultados: %d ]", listaproductosGranel.size()),
                conversiones.productosTableModel(listaproductosGranel)
        );
        //return new Tabla("Lista de productos Productos Granel", conversiones.productosTableModel(listaproductosGranel));
    }

    /**
     * Regresa un objeto Tabla con todos los productos empacados.
     *
     * @param frame Ventana sobre la que se despliega.
     * @return Objeto Tabla con todas los productos.
     */
    public Tabla getTablaProductosEmpacados(JFrame frame) {
        List<Producto> listaproductosEmpacados = null;
        try {
            listaproductosEmpacados = persistencia.consultarProductosEmpacados();
        } catch (PersistenciaException e) {
            return new Tabla("", conversiones.productosTableModel(listaproductosEmpacados));

        }

        return new Tabla(
                String.format("Lista de Productos Granel [ Resultados: %d ]", listaproductosEmpacados.size()),
                conversiones.productosTableModel(listaproductosEmpacados)
        );
    }

    /**
     * Regresa un objeto Tabla con todas las compras granel,
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las compras.
     */
    public Tabla getTablaComprasProductosGranel(JFrame frame) {
        List<MovimientoGranel> listaComprasG = null;
        try {
            listaComprasG = persistencia.consultarComprasGranel();
        } catch (PersistenciaException e) {

            return new Tabla("", conversiones.movimientosGranelTableModel(listaComprasG));
        }
        return new Tabla("Lista de compras Productos Granel", conversiones.movimientosGranelTableModel(listaComprasG));
    }

    /**
     * Regresa un objeto Tabla con todas las compras granel,
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las compras.
     */
    public Tabla getTablaComprasProductosGranel2(JFrame frame) {
        List<MovimientoGranel> listaComprasG = null;
        try {
            listaComprasG = persistencia.consultarComprasGranel();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return new Tabla("Lista de compras Productos Granel", conversiones.movimientosGranelTableModel(listaComprasG));
    }

    /**
     * Regresa un objeto Tabla con todas las compras granel por periodo,
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las compras.
     */
    public Tabla getTablaComprasProductosGranelPeriodo(JFrame frame) {
        StringBuffer respuesta = new StringBuffer("");
        DlgPeriodo dlgPeriodo;
        Periodo prd = new Periodo(new Fecha(), new Fecha());

        List<MovimientoGranel> listaProductosGranel = null;

        try {
            if (!(persistencia.consultarComprasGranel().isEmpty())) {
                dlgPeriodo = new DlgPeriodo(frame, true, prd, ConstantesGUI.AGREGAR, respuesta);
                listaProductosGranel = persistencia.consultarComprasGranel(prd);
                return new Tabla("Lista de compras Productos Granel Periodo", conversiones.movimientosGranelTableModel(listaProductosGranel));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "No hay compras en este periodo", "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return null;
    }

    /**
     * Regresa un objeto Tabla con todas las compras empacados,
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las compras.
     */
    public Tabla getTablaComprasProductosEmpacados(JFrame frame) {
        List<MovimientoEmpacado> listaComprasE = null;
        try {
            listaComprasE = persistencia.consultarComprasEmpacado();
        } catch (PersistenciaException e) {
            return new Tabla("", conversiones.movimientosEmpacadoTableModel(listaComprasE));

        }

        return new Tabla("Lista de Compras Productos Empacados", conversiones.movimientosEmpacadoTableModel(listaComprasE));
    }

    /**
     * Regresa un objeto Tabla con todas las compras empacados,
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las compras.
     */
    public Tabla getTablaComprasProductosEmpacados2(JFrame frame) {
        List<MovimientoEmpacado> listaComprasE = null;
        try {
            listaComprasE = persistencia.consultarComprasEmpacado();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        return new Tabla("Lista de Compras Productos Empacados", conversiones.movimientosEmpacadoTableModel(listaComprasE));
    }

    /**
     * Regresa un objeto Tabla con todas las compras empacados por periodo.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las compras.
     */
    public Tabla getTablaComprasProductosEmpacadosPeriodo(JFrame frame) {
        StringBuffer respuesta = new StringBuffer("");
        DlgPeriodo dlgPeriodo;
        Periodo prd = new Periodo(new Fecha(), new Fecha());

        List<MovimientoEmpacado> listaProductosEmpacado = null;

        try {
            if (!(persistencia.consultarComprasEmpacado().isEmpty())) {
                dlgPeriodo = new DlgPeriodo(frame, true, prd, ConstantesGUI.AGREGAR, respuesta);
                //prd =new Periodo (dlgPeriodo.desde, dlgPeriodo.hasta);
                listaProductosEmpacado = persistencia.consultarComprasEmpacado(prd);

                return new Tabla("Lista de compras Productos Empacado Periodo", conversiones.movimientosEmpacadoTableModel(listaProductosEmpacado));
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay compras en este periodo", "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return null;
    }

    /**
     * Regresa un objeto Tabla con todas las ventas empacados.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las ventas.
     */
    public Tabla getTablaVentasProductosEmpacados(JFrame frame) {
        List<MovimientoEmpacado> listaVentasE = null;
        try {
            listaVentasE = persistencia.consultarVentasEmpacado();
        } catch (PersistenciaException e) {
            return new Tabla("", conversiones.movimientosEmpacadoTableModel(listaVentasE));

        }
        return new Tabla("Lista de Ventas Productos Empacados", conversiones.movimientosEmpacadoTableModel(listaVentasE));
    }

    /**
     * Regresa un objeto Tabla con todas las ventas empacados.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las ventas.
     */
    public Tabla getTablaVentasProductosEmpacados2(JFrame frame) {
        List<MovimientoEmpacado> listaVentasE = null;
        try {
            listaVentasE = persistencia.consultarVentasEmpacado();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return new Tabla("Lista de Ventas Productos Empacados", conversiones.movimientosEmpacadoTableModel(listaVentasE));
    }

    /**
     * Regresa un objeto Tabla con todas las ventas empacados por periodo.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las ventas.
     */
    public Tabla getTablaVentasProductosEmpacadosPeriodo(JFrame frame) {
        StringBuffer respuesta = new StringBuffer("");
        DlgPeriodo dlgPeriodo;
        Periodo prd = new Periodo(new Fecha(), new Fecha());

        List<MovimientoEmpacado> listaProductosEmpacados = null;

        try {
            if (!(persistencia.consultarVentasEmpacado().isEmpty())) {
                dlgPeriodo = new DlgPeriodo(frame, true, prd, ConstantesGUI.AGREGAR, respuesta);
                //prd =new Periodo (dlgPeriodo.desde, dlgPeriodo.hasta);
                listaProductosEmpacados = persistencia.consultarVentasEmpacado(prd);

                return new Tabla("Lista de ventas Productos Granel Periodo", conversiones.movimientosEmpacadoTableModel(listaProductosEmpacados));
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay ventas en este periodo", "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return null;
    }

    /**
     * Regresa un objeto Tabla con todas las ventas granel por periodo.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las ventas.
     */
    public Tabla getTablaVentasProductosGranelPeriodo(JFrame frame) {
        StringBuffer respuesta = new StringBuffer("");
        DlgPeriodo dlgPeriodo = null;
        Periodo prd = new Periodo(new Fecha(), new Fecha());

        List<MovimientoGranel> listaProductosGranel = null;

        try {
            if (!(persistencia.consultarVentasGranel().isEmpty())) {
                dlgPeriodo = new DlgPeriodo(frame, true, prd, ConstantesGUI.AGREGAR, respuesta);
                //prd =new Periodo (dlgPeriodo.desde, dlgPeriodo.hasta);
                listaProductosGranel = persistencia.consultarVentasGranel(prd);

                return new Tabla("Lista de ventas Productos Granel Periodo", conversiones.movimientosGranelTableModel(listaProductosGranel));
            }
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, "No hay ventas en este periodo", "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return null;
    }

    /**
     * Regresa un objeto Tabla con todas las ventas granel.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las ventas.
     */
    public Tabla getTablaVentasProductosGranel(JFrame frame) {
        List<MovimientoGranel> listaVentasG = null;
        try {
            listaVentasG = persistencia.consultarVentasGranel();
        } catch (PersistenciaException e) {
            return new Tabla("", conversiones.movimientosGranelTableModel(listaVentasG));

        }
        return new Tabla("Lista de Ventas Productos granel", conversiones.movimientosGranelTableModel(listaVentasG));
    }

    /**
     * Regresa un objeto Tabla con todas las ventas granel.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todas las ventas.
     */
    public Tabla getTablaVentasProductosGranel2(JFrame frame) {
        List<MovimientoGranel> listaVentasG = null;
        try {
            listaVentasG = persistencia.consultarVentasGranel();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }
        return new Tabla("Lista de Ventas Productos granel", conversiones.movimientosGranelTableModel(listaVentasG));
    }

    /**
     * Regresa un objeto Tabla con todas los productos granel y sus cantidades.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todos los productos de granel.
     */
    public Tabla getTablaInventarioProductosGranel(JFrame frame) {
        List<ProductoGranel> listaInventariosG = null;
        try {
            listaInventariosG = persistencia.consultarInventarioProductosGranel();
        } catch (PersistenciaException e) {
            return new Tabla("", conversiones.productosGranelTableModel(listaInventariosG));

        }

        return new Tabla("Inventario Productos Granel", conversiones.productosGranelTableModel(listaInventariosG));

    }

    /**
     * Regresa un objeto Tabla con todas los productos granel y sus cantidades.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todos los productos de granel.
     */
    public Tabla getTablaInventarioProductosGranel2(JFrame frame) {
        List<ProductoGranel> listaInventariosG = null;
        try {
            listaInventariosG = persistencia.consultarInventarioProductosGranel();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        return new Tabla("Inventario Productos Granel", conversiones.productosGranelTableModel(listaInventariosG));

    }

    /**
     * Regresa un objeto Tabla con todas los productos empacado y sus cantidades.
     *
     * @param frame Ventana sobre la que se despliega
     * @return Objeto Tabla con todos los productos de empacado.
     */
    public Tabla getTablaInventarioProductosEmpacados(JFrame frame) {
        List<ProductoEmpacado> listaInventariosE = null;
        try {
            listaInventariosE = persistencia.consultarInventarioProductosEmpacados();
        } catch (PersistenciaException e) {
            return new Tabla("", conversiones.productosEmpacadoTableModel(listaInventariosE));

        }

        return new Tabla("Inventario Productos Granel", conversiones.productosEmpacadoTableModel(listaInventariosE));

    }

    /**
     * Regresa un objeto Tabla con todas los productos empacado y sus cantidades.
     *
     * @param frame Ventana sobre la que se despliega,
     * @return Objeto Tabla con todos los productos de empacado.
     */
    public Tabla getTablaInventarioProductosEmpacados2(JFrame frame) {
        List<ProductoEmpacado> listaInventariosE = null;
        try {
            listaInventariosE = persistencia.consultarInventarioProductosEmpacados();
        } catch (PersistenciaException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Error!!.", JOptionPane.ERROR_MESSAGE);
            return null;

        }

        return new Tabla("Inventario Productos Granel", conversiones.productosEmpacadoTableModel(listaInventariosE));
    }
}
