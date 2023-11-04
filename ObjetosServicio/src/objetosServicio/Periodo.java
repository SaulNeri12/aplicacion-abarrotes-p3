package objetosServicio;

/**
 * Clase con métodos para diversas funciones referentes a fechas y periodos.
 * 
 * @author Enrique Rodríguez Angulo
 */
public class Periodo {
    //Declaración de las distintas variables.
    private Fecha desde;
    private Fecha hasta;
    /**
     * Construye un objeto de tipo Periodo y lo inicializa al valor de sus 
     * parámetros.
     * 
     * @param desde Fecha de comienzo.
     * @param hasta Fecha final.
     */
    public Periodo(Fecha desde, Fecha hasta){
        this.desde = desde;
        this.hasta = hasta;
        
    }
    /**
     * Obtener la fecha de comienzo.
     * 
     * @return fecha de comienzo.
     */
    public Fecha getDesde() {
        return desde;
    }
    /**
     * Establecer la fecha comienzo.
     * 
     * @param desde Fecha de comienzo.
     */
    public void setDesde(Fecha desde) {
        this.desde = desde;
    }
    /**
     * Obtener la fecha final.
     * 
     * @return Fecha final.
     */
    public Fecha getHasta() {
        return hasta;
    }
    /**
     * Establecer la fecha final.
     * 
     * @param hasta Fecha final.
     */
    public void setHasta(Fecha hasta) {
        this.hasta = hasta;
    }
    /**
     * Método que evalua si una fecha esta dentro de un periodo establecido por
     * la clase
     * 
     * @param fecha Fecha que será evaluada.
     * @return 
     */
    public boolean contiene(Fecha fecha){
        return (fecha.after(desde) && fecha.before(hasta))||(fecha.equals(desde) || fecha.equals(hasta));
    }
    
    //Sobre escritura de un método.
    /**
     * Método que regresa dos fechas con un formato establecido: 
     * dd/mm/aaaa a dd/mm/aaaa.
     * 
     * @return Dos fechas con formato: dd/mm/aaaa a dd/mm/aaaa.
     */    
    @Override
    public String toString(){
        
        return desde.toString() +" a " + hasta.toString();
        
    }
    
}
