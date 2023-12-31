
package interfazUsuario;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import objetosNegocio.Producto;

/**
 *
 * @author EquipoSiete
 */
public class DlgProducto extends javax.swing.JDialog {

    private Producto producto;
    private int operacion;
    private StringBuffer respuesta;

    /**
     * Constructor para el cuadro de dialogo DlgProducto.
     */
    public DlgProducto(java.awt.Frame parent, String title, boolean modal, Producto producto, int operacion, StringBuffer respuesta) {
        super(parent, title, modal);
        this.producto = producto;
        this.operacion = operacion;
        this.respuesta = respuesta;
        
        initComponents();
        
        // centra el cuadro de dialogo sobre la ventana de la aplicación
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

        // Despliega la clave del producto
        campoTextoClave.setText(producto.getClave());
        campoTextoClave.setEditable(false);

        // Si la operación es de actualizar, eliminar o desplegar,
        if ((operacion == ConstantesGUI.ELIMINAR)
                || (operacion == ConstantesGUI.ACTUALIZAR)
                || (operacion == ConstantesGUI.DESPLEGAR)) {
            // despliega el resto de los datos del producto
            campoTextoNombre.setText(producto.getNombre());
            campoTextoTipo.setText(String.valueOf(producto.getTipo()));
            campoTextoUnidad.setText(producto.getUnidad());
        }
        // Si la operación es de eliminar o desplegar
        if ((operacion == ConstantesGUI.ELIMINAR)
                || (operacion == ConstantesGUI.DESPLEGAR)) {
            // hace los campos de texto de sólo lectura
            campoTextoNombre.setEditable(false);
            campoTextoTipo.setEditable(false);
            campoTextoUnidad.setEditable(false);
        }
        // Establece el valor por omisión para respuesta, por si se cierra el
        // cuadro de diálogo presionando el botón cerrar o el botón cancelar
        respuesta.append(ConstantesGUI.CANCELAR);
        // Muestra el cuadro de diálogo
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
     * Metodo que valida si los campos estan vacios.
     *
     * @return verdadero o falso.
     */
    public boolean validarCampos() {
        if (campoTextoNombre.getText().equals("")) {
            return false;
        }
        if (campoTextoTipo.getText().equals("")) {
            return false;
        }
        if (campoTextoUnidad.getText().equals("")) {
            return false;
        }
        return true;
    }

    public boolean validarTipo() {
        if (campoTextoTipo.getText().equalsIgnoreCase("E") || campoTextoTipo.getText().equalsIgnoreCase("G")) {
            return true;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        campoTextoClave = new javax.swing.JTextField();
        campoTextoNombre = new javax.swing.JTextField();
        campoTextoTipo = new javax.swing.JTextField();
        campoTextoUnidad = new javax.swing.JTextField();
        botonAceptar = new javax.swing.JButton();
        botonRestaurar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Clave:");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Nombre:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Tipo:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Unidad:");

        campoTextoClave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        campoTextoNombre.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        campoTextoTipo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        campoTextoUnidad.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonRestaurar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonAceptar)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(campoTextoNombre)
                    .addComponent(campoTextoClave)
                    .addComponent(campoTextoUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTextoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoTextoClave, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoTextoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(campoTextoTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(campoTextoUnidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonAceptar)
                    .addComponent(botonRestaurar)
                    .addComponent(botonCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Método oyente del botón botonCancelar
     *
     * @param evt Evento al que escucha
     */
    private void botonRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRestaurarActionPerformed
        // Restaura el contenido de los campos de texto a su valor original
        // Si la operación es Agregar
        if (operacion == ConstantesGUI.AGREGAR) {
            campoTextoClave.setEditable(false);
            campoTextoNombre.setText("");
            campoTextoTipo.setText("");
            campoTextoUnidad.setText("");
        }
        // Si la operación es Actualizar
        if (operacion == ConstantesGUI.ACTUALIZAR) {
            campoTextoClave.setEditable(false);
            campoTextoNombre.setText(producto.getNombre());
            campoTextoTipo.setText(String.valueOf(producto.getTipo()));
            campoTextoUnidad.setText(producto.getUnidad());
        }
    }//GEN-LAST:event_botonRestaurarActionPerformed

    /**
     * Metodo oyente del boton aceptar
     *
     * @param evt Evento al que escucha
     */
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        if (operacion == ConstantesGUI.AGREGAR || operacion == ConstantesGUI.ACTUALIZAR) {
            if (validarCampos()) {
                if (validarTipo()) {
                    producto.setNombre(campoTextoNombre.getText());
                    producto.setTipo((campoTextoTipo.getText()).toUpperCase().charAt(0));
                    producto.setUnidad(campoTextoUnidad.getText());
                } else {
                    JOptionPane.showMessageDialog(this, "Asegurese de ingresar un tipo valido E o G", "Error!!.", JOptionPane.WARNING_MESSAGE);
                    campoTextoTipo.setText("");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Asegurese de rellenar todos los campos", "Error!!.", JOptionPane.WARNING_MESSAGE);
            }
            respuesta.delete(0, respuesta.length());
            respuesta.append(ConstantesGUI.ACEPTAR);
            if (validarCampos()) {
                dispose();
            }
        }
        if (operacion == ConstantesGUI.DESPLEGAR) {
            dispose();
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    /**
     * Meotodo oyente del boton cancelar.
     *
     * @param evt evento al que esucha.
     */
    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

        dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed
    private javax.swing.JTable jtabla;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonRestaurar;
    private javax.swing.JTextField campoTextoClave;
    private javax.swing.JTextField campoTextoNombre;
    private javax.swing.JTextField campoTextoTipo;
    private javax.swing.JTextField campoTextoUnidad;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
