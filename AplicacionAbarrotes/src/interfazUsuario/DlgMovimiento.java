/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package interfazUsuario;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import objetosNegocio.MovimientoEmpacado;
import objetosNegocio.MovimientoGranel;
import objetosNegocio.ProductoEmpacado;
import objetosNegocio.ProductoGranel;
import objetosServicio.Fecha;
import objetosServicio.Periodo;

/**
 *
 * @author Jesus 
 */
public class DlgMovimiento extends javax.swing.JDialog {

    private MovimientoEmpacado movimientoEmpacado;
    private MovimientoGranel movimientoGranel;
    private DefaultComboBoxModel listaProductos;
    private String tituloMovimiento;
    private int operacion;
    private StringBuffer respuesta;
    private boolean caliz = true;

    /**
     * Constructor que inicializa los atributos del cuadro de dialogo, sobre
     * movimientos y productos empacados.
     */
    public DlgMovimiento(java.awt.Frame parent, boolean modal, MovimientoEmpacado movimientoEmpacado, DefaultComboBoxModel listaProductos, String tituloMovimiento, int operacion, StringBuffer respuesta) {
        super(parent,tituloMovimiento, modal);
        this.movimientoEmpacado = movimientoEmpacado;
        this.listaProductos = listaProductos;
        this.tituloMovimiento = tituloMovimiento;
        this.operacion = operacion;
        this.respuesta = respuesta;
        caliz = false;

        initComponents();
        
        //centra el cuadro de dialogo sobre la ventana de la aplicacion
        centraCuadroDialogo(parent);
        setResizable(false);
        
        // Modifica el título del botón botonAceptar y establece si los
        // botones botonRestaurar y botonCancelar estarán habilitados.
        // Si la operación es agregar
        if (operacion == ConstantesGUI.AGREGAR) {
            botonAceptar.setText("Guardar");
        } // Si la operación es actualizar
        else if (operacion == ConstantesGUI.ACTUALIZAR) {
            botonAceptar.setText("Actualizar");
        } // Si la operación es eliminar
        else if (operacion == ConstantesGUI.ELIMINAR) {
            botonAceptar.setText("Eliminar");
            botonRestaurar.setEnabled(false);
        } // Si la operación es desplegar
        else if (operacion == ConstantesGUI.DESPLEGAR) {
            botonAceptar.setText("Continuar");
            botonRestaurar.setEnabled(false);
            botonCancelar.setEnabled(false);
        }

        //Despliega la clave de movimiento
        campoTextoClaveMov.setText(movimientoEmpacado.getCveMovimiento());
        campoTextoClaveMov.setEditable(false);

        //Si la operación es de actualizar, eliminar o desplegar
        if ((operacion == ConstantesGUI.ELIMINAR) || (operacion == ConstantesGUI.ACTUALIZAR) || (operacion == ConstantesGUI.DESPLEGAR)) {
            //se despliega la info del movimiento
            campoTextoCantidad.setText(Integer.toString(movimientoEmpacado.getProductoEmpacado().getCantidad()));
            campoTextoFecha.setText(movimientoEmpacado.getFecha().toString());
            cajaCombinadaProductos.setSelectedItem(movimientoEmpacado.getProductoEmpacado());
        }

        //Si la operacion es eliminar o desplegar
        if ((operacion == ConstantesGUI.ELIMINAR) || (operacion == ConstantesGUI.DESPLEGAR)) {
            //Muestra los campos de lectura
            campoTextoCantidad.setEditable(false);
            campoTextoFecha.setEditable(false);
            cajaCombinadaProductos.setEditable(false);
        }

        // Establece el valor por omisión para respuesta, por si se cierra el
        // cuadro de diálogo presionando el botón cerrar o el botón cancelar
        respuesta.append(ConstantesGUI.CANCELAR);

        //Muestra el cuadro de dialogo
        setVisible(true);
    }

