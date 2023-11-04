package interfazUsuario;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import javax.swing.JOptionPane;
import objetosServicio.Periodo;
import objetosServicio.Fecha;

/**
 *
 * @author Equipo Siete
 */
public class DlgPeriodo extends javax.swing.JDialog {

    public Periodo periodo;
    private int operacion;
    private StringBuffer respuesta;

    /**
     * Constructor de la clase DlgPeriodo.
     */
    public DlgPeriodo(java.awt.Frame parent, boolean modal, Periodo periodo, int operacion, StringBuffer respuesta) {
        super(parent, modal);
        this.operacion = operacion;
        this.respuesta = respuesta;
        this.periodo = periodo;

        initComponents();
        
        //centra el cuadro de dialogo sobre la ventana de la aplicacion
        centraCuadroDialogo(parent);

        setVisible(true);
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

        // Establece el valor por omisión para respuesta, por si se cierra el
        // cuadro de diálogo presionando el botón cerrar o el botón cancelar
        respuesta.append(ConstantesGUI.CANCELAR);

        //Muestra el cuadro de dialogo
        
    }

    /** 
     * Metodo que centra el cuadro de dialogo en la ventana principal.
     * @param parent Frame.
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
     * @return verdadero o falso.
     */
    public boolean validarCampos() {
        if (campoTextoDesde.getText().equals("")) {
            return false;
        }
        if (campoTextoHasta.getText().equals("")) {
            return false;
        }
        return true;
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
        botonAceptar = new javax.swing.JButton();
        botonRestaurar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        campoTextoDesde = new javax.swing.JTextField();
        campoTextoHasta = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setText("Fecha desde:");

        jLabel2.setText("Fecha hasta:");

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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(botonCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonRestaurar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(botonAceptar)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(campoTextoHasta, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoTextoDesde, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(campoTextoDesde, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoTextoHasta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonCancelar)
                    .addComponent(botonRestaurar)
                    .addComponent(botonAceptar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * Metodo oyente del boton aceptar.
     *
     * @param evt evento al cual esuchara.
     */
    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        String desde, hasta;
        do {
            desde = campoTextoDesde.getText();
            hasta = campoTextoHasta.getText();
            if (!desde.matches("\\d{2}/\\d{2}/\\d{4}") || !hasta.matches("\\d{2}/\\d{2}/\\d{4}")) {
                JOptionPane.showMessageDialog(this, "Introduzca una fecha en el formato dd/mm/aaaa", "ERORR!!", JOptionPane.ERROR_MESSAGE);
                break;
            }
        } while (!desde.matches("\\d{2}/\\d{2}/\\d{4}") || !hasta.matches("\\d{2}/\\d{2}/\\d{4}"));
        if (desde.matches("\\d{2}/\\d{2}/\\d{4}") && hasta.matches("\\d{2}/\\d{2}/\\d{4}")) {
            if (!campoTextoDesde.getText().trim().equals("") && !campoTextoHasta.getText().trim().equals("")) {
                Fecha desdeF = new Fecha(desde);
                Fecha hastaF = new Fecha(hasta);
                if ((hastaF.after(desdeF) && desdeF.before(hastaF))||(hastaF.equals(desde) || desdeF.equals(hastaF))) {
                    periodo.setDesde(new Fecha(campoTextoDesde.getText()));
                    periodo.setHasta(new Fecha(campoTextoHasta.getText()));
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "La fecha hasta es anterior a la de desde", "ERORR!!", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Llena todos los campo de texto", "ERORR!!", JOptionPane.ERROR_MESSAGE);

            }
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    /** 
     * Metodo oyente del boton restaurar.
     * @param evt evento que esucuhara.
     */
    private void botonRestaurarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonRestaurarActionPerformed
        if (operacion == ConstantesGUI.AGREGAR) {
            campoTextoDesde.setText("");
            campoTextoHasta.setText("");
        }
    }//GEN-LAST:event_botonRestaurarActionPerformed

    /** 
     * Metodo oyente del boton cancelar.
     * @param evt evento el cual escuchara.
     */
    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        //Cierra el cuador de dialogo
        dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonRestaurar;
    private javax.swing.JTextField campoTextoDesde;
    private javax.swing.JTextField campoTextoHasta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables

}