package objetosNegocio;

import java.util.Objects;

/**
 *  Clase acerca de lo que corresponde a un producto en general.
 * 
 * @author Enrique Rodriguez
 */

public class Producto {
    
    protected String clave;
    protected String nombre;
    protected char tipo;
    protected String unidad;
    
    /**
     * Constructor por ausencia.
     */
    public Producto (){
        
    }
    
    /**
     * Método constructor que inicialice los atributos de la clase al valor 
     * de sus  parámetros.
     * 
     * @param clave Clave desiganada para el producto.
     * @param nombre Nombre del producto en cuestión.
     * @param tipo Tipo del producto.
     * @param unidad Unidad en la que se maneja.
     */
    public Producto(String clave, String nombre, char tipo, String unidad) {
        this.clave = clave;
        this.nombre = nombre;
        this.tipo = tipo;
        this.unidad = unidad;
        
    }
    
    /**
     * Método constructor que recibe una clave e inicializa los demas valores 
     * a nulos o ' '.
     * 
     * @param clave Clave desiganada para el producto.
     */
    public Producto(String clave) {
        this.clave = clave;
        this.nombre = null;
        this.tipo = ' ';
        this.unidad = null;
        
    }
    
    /**
     * Método constructor de copia, recibe como parametro un objeto de tipo 
     * producto e inicializa los atributos de la clase con los valores del mismo. 
     * 
     * @param producto Un producto.
     */
    public Producto (Producto producto){
        this.clave = producto.clave;
        this.nombre = producto.nombre;
        this.tipo = producto.tipo;
        this.unidad = producto.unidad;

    }
    
    /**
     * Método que obtiene la clave del producto.
     * 
     * @return Clave desiganada para el producto.
     */
    public String getClave() {
        return clave;
    }
    
    /**
     * Método que asigna la clave al producto.
     * 
     * @param clave Clave desiganada para el producto.
     */
    public void setClave(String clave) {
        this.clave = clave;
    }
    
    /**
     * Método que obtiene el nombre del producto.
     * 
     * @return Nombre del producto en cuestión.
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Método que asigna el nombre del producto.
     * 
     * @param nombre Nombre del producto en cuestión.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método que obtiene el tipo del producto.
     * 
     * @return Tipo del producto.
     */
    public char getTipo() {
        return tipo;
    }
    
    /**
     * Método que asigna el tipo del producto.
     * 
     * @param tipo Tipo del producto.
     */
    public void setTipo(char tipo) {
        this.tipo = tipo;
    }
    
    /**
     * Método que obtiene la unidad.
     * @return Unidad en la que se maneja.
     */
    public String getUnidad() {
        return unidad;
    }
    
    /**
     * 
     * @param unidad Unidad en la que se maneja.
     */
    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
    
    /**
     * El método hashCode().
     * @return El código Hash de una instancia de la clase.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 11 * hash + Objects.hashCode(this.clave);
        return hash;
    }
    
    /**
     * Compara este producto con el objeto del parámetro. El método regresa 
     * verdadero si el objeto del parámetro es de la clase Producto y ambos 
     * tienen la misma clave, falso en caso contrario. 

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
        final Producto other = (Producto) obj;
        return this.clave.equalsIgnoreCase(other.clave);
    }

    /**
     * El método toString() que regresa una cadena con los valores de los 
     * atributos de la clase. Los valores de los atributos irán separados por 
     * comas sin saltos de línea u otra información adicional.

     * 
     * @return Cadena de texto con los atributos.
     */
    @Override
    public String toString(){
        
        return clave + ", " + nombre + ", " + tipo + ", " + unidad;
        
    }
    
    
}