package control;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Equipo Siete
 */
public class Tabla {
    public String tituloTabla;
    public DefaultTableModel modeloTabla;
    
    /** 
     * Constructor sin parametros  
     */
    public Tabla(){
    }
    
    /** 
     * Constructor que inicializa los atributos de la clase 
     * @param tituloTabla Titulo de la tabla
     * @param modeloTabla Objeto TableModel con los datos de la tabla. 
     */
    public Tabla (String tituloTabla, DefaultTableModel modeloTabla){
        this.modeloTabla= modeloTabla;
        this.tituloTabla=tituloTabla;
    }

    /** 
     * Regresa el titulo de la tabla 
     * @return El titulo de la tabla 
     */
    public String getTitulo() {
        return tituloTabla;
    }

    /** 
     * Establece el titulo de la tabla 
     * @param tituloTabla Titulo de la tabla
     */
    public void setTitulo(String tituloTabla) {
        this.tituloTabla = tituloTabla;
    }

    /** 
     * Regresa los datos de la tabla
     * @return Objeto TableModel con los datos de la tabla  
     */
    public DefaultTableModel getModeloTabla() {
        return modeloTabla;
    }

    /** 
     * Establece los datos de la tabla
     * @param modeloTabla Objeto TableModel con los datos de la tabla 
     */
    public void setModeloTabla(DefaultTableModel modeloTabla) {
        this.modeloTabla = modeloTabla;
    }
    
    
    
}
