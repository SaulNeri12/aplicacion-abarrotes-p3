
package excepciones;

/**
 * Indica cuando hay un error al querer conectarse o desconectarse de la base de datos
 * de abarrotes
 * @author Saul Neri
 */
public class ErrorConexionException extends Exception {
    public ErrorConexionException(String msg) {
        super(msg);
    }
}