    /**
     * Constructor que inicializa los atributos del cuadro de dialogo, sobre
     * movimientos y productos granel.
     */
    public DlgMovimiento(java.awt.Frame parent, boolean modal, MovimientoGranel movimientoGranel, DefaultComboBoxModel listaProductos, String tituloMovimiento, int operacion, StringBuffer respuesta) {
        super(parent,tituloMovimiento, modal);
        this.movimientoGranel = movimientoGranel;
        this.listaProductos = listaProductos;
        this.tituloMovimiento = tituloMovimiento;
        this.operacion = operacion;
        this.respuesta = respuesta;
        caliz = true;

        initComponents();
        
        //centra el cuadro de dialogo sobre la ventana de la aplicacion
        centraCuadroDialogo(parent);

        // Modifica el título del botón botonAceptar y establece si los
        // botones botonRestaurar y botonCancelar estarán habilitados.
        // Si la operación es agregar
        if (operacion == ConstantesGUI.AGREGAR) {
            botonAceptar.setText("Guardar");
        } // Si la operación es actualizar
        else if (operacion == ConstantesGUI.ACTUALIZAR) {
            botonAceptar.setText("Actualizar");
        } // Si la operación es eliminar
        else if (operacion == ConstantesGUI.ELIMINAR) {
            botonAceptar.setText("Eliminar");
            botonRestaurar.setEnabled(false);
        } // Si la operación es desplegar
        else if (operacion == ConstantesGUI.DESPLEGAR) {
            botonAceptar.setText("Continuar");
            botonRestaurar.setEnabled(false);
            botonCancelar.setEnabled(false);
        }

        //Despliega la clave de movimiento
        campoTextoClaveMov.setText(movimientoGranel.getCveMovimiento());
        campoTextoClaveMov.setEditable(false);

        //Si la operación es de actualizar, eliminar o desplegar
        if ((operacion == ConstantesGUI.ELIMINAR) || (operacion == ConstantesGUI.ACTUALIZAR) || (operacion == ConstantesGUI.DESPLEGAR)) {
            //se despliega la info del movimiento
            campoTextoCantidad.setText(Float.toString(movimientoGranel.getProductoGranel().getCantidad()));
            campoTextoFecha.setText(movimientoGranel.getFecha().toString());
            cajaCombinadaProductos.setSelectedItem(movimientoGranel.getProductoGranel());
        }

        //Si la operacion es eliminar o desplegar
        if ((operacion == ConstantesGUI.ELIMINAR) || (operacion == ConstantesGUI.DESPLEGAR)) {
            //Muestra los campos de lectura
            campoTextoClaveMov.setEditable(false);
            campoTextoCantidad.setEditable(false);
            campoTextoFecha.setEditable(false);
            cajaCombinadaProductos.setEditable(false);
        }

        // Establece el valor por omisión para respuesta, por si se cierra el
        // cuadro de diálogo presionando el botón cerrar o el botón cancelar
        respuesta.append(ConstantesGUI.CANCELAR);

        //Muestra el cuadro de dialogo
        setVisible(true);
    }

    /**
     * Este método centra el cuadro de dialogo sobre la ventana de la
     * aplicación.
     *
     * @param parent Ventana sobre la que aparecerá el cuadro de diálogo
     */
    public void centraCuadroDialogo(java.awt.Frame parent) {
        //Obtiene el tamaño de la pantalla
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // Obtiene el tamaño de la ventana de la aplicación
        Dimension frameSize = getSize();

        // Se asegura que el tamaño de la ventana de la aplicación
        // no exceda el tamaño de la pantalla
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }

