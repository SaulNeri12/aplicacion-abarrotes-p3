package excepciones;

/**
 * Excepcion necesaria para el manejo de errores en DAO Abarrotes
 * @author Equipo 07
 */
public class DAOException extends Exception{
        public DAOException(String mensaje){
            super(mensaje);
        }
}
