package objetosNegocio;

import java.util.Objects;
import objetosServicio.Fecha;

/**
 * Clase sobre los movimientos de productos en general.
 * @author Enrique Rodriguez
 */
public class Movimiento {

    private String cveMovimiento;
    private Fecha fecha;
    private boolean procesado;
    
    /**
     * El constructor por ausencia.
     */
    public Movimiento() {
        
    }
    
    /**
     * Método constructor que inicialice los atributos de la clase al valor 
     * de sus parámetros 
     * @param cveMovimiento Clave del movimiento.
     * @param fecha Fecha del movimiento.
     * @param procesado Valor booleano dependiendo si el producto fue o 
     * no procesado
     */
    public Movimiento(String cveMovimiento, Fecha fecha, boolean procesado) {
        this.cveMovimiento = cveMovimiento;
        this.fecha = fecha;
        this.procesado = procesado;
    }
    
    /**
     * Método constructor que reciba sólo la clave del movimiento e inicialice con
     * ella el atributo de su clase, los demás atributos de la clase se 
     * inicializarán a null o false.
     * 
     * @param cveMovimiento Clave del movimiento.
     */
    public Movimiento(String cveMovimiento) {
        this.cveMovimiento = cveMovimiento;
        this.fecha = null;
        this.procesado = false;
        
    }
    
    /**
     * Método que obtiene la clave del movimiento.
     * 
     * @return Clave del movimiento.
     */
    public String getCveMovimiento() {
        return cveMovimiento;
    }
    
    /**
     * Método que asigna la clave del movimiento.
     * 
     * @param cveMovimiento Clave del movimiento.
     */
    public void setCveMovimiento(String cveMovimiento) {
        this.cveMovimiento = cveMovimiento;
    }
    
    /**
     * Método que obtiene la fecha del movimiento.
     * 
     * @return 
     */
    public Fecha getFecha() {
        return fecha;
    }
    
    /**
     * Método que asigna la fecha del movimiento.
     * 
     * @param fecha 
     */
    public void setFecha(Fecha fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Método que obtiene un valor booleano dependiendo de si el producto fue o 
     * no procesado.
     * 
     * @return Valor booleano dependiendo si el producto fue o 
     * no procesado
     */
    public boolean getProcesado() {
        return procesado;
    }
    
    /**
     * Método que asigna un valor booleano dependiendo de si el producto fue o 
     * no procesado.
     * 
     * @param procesado Valor booleano dependiendo si el producto fue o 
     * no procesado
     */
    public void setProcesado(boolean procesado) {
        this.procesado = procesado;
    }
    
    /**
     * El método hashCode()
     * 
     * @return El código Hash de una instancia de la clase.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.cveMovimiento);
        return hash;
    }
    
    /**
     * Método que compara este producto con el objeto del parámetro. El método 
     * regresa verdadero si el objeto del parámetro es de la clase Producto y 
     * ambos tienen la misma clave, falso en caso contrario.
     * 
     * @param obj Objeto a comparar.
     * @return Valor booleano.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Movimiento other = (Movimiento) obj;
        return this.cveMovimiento.equalsIgnoreCase(other.cveMovimiento);
    }
    
    /**
     * Método regresa una cadena con los valores de los atributos de la clase. Los v
     * alores de los atributos irán separados por comas sin saltos de línea u 
     * otra información adicional.
     * 
     * @return Cadena de texto con los atributos.
     */
    @Override
    public String toString() {
        return cveMovimiento + ", " + fecha + ", " + procesado;
    }
    
}