        // Centra la ventana de la aplicación sobre la pantalla
        setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /** 
     * Metodo que valida si los campos estan llenos.
     * @return verdadero o falso.
     */
    public boolean validarCampos() {
        if (campoTextoCantidad.getText().equals("")) {
            return false;
        }
        if (campoTextoClaveMov.getText().equals("")) {
            return false;

        }
        if (campoTextoFecha.getText().equals("")) {
            return false;
        }
        return true;
    }
        private boolean validarPunto() {
        if (campoTextoCantidad.getText().contains(".")) {
            int puntos = 0;
            for (int i = 0; i < campoTextoCantidad.getText().length(); i++) {
                if (campoTextoCantidad.getText().charAt(i) == '.') {
                    puntos++;
                    if (puntos == 2) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return true;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoTextoCantidad = new javax.swing.JTextField();
        campoTextoFecha = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonRestaurar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cajaCombinadaProductos = new javax.swing.JComboBox<>();
        campoTextoClaveMov = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setText("Cantidad:");

        jLabel3.setText("Fecha:");

        campoTextoCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoTextoCantidadKeyTyped(evt);
            }
        });

        botonAceptar.setText("Aceptar");
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        botonRestaurar.setText("Restaurar");
        botonRestaurar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonRestaurarActionPerformed(evt);
            }
        });

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        jLabel4.setText("Clave:");

        jLabel1.setText("Catalogo:");

        cajaCombinadaProductos.setModel(listaProductos);
        cajaCombinadaProductos.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        cajaCombinadaProductos.setFocusCycleRoot(true);
        cajaCombinadaProductos.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cajaCombinadaProductos, 0, 222, Short.MAX_VALUE)
                    .addComponent(campoTextoClaveMov)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoTextoClaveMov, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cajaCombinadaProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGap(32, 32, 32)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(campoTextoCantidad)
                                    .addComponent(campoTextoFecha)))
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 73, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(botonCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonRestaurar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonAceptar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(campoTextoCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTextoFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar)
                    .addComponent(botonAceptar)
                    .addComponent(botonRestaurar))
                .addGap(8, 8, 8))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /** 
     * Metodo oyente del boton aceptar.
     * @param evt evento al que escuchara.
     */
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        if ((operacion == ConstantesGUI.AGREGAR) || (operacion == ConstantesGUI.ACTUALIZAR)) {
            if (validarCampos()) {
                if (campoTextoFecha.getText().matches("\\d{2}/\\d{2}/\\d{4}")) {
                    if (movimientoEmpacado != null) {
                        if (cajaCombinadaProductos.getSelectedItem() instanceof ProductoEmpacado) {
                            movimientoEmpacado.setFecha(new Fecha(campoTextoFecha.getText()));
                        }
                        movimientoEmpacado.setProcesado(false);
                        movimientoEmpacado.setProductoEmpacado((ProductoEmpacado) cajaCombinadaProductos.getSelectedItem());
                        movimientoEmpacado.getProductoEmpacado().setCantidad(Integer.parseInt(campoTextoCantidad.getText()));
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Ingrese la fecha en el formato:(dd/mm/aaaa) ", "Error!!.", JOptionPane.WARNING_MESSAGE);
                    campoTextoFecha.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Asegurese de rellenar todos los campos", "Error!!.", JOptionPane.WARNING_MESSAGE);
            }
            if (validarCampos()) {
                if(validarPunto()){
                if (movimientoGranel != null) {
                    if (cajaCombinadaProductos.getSelectedItem() instanceof ProductoGranel) {
                        movimientoGranel.setFecha(new Fecha(campoTextoFecha.getText()));
                        movimientoGranel.setProcesado(false);
                        movimientoGranel.setProductoGranel((ProductoGranel) cajaCombinadaProductos.getSelectedItem());
                        movimientoGranel.getProductoGranel().setCantidad(Float.parseFloat(campoTextoCantidad.getText()));
                    }
                }
            } else{
                    JOptionPane.showMessageDialog(this, "Solo puede ingresar un punto decimal", "Error!!.", JOptionPane.WARNING_MESSAGE);
                    campoTextoCantidad.setText("");
                }
            }
        }
        //Borra el contenido de la respuesta
        respuesta.delete(0, respuesta.length());

        //Establece que se presiono el boton botonAceptar
        respuesta.append(ConstantesGUI.ACEPTAR);

        if (validarCampos()) {
            dispose();
        }
        if (operacion == ConstantesGUI.DESPLEGAR) {
            dispose();
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    /** 
     * Metodo oyente del boton restaurar.
     * @param evt evento al que escuchara.
     */
    private void botonRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRestaurarActionPerformed
        // Restaura el contenido de los campos de texto a su valor original
        // Si la operación es Agregar
        if (operacion == ConstantesGUI.AGREGAR) {
            campoTextoCantidad.setText("");
            campoTextoFecha.setText("");
            cajaCombinadaProductos.setSelectedIndex(0);
        }

        // Si la operación es Actualizar
        if (operacion == ConstantesGUI.ACTUALIZAR) {
            if (cajaCombinadaProductos.getSelectedItem() instanceof ProductoEmpacado) {
                campoTextoCantidad.setText(String.valueOf(movimientoEmpacado.getProductoEmpacado().getCantidad()));
                campoTextoFecha.setText(movimientoEmpacado.getFecha().toString());
                cajaCombinadaProductos.setSelectedItem(movimientoEmpacado.getProductoEmpacado());
            } else if (cajaCombinadaProductos.getSelectedItem() instanceof ProductoGranel) {
                campoTextoCantidad.setText(String.valueOf(movimientoGranel.getProductoGranel().getCantidad()));
                campoTextoFecha.setText(movimientoGranel.getFecha().toString());
                cajaCombinadaProductos.setSelectedItem(movimientoGranel.getProductoGranel());
            }
        }
    }//GEN-LAST:event_botonRestaurarActionPerformed

    /** 
     * Metodo oyente del boton cancelar.
     * @param evt evento al que escuchara.
     */
    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        //Destruye el cuadro de dialogo
        dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    /** 
     * Metodo oyente el cual validara que en el campo de texto en la cantidad, si es granel
     * permitira solo numeros y puntos, si es empacados, solo numeros.
     * @param evt evento al que escuchara.
     */
    private void campoTextoCantidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoTextoCantidadKeyTyped
        if (!caliz) {
            char c = evt.getKeyChar();
            if (!Character.isDigit(c)) {
                evt.consume();
            }
        } else {
            char c = evt.getKeyChar();
            if (!Character.isDigit(c) && c != '.') {
                evt.consume(); // Cancelar el evento de teclado si no es un número o un punto
            }
        }

     }//GEN-LAST:event_campoTextoCantidadKeyTyped

/**
 * @param args the command line arguments
 */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonRestaurar;
    private javax.swing.JComboBox<String> cajaCombinadaProductos;
    private javax.swing.JTextField campoTextoCantidad;
    private javax.swing.JTextField campoTextoClaveMov;
    private javax.swing.JTextField campoTextoFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
