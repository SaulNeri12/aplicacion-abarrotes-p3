package excepciones;

/**
 * Excepcion necesaria para marcar errores de cualquier tipo en la persistencia
 * @author Equipo 07
 */
public class PersistenciaException extends Exception{
    
    /** 
     * Crea una excepcion del tipo PersistenciaException
     * @param mensaje mensaje de error
     */
    public PersistenciaException(String mensaje){
        super(mensaje);
    }
}
